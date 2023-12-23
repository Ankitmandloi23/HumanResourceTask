package com.humanResource.humanResource.controller;
import com.humanResource.humanResource.entity.Salary;
import com.humanResource.humanResource.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/salaries")
public class SalaryController {

    private final SalaryRepository salaryRepository;

    @Autowired
    public SalaryController(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    @GetMapping
    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salary> getSalaryById(@PathVariable Long id) {
        Optional<Salary> salary = salaryRepository.findById(id);
        return salary.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Salary> createSalary(@RequestBody Salary salary) {
        Salary newSalary = salaryRepository.save(salary);
        return new ResponseEntity<>(newSalary, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Salary> updateSalary(@PathVariable Long id, @RequestBody Salary updatedSalary) {
        Optional<Salary> salary = salaryRepository.findById(id);
        if (salary.isPresent()) {
            updatedSalary.setId(id);
            Salary savedSalary = salaryRepository.save(updatedSalary);
            return new ResponseEntity<>(savedSalary, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSalary(@PathVariable Long id) {
        try {
            salaryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
