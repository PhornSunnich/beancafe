package nichy.beancafe.service;

import lombok.RequiredArgsConstructor;
import nichy.beancafe.dto.ProductRequestDto;
import nichy.beancafe.entity.Product;
import nichy.beancafe.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> getProducts(Product.Category category){
        if (category != null){
            return productRepository.findByCategoryAndIsAvailableTrue(category);
        }
        return productRepository.findByIsAvailableTrue();
    }

    @Transactional(readOnly = true)
    public Product getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Transactional
    public Product createProduct(ProductRequestDto dto){
        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .imageUrl(dto.getImageUrl())
                .category(dto.getCategory())
                .isAvailable(dto.getIsAvailable())
                .build();
        return productRepository.save(product);
    }

    public Product updateProductAvailability(Long id, Boolean isAvailable){
        Product product = getProductById(id);
        product.setIsAvailable(isAvailable);
        return productRepository.save(product);
    }



}
