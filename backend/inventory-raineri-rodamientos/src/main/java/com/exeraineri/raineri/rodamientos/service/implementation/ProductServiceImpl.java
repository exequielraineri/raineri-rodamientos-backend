package com.exeraineri.raineri.rodamientos.service.implementation;

import com.exeraineri.raineri.rodamientos.entity.Category;
import com.exeraineri.raineri.rodamientos.entity.Product;
import com.exeraineri.raineri.rodamientos.exception.ResourceNotFoundException;
import com.exeraineri.raineri.rodamientos.repository.IProductRepository;
import com.exeraineri.raineri.rodamientos.service.interfaces.ICategoryService;
import com.exeraineri.raineri.rodamientos.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    private final ICategoryService categoryService;
    @Override
    public List<Product> findAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "description");
        return productRepository.findAll(sort);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No se encontro le producto"));
    }

    @Override
    public Product save(Product product) {
        Category category = categoryService.findById(product.getCategory().getId());
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product update(Product product, Long id) {
        Product productBD = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro le producto"));

        Category category = categoryService.findById(product.getCategory().getId());

        productBD.setDescription(product.getDescription() != null ? product.getDescription() : "");
        productBD.setStock(product.getStock() != null ? product.getStock() : 0);
        productBD.setCategory(category);
        productBD.setBrand(product.getBrand() != null ? product.getBrand() : "");
        productBD.setCostPrice(product.getCostPrice() != null ? product.getCostPrice() : BigDecimal.valueOf(0));
        productBD.setSalePrice(product.getSalePrice() != null ? product.getSalePrice() : BigDecimal.valueOf(0));
        productBD.setMeasures(product.getMeasures() != null ? product.getMeasures() : "");
        productBD.setCode(product.getCode());

        return productRepository.save(productBD);

    }
}
