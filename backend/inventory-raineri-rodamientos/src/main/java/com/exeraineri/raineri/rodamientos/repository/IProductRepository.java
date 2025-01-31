package com.exeraineri.raineri.rodamientos.repository;

import com.exeraineri.raineri.rodamientos.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategoryId(Long id);

    Page<Product> findAll(Pageable pageable);

    @Override
    List<Product> findAll(Sort sort);


}
