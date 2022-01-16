package ru.job4j.passport.control;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.passport.model.Passport;
import ru.job4j.passport.service.PassportService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Класс PassportController
 *
 * @author Nikolay Polegaev
 * @version 1.0 15.01.2022
 */
@RestController
@AllArgsConstructor
@RequestMapping("/passport")
public class PassportController {
    private final PassportService passportService;

    @GetMapping("/")
    public List<Passport> findAll() {
        return StreamSupport.stream(
                this.passportService.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passport> findById(@PathVariable int id) {
        var passport = this.passportService.findById(id);
        return new ResponseEntity<Passport>(
                passport.orElse(new Passport()),
                passport.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/seria/{seria}")
    public ResponseEntity<List<Passport>> findBySeria(
            @PathVariable int seria) {
        List<Passport> passports = passportService.findBySeries(seria);
        if (passports.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                passports,
                HttpStatus.OK
        );
    }

    @GetMapping("/unavaliabe")
    public ResponseEntity<List<Passport>> findUnavailable() {
        List<Passport> passports = passportService.findUnavaliabePassports();
        return new ResponseEntity<>(passports,
                passports.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/replaceable")
    public ResponseEntity<List<Passport>> findReplaceablePassports() {
        List<Passport> passports = passportService.findReplaceablePassports();
        return new ResponseEntity<>(passports,
                passports.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Passport> create(@RequestBody Passport passport) {
        return new ResponseEntity<Passport>(
                passportService.save(passport),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Passport passport) {
        passportService.update(passport);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        passportService.delete(id);
        return ResponseEntity.ok().build();
    }
}
