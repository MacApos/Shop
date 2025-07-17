package com.shop.interceptor;

import com.shop.service.AbstractService;
import com.shop.service.ServiceInterface;
import com.shop.service.ServiceInterface;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class InterceptorService<T> implements HandlerInterceptor {
    private final AbstractService<T> serviceInterface;

    @Override
    public boolean preHandle(HttpServletRequest request, @Nullable HttpServletResponse response,
                             @Nullable Object handler) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        Pattern pattern = Pattern.compile("id=\\s*(\\d+)");
        Matcher matcher = pattern.matcher(path);

        String[] split = path.split("/");
        String id = split[split.length - 1];

        if (serviceInterface.existsById(Long.valueOf(id))) {
            return true;
        }
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
