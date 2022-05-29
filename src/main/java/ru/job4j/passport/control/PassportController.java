package ru.job4j.passport.control;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.passport.model.Passport;
import ru.job4j.passport.service.PassportService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Класс PassportController
 *
 * @author Nikolay Polegaev
 * @version 2.0 29.05.2022
 */
@RestController
//@AllArgsConstructor
@RequestMapping("/passport")
public class PassportController {
    private final PassportService passportService;

    private final GraphQL graphQL;

    @Autowired
    public PassportController(PassportService passportService, GraphQL graphQL) {
        this.passportService = passportService;
        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withBasePackages("com.baeldung")
                .withOperationsFromSingleton(passportService)
                .generate();
        this.graphQL = new GraphQL.Builder(schema)
                .build();
    }

//    @Autowired
//    public GraphqlController(PassportService passportService) {
//        GraphQLSchema schema = new GraphQLSchemaGenerator()
//                .withBasePackages("com.baeldung")
//                .withOperationsFromSingleton(passportService)
//                .generate();
//        this.graphQL = new GraphQL.Builder(schema)
//                .build();
//    }

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
    public ResponseEntity<?> create(
            @RequestBody Map<String, String> request) {
        Passport passport = new Passport();

        //todo вынести в отдельный метод
        if (request.containsKey("query")) {
            String queryEx = request.get("query");
            if(request.containsKey("parameters")) {
                queryEx = queryEx + ", " + request.get("parameters");
                System.out.println(queryEx);
            }
            ExecutionResult result = graphQL.execute(queryEx);
            System.out.println(result.getData().toString());

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.getData());
        }
        passport.setName(request.get("name"));
        passport.setSurname(request.get("surname"));
        passport.setSeries(Integer.valueOf(request.get("series")));
        passport.setNumber(Integer.valueOf(request.get("number")));
//            passport.setIssueDate(Timestamp.valueOf(request.get("issueDate")));
//            passport.setReplacementDate(Timestamp.valueOf(request.get("replacementDate")));


        return new ResponseEntity<>(
                passportService.save(passport),
                HttpStatus.CREATED);
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
