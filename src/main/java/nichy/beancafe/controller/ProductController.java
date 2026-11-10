package nichy.beancafe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nichy.beancafe.dto.ProductRequestDto;
import nichy.beancafe.entity.Product;
import nichy.beancafe.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(@RequestParam(required = false) Product.Category category){
        return ResponseEntity.ok(productService.getProducts(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(dto));
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<Product> updateAvailability(
            @PathVariable Long id,
            @RequestParam Boolean isAvailable
    ){
        return ResponseEntity.ok(productService.updateProductAvailability(id, isAvailable));
    }



}
