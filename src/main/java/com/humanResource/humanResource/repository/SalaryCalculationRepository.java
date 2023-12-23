package com.humanResource.humanResource.repository;

import com.humanResource.humanResource.entity.SalaryCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SalaryCalculationRepository extends JpaRepository<SalaryCalculation, Long> {
    List<SalaryCalculation> findByCalculationDateBetween(Date startDate, Date endDate);
}

