<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.utils.json;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Gotug
 */
public class JsonView {

     public static ModelAndView Render(Object model, HttpServletResponse response, HttpStatus statusCode) {

        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();

        MediaType jsonMimeType = MediaType.APPLICATION_JSON;

        try {
            ServletServerHttpResponse serverHttpResponse = new ServletServerHttpResponse(response);
            serverHttpResponse.setStatusCode(statusCode);

            jsonConverter.write(model, jsonMimeType, serverHttpResponse);
        } catch (HttpMessageNotWritableException | IOException e) {
            //
        }

        return null;
    }
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.utils.json;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Gotug
 */
public class JsonView {

     public static ModelAndView Render(Object model, HttpServletResponse response, HttpStatus statusCode) {

        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();

        MediaType jsonMimeType = MediaType.APPLICATION_JSON;

        try {
            ServletServerHttpResponse serverHttpResponse = new ServletServerHttpResponse(response);
            serverHttpResponse.setStatusCode(statusCode);

            jsonConverter.write(model, jsonMimeType, serverHttpResponse);
        } catch (HttpMessageNotWritableException | IOException e) {
            //
        }

        return null;
    }
>>>>>>> master
}