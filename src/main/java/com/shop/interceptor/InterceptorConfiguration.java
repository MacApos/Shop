package com.shop.interceptor;

import com.shop.service.CartItemService;
import com.shop.service.CategoryService;
import com.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final CartItemService cartItemService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new InterceptorService<>(categoryService))
                .addPathPatterns(
                        "/categories/{id:\\d+}",
                        "/categories/parents/{id:\\d+}",
                        "/products/by-category/{id:\\d+}");
        registry.addInterceptor(new InterceptorService<>(productService))
                .addPathPatterns(
                        "/products/{id:\\d+}",
                        "/products?category:\\d+");
        registry.addInterceptor(new InterceptorService<>(cartItemService))
                .addPathPatterns(
                        "/cart-items/update/{id:\\d+}",
                        "/cart-items/delete/{id:\\d+}");
    }
}
