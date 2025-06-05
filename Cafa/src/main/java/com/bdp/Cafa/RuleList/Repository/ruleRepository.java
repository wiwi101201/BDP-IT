package com.bdp.Cafa.RuleList.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ruleRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE %:searchTerm% OR LOWER(p.description) LIKE %:searchTerm%")
    List<Product> searchProducts(@Param("searchTerm") String searchTerm);
}