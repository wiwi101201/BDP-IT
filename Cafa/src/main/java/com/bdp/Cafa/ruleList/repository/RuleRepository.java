package com.bdp.Cafa.ruleList.repository;
import com.bdp.Cafa.ruleList.model.RuleList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RuleRepository extends JpaRepository<RuleList, Long> {
    @Query("SELECT r FROM RuleList r")
    List<RuleList> findAll();

    @Query("SELECT r FROM RuleList r WHERE LOWER(r.configkey) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(r.configtype) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<RuleList> searchRules(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT(r) FROM RuleList r WHERE r.configkey ILIKE :keyword")
    RuleList searchRule(@Param("keyword") String keyword);
}