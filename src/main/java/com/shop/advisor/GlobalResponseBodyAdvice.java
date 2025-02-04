//package com.shop.advisor;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice
//@RequiredArgsConstructor
//public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {
//    private final ObjectMapper objectMapper;
//
//    @Override
//    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
//        return true;
//    }
//
//    @Override
//    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
//                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
//                                  ServerHttpRequest request, ServerHttpResponse response) {
//        Map<String, Object> map = objectMapper
//                .convertValue(body, new TypeReference<>() {
//                });
//        map.put("status", 200);
//        return map;
//    }
//}
