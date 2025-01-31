package com.exeraineri.raineri.rodamientos.controller;

import com.exeraineri.raineri.rodamientos.entity.Product;
import com.exeraineri.raineri.rodamientos.payload.ApiResponse;
import com.exeraineri.raineri.rodamientos.service.interfaces.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {

    private final IProductService productoService;

    @GetMapping
    public ResponseEntity<? extends ApiResponse<?>> findAllProducts() {
            List<Product> products = productoService.findAll();

            return new ResponseEntity<>(new ApiResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    "Listado de productos",
                    products
            ), HttpStatus.OK);

    }


    @PostMapping
    public ResponseEntity<? extends ApiResponse<?>> saveProduct(@Valid @RequestBody Product product){
            Product savedProduct = productoService.save(product);
            return new ResponseEntity<>(new ApiResponse<>(
                    true,
                    HttpStatus.CREATED.value(),
                    "Producto guardado",
                    savedProduct
            ), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<? extends ApiResponse<?>> findProductById(@PathVariable Long id){
            Product product = productoService.findById(id);
            return new ResponseEntity<>(new ApiResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    "Producto encontrado",
                    product
            ), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<? extends ApiResponse<?>> updateProduct(@RequestBody Product product,
                                                                  @PathVariable Long id){
        Product updatedProduct = productoService.update(product, id);

        return new ResponseEntity<>(new ApiResponse<>(
                true,
                HttpStatus.OK.value(),
                "Producto actualizado",
                updatedProduct
        ),HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<? extends ApiResponse<?>> deleteProduct(@PathVariable Long id){
       productoService.deleteById(id);

        return new ResponseEntity<>(new ApiResponse<>(
                true,
                HttpStatus.NO_CONTENT.value(),
                "Producto eliminado",
                null
        ),HttpStatus.NO_CONTENT);

    }
}
