package com.exeraineri.raineri.rodamientos.service.implementation;

import com.exeraineri.raineri.rodamientos.entity.Category;
import com.exeraineri.raineri.rodamientos.exception.ResourceNotFoundException;
import com.exeraineri.raineri.rodamientos.repository.ICategoryRepository;
import com.exeraineri.raineri.rodamientos.service.interfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro la categoria"));
    }

    @Override
    public Category save(Category element) {
        return categoryRepository.save(element);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);

    }

    @Override
    public Category update(Category category, Long id) {
        Category categoryBD = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro la categoria"));

        categoryBD.setName(category.getName());
        return categoryRepository.save(categoryBD);
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name)
                .orElse(null);
    }
}
