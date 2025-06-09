package com.bdp.Cafa.RuleList.Repository;
import com.bdp.Cafa.RuleList.Model.RuleList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RuleRepository extends JpaRepository<RuleList, Long> {
    // Cara 1: pakai Spring Data query method
    List<RuleList> findByNameContaining(String keyword);

    List<RuleList> findAll();

    @Query("SELECT r FROM RuleList r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<RuleList> searchRules(@Param("keyword") String keyword);
}