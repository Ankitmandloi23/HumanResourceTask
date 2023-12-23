package com.humanResource.humanResource.controller;


import com.humanResource.humanResource.entity.Attendance;
import com.humanResource.humanResource.entity.Employee;
import com.humanResource.humanResource.entity.Salary;
import com.humanResource.humanResource.entity.SalaryCalculation;
import com.humanResource.humanResource.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component
public class SalaryCalculationScheduler {

    private final SalaryCalculationRepository salaryCalculationRepository;
    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final SalaryRepository salaryRepository;

    @Autowired
    public SalaryCalculationScheduler(SalaryCalculationRepository salaryCalculationRepository,
                                      EmployeeRepository employeeRepository,
                                      AttendanceRepository attendanceRepository,
                                      SalaryRepository salaryRepository) {
        this.salaryCalculationRepository = salaryCalculationRepository;
        this.employeeRepository = employeeRepository;
        this.attendanceRepository = attendanceRepository;
        this.salaryRepository = salaryRepository;
    }

    @Scheduled(cron = "0 0 0 1 * *") // Run at midnight on the first day of every month
    public void calculateMonthlySalary() {
        // Check if salary calculation for the current month has already been completed
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1); // Get previous month
        Date startDate = calendar.getTime();

        List<SalaryCalculation> existingCalculations = salaryCalculationRepository.findByCalculationDateBetween(
                startDate, new Date());
        LocalDate currentDate = LocalDate.now();
        if (existingCalculations.isEmpty()) {
            List<Employee> employees = employeeRepository.findAll();
            for (Employee employee : employees) {

                List<Attendance> attendances = attendanceRepository.findByEmployeeIdAndMonth(employee.getId(), currentDate.minusMonths(1));

                // Calculate salary for the employee based on attendance and salary table formulas
                double calculatedSalary = calculateSalaryFromAttendance(attendances, employee);

                // Update the salary record for the employee
                Salary salary = salaryRepository.findByEmployeeId(employee.getId());
                salary.setGrossSalary(calculatedSalary);
                salaryRepository.save(salary);
            }
            SalaryCalculation newCalculation = new SalaryCalculation();

            newCalculation.setCalculationDate(startDate);
            newCalculation.setStatus(Status.Completed); // Mark as completed
            newCalculation.setCreatedAt(new Date()); // Set creation date
            newCalculation.setUpdatedAt(new Date()); // Set updated date
            salaryCalculationRepository.save(newCalculation);
        }
    }


    public double calculateSalaryFromAttendance(List<Attendance> attendances, Employee employee) {
        Salary salary = new Salary();
        double basicSalary =  salary.getBasicSalary();// Assuming you have a salary object associated with each employee


        int totalWorkingDays = calculateTotalWorkingDays(attendances); // Implement this method
        int totalWorkingHours = calculateTotalWorkingHours(attendances); // Implement this method

        double salaryy = basicSalary*1000;

        // Additional calculations such as adding HRA, DA, allowances, etc.
        // salary += ...

        return salaryy;
    }

    private int calculateTotalWorkingDays(List<Attendance> attendances) {
      return 1*10000;
    }

    private int calculateTotalWorkingHours(List<Attendance> attendances) {
        // Logic to calculate the total number of working hours attended
        // Similar to calculateTotalWorkingDays method, implement the logic to sum up total working hours from attendance records
        return 2*200000;
    }


}
