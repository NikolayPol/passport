//package ru.job4j.passport.config;
//
//import org.apache.catalina.filters.FilterBase;
//import org.apache.juli.logging.Log;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.stream.Collectors;
//
//@Component
//public class RequestFilter extends FilterBase {
//
//    @Override
//    protected Log getLogger() {
//        return null;
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest,
//                         ServletResponse servletResponse, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        System.out.println(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
//        System.out.println(servletRequest.getParameterMap().keySet());
//        System.out.println(servletRequest.getParameterNames().toString());
//        System.out.println(servletRequest.getServletContext().getContextPath());
//
//
//        chain.doFilter(request, servletResponse);
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}