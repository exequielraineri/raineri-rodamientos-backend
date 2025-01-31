package com.exeraineri.raineri.rodamientos.service.interfaces;

import com.exeraineri.raineri.rodamientos.entity.Category;

public interface ICategoryService extends IBasicService<Category> {

    Category findByName(String name);
}
