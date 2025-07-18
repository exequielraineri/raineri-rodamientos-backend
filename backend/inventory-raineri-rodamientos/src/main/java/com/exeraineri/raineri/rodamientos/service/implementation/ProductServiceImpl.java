package com.exeraineri.raineri.rodamientos.service.implementation;

import com.exeraineri.raineri.rodamientos.entity.Product;
import com.exeraineri.raineri.rodamientos.exception.ResourceNotFoundException;
import com.exeraineri.raineri.rodamientos.repository.IProductRepository;
import com.exeraineri.raineri.rodamientos.service.interfaces.ICategoryService;
import com.exeraineri.raineri.rodamientos.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    private final ICategoryService categoryService;


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Page<Product> findAllPageable(String search, Pageable pageable) {
        return productRepository.findByCodeContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search, pageable);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro le producto"));
    }

    @Override
    public Product save(Product product) {
        //Category category = categoryService.findById(product.getCategory().getId());
        //product.setCategory(category);
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

        //Category category = categoryService.findById(product.getCategory().getId());

        productBD.setDescription(product.getDescription() != null ? product.getDescription() : "");
        productBD.setStock(product.getStock() != null ? product.getStock() : 0);
        // productBD.setCategory(category);
        productBD.setBrand(product.getBrand() != null ? product.getBrand() : "");
        productBD.setCostPrice(product.getCostPrice() != null ? product.getCostPrice() : BigDecimal.valueOf(0));
        productBD.setSalePrice(product.getSalePrice() != null ? product.getSalePrice() : BigDecimal.valueOf(0));
        productBD.setCode(product.getCode());

        return productRepository.save(productBD);
    }


    public void importDataExcel(MultipartFile archivoExcel) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(archivoExcel.getInputStream())) {
            Sheet hoja = workbook.getSheetAt(0);

            for (int i = 1; i <= hoja.getLastRowNum(); i++) { // Empieza en 1 para saltar el header
                Row fila = hoja.getRow(i);
                if (fila == null) continue;

                Product product = new Product();
                product.setCode(getString(fila.getCell(0)));
                product.setDescription(getString(fila.getCell(1)));
                product.setBrand(getString(fila.getCell(2)));
                product.setCostPrice(parseBigDecimal(fila.getCell(3)));
                product.setStock(0);

                productRepository.save(product);
            }
        }
    }

    private String getString(Cell cell) {
        return cell == null ? "" : cell.getCellType() == CellType.STRING
                ? cell.getStringCellValue()
                : String.valueOf((int) cell.getNumericCellValue());
    }

    private BigDecimal parseBigDecimal(Cell cell) {
        if (cell == null) return BigDecimal.ZERO;
        String valor = cell.getCellType() == CellType.STRING
                ? cell.getStringCellValue().replace(",", ".")
                : String.valueOf(cell.getNumericCellValue());
        return new BigDecimal(valor);
    }


}
