package com.medinastr.payments.repository;

import com.medinastr.payments.model.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {

    @Query(value = "SELECT p FROM Products p WHERE p.price < :price")
    List<Products> findLowPrices(Float price);

    List<Products> findAllByPriceLessThan(Float price);
}
