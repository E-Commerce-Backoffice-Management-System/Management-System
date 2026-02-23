package com.managementSystem.product.repository;
import com.managementSystem.product.entity.Product;
import com.managementSystem.product.enums.Category;
import com.managementSystem.product.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
        SELECT p FROM Product p
        WHERE (:productName IS NULL OR p.productName LIKE %:name%)
        AND (:category IS NULL OR p.category = :category)
        AND (:status IS NULL OR p.status = :status)
""")
    Page<Product> findByProductNameContaining(String productName, Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);

    Page<Product> findByStatus(Status status, Pageable pageable);

    Page<Product> findByProductNameAndCategory(String productName, Category category, Pageable pageable);

    Page<Product> findByProductNameAndStatus(String productName, Status status, Pageable pageable);

    Page<Product> findByCategoryAndStatus(Category category, Status status, Pageable pageable);

    Page<Product> findByProductNameAndCategoryAndStatus(String productName, Category category, Status status, Pageable pageable);
}
