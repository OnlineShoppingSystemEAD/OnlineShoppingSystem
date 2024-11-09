package com.example.product_management.repository;

import com.example.product_management.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM items WHERE categoryId=:categoryId")
    List<Item> findByCategoryId(@Param("categoryId") int categoryId);

}
