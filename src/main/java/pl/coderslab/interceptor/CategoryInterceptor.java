package pl.coderslab.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.entity.Category;
import pl.coderslab.service.impl.CategoryService;

@Service
@RequiredArgsConstructor
public class CategoryInterceptor implements InterceptorInterface<Category>{
    private final CategoryService categoryService;

    @Override
    public Category findById(Long id) {
        return categoryService.findById(id);
    }
}
