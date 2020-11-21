package com.my.jasuil.data.repository;

import com.my.jasuil.data.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findAllByIdIn(List<Integer> list);
}
