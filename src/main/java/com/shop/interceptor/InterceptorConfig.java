package com.shop.interceptor;

import com.shop.service.CategoryService;
import com.shop.service.ProductService;
import com.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;

    @Override
    public void addInterceptors( InterceptorRegistry registry) {
        registry.addInterceptor(new InterceptorService<>(categoryService))
                .addPathPatterns(
                        "/category/{id:\\d+}",
                        "/category/parents/{id:\\d+}",
                        "/product/by-category/{id:\\d+}");
        registry.addInterceptor(new InterceptorService<>(productService))
                .addPathPatterns(
                        "/product/{id:\\d+}");
        registry.addInterceptor(new InterceptorService<>(userService))
                .addPathPatterns(
                        "/user/reset-password/{username:\\w+}");
    }

}
