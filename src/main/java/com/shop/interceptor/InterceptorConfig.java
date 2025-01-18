package com.shop.interceptor;

import com.shop.service.CategoryService;
import com.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource(value = "messages_pl.properties", encoding="UTF-8")
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final CategoryService categoryService;
    private final ProductService productService;

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
    }

}
