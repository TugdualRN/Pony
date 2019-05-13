
    /* Au clic sur un élément de menu... */
//     $('#menu li a').click(function(event){
//
//         /* On récupère l'url du lien sur lequel on vient de cliquer. */
//         var url = $(this).attr('href');
//
//         /*
//          * Dans notre bloc #content, on inject le contenu
//          * de la page ciblée (par le href) en se limitant
//          * à ce qui est dans le bloc #content.
//          */
//         $('#content').load(url + " #content");
//
//         /*
//          * On évite le comportement par défaut qui est de
//          * nous envoyer sur la page donnée dans le href).
//          */
//         event.preventDefault();
//     });
//
// });



// // Globals variables
    var currentUrl;

// Globals functions
    var loadContent;
    var refreshContent;

    var isSessionAlreadyFinished = false;
    var isRequestPending = false;
    var pendingRequest = null;
    var isContentLoading = false;
    var isRefreshing = false;

    function abortPendingRequestIfExists() {
        if (pendingRequest !== null) {
            pendingRequest.abort();
        }
    }

    $(function () {

        const ORIGIN = window.location.origin + "/";
        const DEFAULT_PROGRESS_PERCENT = 0;

        const loaderProgress = $("#loader-progress");

        var currentNavigationElement = null;
        var currentNavigationHeader = null;
        var body = $("body");

        var removeTooltipIntervalId = null;
        var inputToRemoveTooltip = null;

        // If the current session ended
        $(document).ajaxError(function (event, xhr, settings) {

            if (xhr.status === 412 && !isSessionAlreadyFinished) {
                isSessionAlreadyFinished = true;

                $.alert({
                    title: 'Session expirée',
                    content: "Votre session à expirée, vous allez être redirigé vers la page de connexion.",
                    typeAnimated: false,
                    type: 'blue',
                    autoClose: 'autoClose|10000',
                    buttons: {
                        autoClose: {
                            text: 'Ok',
                            btnClass: 'btn-primary',
                            action: function () {
                                window.location.replace("/login?session=expired");
                            }
                        }
                    }
                });
            }
        });

        loadContent = function (url, mustSaveUrl) {

            // We prevent loading another content before finished the current one
            if (isContentLoading) {
                return;
            }

            // We abort pending request; for exemple refresh requests
            abortPendingRequestIfExists();

            // We keep trace we actually downloading content
            isContentLoading = true;

            var progress = DEFAULT_PROGRESS_PERCENT;
            loaderProgress.addClass("progress-bar-animated").removeClass("invisible");

            var interval = setInterval(function () {
                if (progress < 90) {
                    progress += 15;
                    loaderProgress.css("width", progress + "%");
                } else {
                    loaderProgress.css("width", "98%");
                }
            }, 500);

            $.ajax({
                url: url,
                type: "GET",
                async: true
            }).done(function (data) {

                if (mustSaveUrl)
                    currentUrl = url; // We save the last content url

                if (isRefreshing === false) {
                    var stateObj = {url: currentUrl};
                    history.pushState(stateObj, "page 2", "home");
                }

                isRefreshing = false;

                var mainContent = $("#main-content");
                mainContent.hide(); // We hide the content to let it be correctly initialized
                mainContent.html(data); // We change the contentmodel

                var contentTitle = $(".content-title").detach(); // We detach the content title from the html page
                $("#main-title").text(contentTitle.text()); // We change the #main-title h1 text by the current .content-title text

                $('.history li:last-child a').text($(".page-title").text());

                // We let a little time to let content to be ready before showing it
                setTimeout(function () {
                    mainContent.show();
                }, 10);
            })
                .fail(function (xhr) {
                        // There is already a message for ended session
                        $('.history li:last-child').remove();
                        if (xhr.status !== 412) {
                            $.alert({
                                title: 'Ressource introuvable',
                                content: "La page ou la ressource demandée est introuvable.",
                                type: 'red',
                                typeAnimated: false,
                                buttons: {close: {text: 'Fermer', btnClass: "btn-default"}}
                            });
                        }
                    }
                ).always(function () {
                clearInterval(interval); // We remove the updating progress bar interval
                loaderProgress.addClass("invisible").removeClass("progress-bar-animated").css("width", DEFAULT_PROGRESS_PERCENT + "%"); // We hide and reset the progress bar
                isContentLoading = false; // We keep trace we are no longer downloading content
            });
        };

        refreshContent = function () {
            isRefreshing = true;
            loadContent(currentUrl);
        };

        $(window).on('popstate', function () {
            if (history.state !== null) {
                getBack();
                $('.history li:last-child').remove();
            } else {
                history.go(-1);
            }
        });

        $(".btn-refresh").on("click", function () {
            refreshContent();
        });

        body.on("click", ".btn-return", function () {
            history.go(-1);
        });

        body.on("click", ".history-item", function () {
            console.log($(this));

            $(this).parent().nextAll().remove();
        });

        function getBack() {
            var currentState = history.state;

            // We prevent loading another content before finished the current one
            if (isContentLoading) {
                return;
            }

            // We abort pending request; for exemple refresh requests
            abortPendingRequestIfExists();

            // We keep trace we actually downloading content
            isContentLoading = true;

            var progress = DEFAULT_PROGRESS_PERCENT;
            loaderProgress.addClass("progress-bar-animated").removeClass("invisible");

            var interval = setInterval(function () {
                if (progress < 90) {
                    progress += 15;
                    loaderProgress.css("width", progress + "%");
                } else {
                    loaderProgress.css("width", "98%");
                }
            }, 500);

            $.ajax({
                url: currentState.url,
                type: "GET",
                async: true
            }).done(function (data) {

                currentUrl = currentState.url; // We save the last content url

                var mainContent = $("#main-content");

                mainContent.hide(); // We hide the content to let it be correctly initialized
                mainContent.html(data); // We change the content

                var contentTitle = $(".content-title").detach(); // We detach the content title from the html page
                $("#main-title").text(contentTitle.text()); // We change the #main-title h1 text by the current .content-title text

                // We remove the active style on the previously selected navigation link
                if (currentNavigationElement !== null) {
                    currentNavigationElement.removeClass("active");
                }

                // We remove the active style on the previously selected navigation link header
                if (currentNavigationHeader !== null) {
                    currentNavigationHeader.removeClass("active");
                }

                // We let a little time to let content to be ready before showing it
                setTimeout(function () {
                    mainContent.show();
                }, 10);
            }).fail(function (xhr) {
                // There is already a message for ended session
                if (xhr.status !== 412) {
                    $.alert({
                        title: 'Ressource introuvable',
                        content: "La page ou la ressource demandée est introuvable.",
                        type: 'red',
                        typeAnimated: false,
                        buttons: {close: {text: 'Fermer', btnClass: "btn-default"}}
                    });
                }
            }).always(function () {
                clearInterval(interval); // We remove the updating progress bar interval
                loaderProgress.addClass("invisible").removeClass("progress-bar-animated").css("width", DEFAULT_PROGRESS_PERCENT + "%"); // We hide and reset the progress bar
                isContentLoading = false; // We keep trace we are no longer downloading content
            });
        }


        body.on("click", ".custom-link", function () {

            var mustSaveUrl = !$(this).hasClass("form-link"); // We don't save current url for form page due to the redirection

            if ($(this).hasClass("main")) {
                $('.history').empty();
            }
            if (!$(this).hasClass("history-item")) {
                $('.history').append('<li><a class="custom-link history-item" href="#" data-href="' + $(this).data("href") + '"></a></li>');
            }

            loadContent(ORIGIN + $(this).data("href"), mustSaveUrl, $(this));


            var navigationElement = $(this);
            var navigationHeader = navigationElement.closest("li.has-treeview").find("a").first();

            // We remove the active style on the previously selected navigation link
            if (currentNavigationElement !== null) {
                currentNavigationElement.removeClass("active");
            }

            // We remove the active style on the previously selected navigation link header
            if (currentNavigationHeader !== null) {
                currentNavigationHeader.removeClass("active");
            }

            // We add the active style on the newly selected navigation link
            if (navigationElement.hasClass("nav-link")) {
                navigationElement.addClass("active");
                currentNavigationElement = navigationElement;
                $("body").removeClass("sidebar-open");

                if (navigationHeader !== null && navigationHeader.length > 0) {
                    navigationHeader.addClass("active");
                    currentNavigationHeader = navigationHeader;
                }
            }

            // Prevent normal navigation
            return false;
        });

        // // To prevent user to press unauthorized characters in form's input with regex
        $("#main-content").on("keypress", "input", function (event) {

            // If the user press the enter key
            if (event.keyCode === 13) {
                submitForm($(this), event);
            }

            var char = event.originalEvent.key;
            var value = event.target.value;
            var regexString = event.target.dataset.pattern;
            var input = $(this);

            if (regex === 'undefined' || regex === null || event.charCode === 0) { // event.charCode allow the use of some non character key
                // We let the user enter a character
                return true;
            }

            var regex = RegExp(regexString);

            // If the input was empty
            if (value.length === 0) {
                return testRegexAndShowTooltip(regex, char, input, char);
            }

            return testRegexAndShowTooltip(regex, value + char, input, char);
        });

        function testRegexAndShowTooltip(regex, value, input, newChar) {

            if (regex.test(value)) {
                input.siblings(".tooltip").remove();
                return true;
            } else {
                var tooltip = input.tooltip({
                    title: "Ce caractère n'est pas autorisé",
                    placement: 'bottom',
                    trigger: 'manual'
                }).tooltip('show');
                tooltip.siblings(".tooltip").find(".tooltip-inner").html("<span><span class='pressed-key'>" + newChar + "</span> n'est pas autorisé</span>"); // We override the previous text; allows to refresh wrong character in tooltip

                // If it is the same input than the last time we report the moment when the tooltip will be removed
                if (inputToRemoveTooltip === input.get(0) && removeTooltipIntervalId !== null) {
                    clearInterval(removeTooltipIntervalId);
                }

                inputToRemoveTooltip = input.get(0);

                removeTooltipIntervalId = setTimeout(function () {
                    input.siblings(".tooltip").remove();
                }, 3000); // The tooltip is removed after 3 seconds
                return false;
            }
        }

        function submitForm(element, event) {

                // Prevent the normal submit way
                event.preventDefault();

                var form = element.closest("form").eq(0);

                var url = ORIGIN + form.attr("action")

                $.ajax({
                    type: form.attr("method"),
                    url: url,
                    data: form.serialize() // serializes the form's elements.
                }).done(function (data) {

                    var mainContent = $("#main-content");
                    mainContent.hide(); // We hide the content to let it be correctly initialized
                    mainContent.html(data); // We change the content

                    var contentTitle = $(".content-title").detach(); // We detach the content title from the html page
                    $("#main-title").text(contentTitle.text()); // We change the #main-title h1 text by the current .content-title text

                    setTimeout(function () {
                        mainContent.show();
                    }, 10);
                });

                // Prevent normal navigation
                return false;
            }

            body.on("click", ".custom-form-submit", function (event) {

                event.preventDefault();
                var form = $(this);

                $.confirm({
                    title: "Enregistrement",
                    content: "Voulez-vous vraiment enregistrer ces informations ?",
                    type: "blue",
                    typeAnimated: false,
                    buttons: {
                        cancel: {
                            text: "Annuler"
                        },
                        modify: {
                            text: "Enregistrer",
                            btnClass: "btn-primary pull-right",
                            action: function () {
                                submitForm(form, event);
                            }
                        }
                    }
                });

            });

            var firstLinkOnPage = $(".main-sidebar a.custom-link").eq(0);
            // We automatically load the first possible page available in the main-sidebar
            firstLinkOnPage.click();
        });