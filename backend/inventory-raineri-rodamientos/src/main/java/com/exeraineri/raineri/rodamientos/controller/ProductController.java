package com.exeraineri.raineri.rodamientos.controller;

import com.exeraineri.raineri.rodamientos.entity.Product;
import com.exeraineri.raineri.rodamientos.payload.ApiResponse;
import com.exeraineri.raineri.rodamientos.service.implementation.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductServiceImpl productoService;

    @GetMapping
    public ResponseEntity<? extends ApiResponse<?>> findAllProducts(@RequestParam(name = "search", required = false) String search,
                                                                    @PageableDefault(page = 0, size = 50, sort = {"updatedAt"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {

        Page<Product> products = productoService.findAllPageable(search, pageable);

        return new ResponseEntity<>(new ApiResponse<>(
                true,
                HttpStatus.OK.value(),
                "Listado de productos",
                products
        ), HttpStatus.OK);

    }


    @PostMapping
    public ResponseEntity<? extends ApiResponse<?>> saveProduct(@Valid @RequestBody Product product) {
        Product savedProduct = productoService.save(product);
        return new ResponseEntity<>(new ApiResponse<>(
                true,
                HttpStatus.CREATED.value(),
                "Producto guardado",
                savedProduct
        ), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<? extends ApiResponse<?>> findProductById(@PathVariable Long id) {
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
                                                                  @PathVariable Long id) {
        Product updatedProduct = productoService.update(product, id);

        return new ResponseEntity<>(new ApiResponse<>(
                true,
                HttpStatus.OK.value(),
                "Producto actualizado",
                updatedProduct
        ), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<? extends ApiResponse<?>> deleteProduct(@PathVariable Long id) {
        productoService.deleteById(id);

        return new ResponseEntity<>(new ApiResponse<>(
                true,
                HttpStatus.NO_CONTENT.value(),
                "Producto eliminado",
                null
        ), HttpStatus.NO_CONTENT);

    }


    @PostMapping("/importar")
    public ResponseEntity<String> importarProductos(@RequestPart(value = "archivo", required = true) MultipartFile archivo) {
        try {
            productoService.importDataExcel(archivo);
            return ResponseEntity.ok("Importación exitosa.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


}
