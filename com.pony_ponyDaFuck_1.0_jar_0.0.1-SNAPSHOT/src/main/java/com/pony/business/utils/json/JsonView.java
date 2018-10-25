package com.pony.business.utils.json;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.ModelAndView;

public class JsonView {

     public static ModelAndView Render(Object model, HttpServletResponse response, HttpStatus statusCode) {

        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();

        MediaType jsonMimeType = MediaType.APPLICATION_JSON;

        try {
            ServletServerHttpResponse serverHttpResponse = new ServletServerHttpResponse(response);
            serverHttpResponse.setStatusCode(statusCode);

            jsonConverter.write(model, jsonMimeType, serverHttpResponse);
        } 
        catch (HttpMessageNotWritableException | IOException e) {
            //
        }

        return null;
    }
}