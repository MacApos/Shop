package pl.coderslab.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.entity.Product;
import pl.coderslab.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductInterceptor implements InterceptorInterface<Product>{
    private final ProductService productService;

    @Override
    public Product findById(Long id) {
        return productService.findById(id);
    }
}
