package ma.ensa.productservice.controller;

import ma.ensa.productservice.Model.Product;
import ma.ensa.productservice.repo.ProductRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductController {
    private final ProductRepository productRepository;


    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @QueryMapping
    public Product getProductById(@Argument Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @MutationMapping
    public Product createProduct(@Argument String name, @Argument String description, @Argument int stock) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setStock(stock);
        return productRepository.save(product);
    }

    @MutationMapping
    public Product updateStock(@Argument Long id, @Argument int stock) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setStock(stock);
        return productRepository.save(product);
    }
}
