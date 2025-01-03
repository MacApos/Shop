package pl.coderslab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.entity.Category;
import pl.coderslab.service.impl.CategoryService;
import pl.coderslab.service.impl.ProductService;

import java.util.List;
import java.util.TreeSet;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping("/categories")
    public List<Category> getAllCategories(){
        return categoryService.findAll();
    }

    @GetMapping("/categories-hierarchy")
    public TreeSet<Category> getCategoriesHierarchy(){
        return categoryService.getHierarchyMap();
    }

    @GetMapping("/category/{id}")
    public Category getCategoryById(@PathVariable Long id){
        Category category = categoryService.findById(id);
        if(category==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return category;
    }

    @GetMapping("/category-parents/{id}")
    public List<Category> getCategoryParents(@PathVariable Long id){
        Category category = categoryService.findById(id);
        if(category==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        List<Category> parents2 = categoryService.getParents2(category);
        return categoryService.getParents(category);
    }


//
//    @RequestMapping("/**")
//    public List<Object> searchController(HttpServletRequest request, Model model) {
//        String[] split = categoryService.splitPathAroundProduct(request.getRequestURI());
//        String path = split[0];
//
//        Category category = categoryService.findByPath(path);
//        if (category == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//
//        List<Object> responseBody = new ArrayList<>();
//        List<Category> parents = categoryService.getParents(category);
//        responseBody.add(parents);
//
//        if (split.length > 1) {
//            String productPath = split[1];
//            Product product = productService.findByPathAndCategory(productPath, category);
//            if (product == null) {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//            }
//            model.addAttribute("product", product);
//            responseBody.add(product);
//            return List.of(parents, product);
//        }
//        List<Category> categories = categoryService.findAllByParentCategory(category);
//        List<Product> products = productService.findAllByCategory(category);
//        model.addAttribute("category", category);
//        model.addAttribute("categories", categories);
//        model.addAttribute("products", products);
//        return "allProducts";
//    }

//    @RequestMapping("/all")
//    public String searchAll(Model model) {
//        List<Category> categories = categoryService.findAllByParentCategory(null);
//        List<Product> products = productService.findAll();
//        model.addAttribute("category", "Wszystkie");
//        model.addAttribute("categories", categories);
//        model.addAttribute("products", products);
//        return "allProducts";
//    }

}