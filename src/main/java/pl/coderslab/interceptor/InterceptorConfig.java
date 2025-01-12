package pl.coderslab.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final CategoryInterceptor categoryInterceptor;
    private final ProductInterceptor productInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new InterceptorService<>(categoryInterceptor))
                .addPathPatterns(
                        "/category/{id:\\d+}",
                        "/category/parents/{id:\\d+}",
                        "/product/by-category/{id:\\d+}");
        registry.addInterceptor(new InterceptorService<>(productInterceptor))
                .addPathPatterns(
                        "/product/{id:\\d+}");
    }

}
