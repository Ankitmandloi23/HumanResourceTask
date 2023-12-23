package com.humanResource.humanResource.repository;
import com.humanResource.humanResource.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByEmployeeIdAndMonth(Long employeeId, LocalDate startOfMonth);

}

