package com.humanResource.humanResource.controller;

import com.humanResource.humanResource.entity.Attendance;
import com.humanResource.humanResource.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    private final AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceController(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @GetMapping
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable Long id) {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        return attendance.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Attendance> createAttendance(@RequestBody Attendance attendance) {
        Attendance newAttendance = attendanceRepository.save(attendance);
        return new ResponseEntity<>(newAttendance, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable Long id, @RequestBody Attendance updatedAttendance) {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        if (attendance.isPresent()) {
            updatedAttendance.setId(id);
            Attendance savedAttendance = attendanceRepository.save(updatedAttendance);
            return new ResponseEntity<>(savedAttendance, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAttendance(@PathVariable Long id) {
        try {
            attendanceRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

