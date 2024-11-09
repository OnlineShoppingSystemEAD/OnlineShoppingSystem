package main.java.com.example.order_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.order.management.model.ShoppingCartItem;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCarItem, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM shoppingCartItems WHERE sc.userid = :userid;")
    List<ShoppingCartItem> findByUserId(@Param("userId") int userId);
}
