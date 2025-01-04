package pl.coderslab.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import pl.coderslab.service.impl.CategoryService;

@RequiredArgsConstructor
@Component
public class CategoryInterceptor implements HandlerInterceptor {
    private final CategoryService categoryService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String[] split = path.split("/");
        String id = split[split.length - 1];

        if (categoryService.findById(Long.valueOf(id)) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return true;
    }

}
