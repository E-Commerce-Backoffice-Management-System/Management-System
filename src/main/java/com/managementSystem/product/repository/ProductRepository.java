package com.managementSystem.product.repository;
import com.managementSystem.product.dto.GetAllProductResponse;
import com.managementSystem.product.entity.Product;
import com.managementSystem.product.enums.Category;
import com.managementSystem.product.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
        SELECT new com.managementSystem.product.dto.GetAllProductResponse(
            p.id, p.name, p.category, p.price, p.stock, p.status, p.createdAt, a.name
        )
        FROM Product p
        JOIN p.createdBy a
        WHERE (:name IS NULL OR p.name LIKE %:name%)
        AND (:category IS NULL OR p.category = :category)
        AND (:status IS NULL OR p.status = :status)
    """)
    Page<GetAllProductResponse> findAllProducts(
            @Param("name") String name,
            @Param("category") Category category,
            @Param("status") Status status,
            Pageable pageable
    );

    Long countByDeletedFalse();

    Long countByStockLessThanEqualAndDeletedFalse(int i);
}
