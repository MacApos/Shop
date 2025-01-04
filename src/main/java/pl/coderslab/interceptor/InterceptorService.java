package pl.coderslab.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;


@RequiredArgsConstructor
public class InterceptorService implements HandlerInterceptor {
    private final InterceptorInterface interceptorInterface;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String[] split = path.split("/");
        String id = split[split.length - 1];

        if (interceptorInterface.findById(Long.valueOf(id)) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return true;
    }

}
