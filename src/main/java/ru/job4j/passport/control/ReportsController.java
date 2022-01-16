package ru.job4j.passport.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.passport.model.Passport;
import java.util.List;

/**
 * Класс ReportsController
 *
 * @author Nikolay Polegaev
 * @version 1.0 15.01.2022
 */
@RestController
@RequestMapping("/report")
public class ReportsController {
    private static final String API = "http://localhost:8080/passport/";
    private static final String API_ID = "http://localhost:8080/passport/{id}";

    @Autowired
    private RestTemplate rest;

    @GetMapping("/")
    public List<Passport> findAll() {
        return rest.exchange(
                API,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
    }

    @GetMapping("/seria/{seria}")
    public List<Passport> findBySeria(@PathVariable int seria) {
        return rest.getForObject(API + "/seria/" + seria, List.class);
    }

    @GetMapping("/unavaliabe")
    public List<Passport> findUnavaliabePassports() {
        return rest.getForObject(API + "/unavaliabe", List.class);
    }

    @GetMapping("/replaceable")
    public List<Passport> findReplaceablePassports() {
        return rest.getForObject(API + "/replaceable", List.class);
    }

    @PostMapping("/")
    public ResponseEntity<Passport> create(@RequestBody Passport passport) {
        Passport rsl = rest.postForObject(API, passport, Passport.class);
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Passport passport) {
        rest.put(API, passport);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        rest.delete(API_ID, id);
        return ResponseEntity.ok().build();
    }
}
