package com.pony.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerMiddleware extends HandlerInterceptorAdapter {
    private static Logger _logger = LoggerFactory.getLogger(LoggerMiddleware.class);

    private long _startTimer; 
    private boolean _isDynamic;
    private static String[] _matches = {".cancer", ".css", ".js", ".img", ".ico"};

    public LoggerMiddleware() { }
    
    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String path = request.getServletPath();
        if (!this.isStatiContent(path)) {
            _startTimer = System.currentTimeMillis();
            _isDynamic = true;
        }

        //Must return true in order to process the request
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        
        if (_isDynamic) {
            long endTimer = System.currentTimeMillis();

            String message = String.format("HTTP %s %s from %s responded %s in %d%n ms", 
                request.getMethod(), request.getServletPath(), request.getRemoteAddr(), response.getStatus(), getElapsedTime(_startTimer, endTimer));
            _logger.info(message);
        }
    }
    
    private boolean isStatiContent(String url)
    {
        for (String filter : _matches) {
            if (url.endsWith(filter)) {
                return true;
            }
        }

        return false;
    }
    
    private long getElapsedTime(long start, long end) {
        
        return end - start;
    }
}