package com.pony.controllers;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pony.viewmodels.RegisterViewModel;

@Controller
@PreAuthorize("hasRole('USER')")
public class DebugController {

    @RequestMapping("/debug")
    public ModelAndView register(@Valid @RequestBody @ModelAttribute RegisterViewModel viewModel, BindingResult bindingResult) {
        
        // try {
        //     var httpTransport = new Nethttptransport();
        //     var jacksonFactory = new JacksonFactory();
        //     TokenResponse response =
        //         new AuthorizationCodeTokenRequest(new NetHttpTransport(), new com.google.api.client.json.jackson2.JacksonFactory(),
        //             new GenericUrl("here is the server url "), "here write your code")
        //             .setRedirectUri("here write the redirectUrl")
        //             .set("client_id","here write your client_id")
        //             .set("client_secret","here write your client_secret")
        //             .set("Other else need","Other else need")
        //             .execute();
        //     System.out.println("Access token: " + response.getAccessToken());
        //   } catch (TokenResponseException e) {
        //     if (e.getDetails() != null) {
        //       System.err.println("Error: " + e.getDetails().getError());
        //       if (e.getDetails().getErrorDescription() != null) {
        //         System.err.println(e.getDetails().getErrorDescription());
        //       }
        //       if (e.getDetails().getErrorUri() != null) {
        //         System.err.println(e.getDetails().getErrorUri());
        //       }
        //     } else {
        //       System.err.println(e.getMessage());
        //     }
        //   }
        return null;
    }
}