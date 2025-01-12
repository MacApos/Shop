package pl.coderslab.interceptor;

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
    private final InterceptorInterface<T> interceptorInterface;

    @Override
    public boolean preHandle(HttpServletRequest request, @Nullable HttpServletResponse response,
                             @Nullable Object handler) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String[] split = path.split("/");
        String id = split[split.length - 1];

        if (interceptorInterface.findById(Long.valueOf(id)) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return true;
    }

}
