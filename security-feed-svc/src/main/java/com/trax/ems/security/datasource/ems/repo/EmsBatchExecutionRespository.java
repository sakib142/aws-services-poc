
package com.trax.ems.security.datasource.ems.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trax.ems.security.datasource.ems.domain.EmsBatchExecution;

@Repository
public interface EmsBatchExecutionRespository extends JpaRepository<EmsBatchExecution, String> {

}
