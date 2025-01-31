package com.exeraineri.raineri.rodamientos.controller;

import com.exeraineri.raineri.rodamientos.entity.Category;
import com.exeraineri.raineri.rodamientos.payload.ApiResponse;
import com.exeraineri.raineri.rodamientos.service.interfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/categories")
@CrossOrigin("*")
@Slf4j
public class CategoryController {

    private final ICategoryService categoryService;


    @GetMapping
    public ResponseEntity<? extends ApiResponse<?>> findAllCategories(){
        List<Category> categories = categoryService.findAll();
        return new ResponseEntity<>(new ApiResponse<>(
                true,
                HttpStatus.OK.value(),
                "Listado de categorias",
                categories
        ),HttpStatus.OK);
    }
}
