package com.bdp.Cafa.logApproval.repository;

import com.bdp.Cafa.logApproval.model.LogList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogList, Long> {

    List<LogList> findByChangesId(Integer changesId);
}

