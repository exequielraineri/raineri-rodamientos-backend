package com.exeraineri.raineri.rodamientos.repository;

import com.exeraineri.raineri.rodamientos.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public interface ICategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByName(String name);
}
