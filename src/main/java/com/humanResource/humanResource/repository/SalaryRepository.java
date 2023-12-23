package com.humanResource.humanResource.repository;
import com.humanResource.humanResource.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    Salary findByEmployeeId(Long employeeId);
}

