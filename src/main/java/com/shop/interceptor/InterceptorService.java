package com.shop.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

@RequiredArgsConstructor
public class InterceptorService<T> implements HandlerInterceptor {
    private final ServiceInterface<T> serviceInterface;

    @Override
    public boolean preHandle(HttpServletRequest request, @Nullable HttpServletResponse response,
                             @Nullable Object handler) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String[] split = path.split("/");
        String id = split[split.length - 1];

        if (serviceInterface.existsById(Long.valueOf(id))) {
            return true;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
