package com.humanResource.humanResource.controller;
import com.humanResource.humanResource.entity.Position;
import com.humanResource.humanResource.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/positions")
public class PositionController {

    private final PositionRepository positionRepository;

    @Autowired
    public PositionController(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @GetMapping
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getPositionById(@PathVariable Long id) {
        Optional<Position> position = positionRepository.findById(id);
        return position.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Position> createPosition(@RequestBody Position position) {
        Position newPosition = positionRepository.save(position);
        return new ResponseEntity<>(newPosition, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable Long id, @RequestBody Position updatedPosition) {
        Optional<Position> position = positionRepository.findById(id);
        if (position.isPresent()) {
            updatedPosition.setId(id);
            Position savedPosition = positionRepository.save(updatedPosition);
            return new ResponseEntity<>(savedPosition, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePosition(@PathVariable Long id) {
        try {
            positionRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
