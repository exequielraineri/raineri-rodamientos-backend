package com.exeraineri.raineri.rodamientos.service.interfaces;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IBasicService<T> {

    @Transactional(readOnly = true)
    List<T> findAll();

    @Transactional(readOnly = true)
    T findById(Long id);

    @Transactional
    T save(T element);

    @Transactional
    void deleteById(Long id);

    @Transactional
    T update(T element, Long id);
}
