package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

public class denkiya {
	@RestController
	public class ProductController {

	    @Autowired
	    private ProductService productService;

	    @GetMapping("/products")
	    public List<Product> getAllProducts() {
	        return productService.getAllProducts();
	    }

	    @GetMapping("/products/{id}")
	    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
	        Product product = productService.getProductById(id);
	        if (product == null) {
	            return ResponseEntity.notFound().build();
	        }
	        return ResponseEntity.ok(product);
	    }
	}
}
