package com.shop.controller;

import com.shop.validation.product.group.sequence.CreateProductSequence;
import com.shop.validation.product.group.sequence.DeleteProductSequence;
import com.shop.validation.product.group.sequence.UpdateProductSequence;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.shop.model.Category;
import com.shop.model.Product;
import com.shop.service.CategoryService;
import com.shop.service.ProductService;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(name = "category") Optional<Integer> categoryId,
                                                        @RequestParam(name = "page") Optional<Integer> pageNumber,
                                                        @RequestParam(name = "size") Optional<Integer> pageSize,
                                                        @RequestParam(name = "sort") Optional<String> sortCriteria,
                                                        @RequestParam(name = "direction") Optional<String> sortDirection) {
        return ResponseEntity.ok(productService.findAll(categoryId, pageNumber, pageSize, sortCriteria, sortDirection));
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> getProductImageById(@PathVariable Long id) throws MalformedURLException {
        Product product = productService.findById(id);
        Path path = Paths.get("uploads/" + product.getImage());
        Resource resource = new UrlResource(path.toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @GetMapping("/by-category/{id}")
    public List<Product> getProductsByCategory(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        return productService.findAllByCategory(category);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<Objects> create(@RequestBody @Validated(CreateProductSequence.class) Product product) {
        productService.save(product);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/admin/update")
    public ResponseEntity<Objects> update(@RequestBody @Validated(UpdateProductSequence.class) Product product) {
        productService.update(product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<Objects> delete(@RequestBody @Validated(DeleteProductSequence.class) Product product) {
        productService.delete(product);
        return ResponseEntity.ok().build();
    }
}