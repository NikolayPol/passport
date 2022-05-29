package ru.job4j.passport.config.advice;

import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

@ControllerAdvice
public class CustomRequestBodyAdvice implements RequestBodyAdvice {


    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
//        return methodParameter.getContainingClass() == PassportController.class;
        return true;

    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        InputStream body = inputMessage.getBody();
        String bodyStr = IOUtils.toString(body, Charset.forName("UTF-8"));
        System.out.println(bodyStr);

        // здесь можно изменить body запроса перед десериализацией в контроллере
//        if(!bodyStr.contains("query")) {
//            String bufStr = bodyStr.substring(0, bodyStr.length()-1);
//            System.out.println(bufStr);
////            bodyStr = bufStr + ",\"query\":\"{}\"}";
//            bodyStr = bufStr + ",\"query\":\"{getPassportByNumber(number: 112233) {id series number}}\"}";
//        }
        //System.out.println(bodyStr);

        HttpInputMessage ret = new MappingJacksonInputMessage(new ByteArrayInputStream(bodyStr.getBytes()), inputMessage.getHeaders()); //set the updated bodyStr
        return ret;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        System.out.println(body);
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
