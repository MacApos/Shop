package com.shop.interceptor;

import com.shop.service.CartItemService;
import com.shop.service.CategoryService;
import com.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

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
                        "/category/{id:\\d+}",
                        "/category/parents/{id:\\d+}",
                        "/product/by-category/{id:\\d+}");
        registry.addInterceptor(new InterceptorService<>(productService))
                .addPathPatterns(
                        "/product/{id:\\d+}",
                        "/product?category:\\d+");
        registry.addInterceptor(new InterceptorService<>(cartItemService))
                .addPathPatterns(
                        "/cart-item/update/{id:\\d+}",
                        "/cart-item/delete/{id:\\d+}");
    }

}
