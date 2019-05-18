/*<![CDATA[*/
    $(function () {

        // Lang change
        // $(document).ready(function() {
        $("#locales").change(function () {
            var selectedOption = $('#locales').val();
            if (selectedOption !== ''){
                window.location.replace('locale?lang=' + selectedOption);
            }
        });

        // $('.collapse').collapse();


        // Close the loader splash screen
        setTimeout(function () {
            $('body').addClass('loaded');
        }, 10);

    });

/*]]>*/