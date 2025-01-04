package pl.coderslab;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.coderslab.filter.CategoryInterceptor;
import pl.coderslab.service.impl.CategoryService;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
@RequiredArgsConstructor
public class Application
        implements WebMvcConfigurer {
    private final CategoryInterceptor categoryInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(categoryInterceptor)
                .addPathPatterns(
                        "/category/{id:\\d+}",
                        "/category/parents/{id:\\d+}");

    }

}
