package pl.coderslab;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.coderslab.interceptor.CategoryInterceptor;
import pl.coderslab.interceptor.InterceptorService;
import pl.coderslab.interceptor.ProductInterceptor;
import pl.coderslab.repository.CategoryRepository;
import pl.coderslab.repository.ProductRepository;

@SpringBootApplication
@RequiredArgsConstructor
public class Application
        implements WebMvcConfigurer {

    private final CategoryInterceptor categoryInterceptor;
    private final ProductInterceptor productInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new InterceptorService(categoryInterceptor))
                .addPathPatterns(
                        "/category/{id:\\d+}",
                        "/category/parents/{id:\\d+}",
                        "/product/by-category/{id:\\d+}");
        registry.addInterceptor(new InterceptorService(productInterceptor))
                .addPathPatterns(
                        "/product/{id:\\d+}");
    }

}
