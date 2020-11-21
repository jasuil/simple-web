package com.my.jasuil.data;

import com.my.jasuil.data.entities.Product;
import com.my.jasuil.data.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class ProductJpaTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @Rollback
    public void selectTest() {
        Product product = new Product();
        product.setCategory("ㅎㅎㅎ");
        product.setName("성일짱");
        product.setStock(200);
        product.setPrice(2300);
        product.setCreatedAt(new Date());
        productRepository.save(product);
        List<Product> productList = (List) productRepository.findAll();

        Assertions.assertTrue(productList.get(0).getCategory().equals("ㅎㅎㅎ"));
    }
}
