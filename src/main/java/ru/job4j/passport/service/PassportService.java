package ru.job4j.passport.service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.passport.model.Passport;
import ru.job4j.passport.repository.PassportRepository;

import java.util.List;
import java.util.Optional;

/**
 * Класс PassportService
 *
 * @author Nikolay Polegaev
 * @version 2.0 29.05.2022
 */
@Service
@AllArgsConstructor
@GraphQLApi
public class PassportService {
    private final PassportRepository passportRepository;

    public List<Passport> findAll() {
        return passportRepository.findAll();
    }

    @GraphQLQuery(name = "getPassportById")
    public Optional<Passport> findById(@GraphQLArgument(name = "id") long id) {
        return passportRepository.findById(id);
    }

    public List<Passport> findBySeries(int series) {
        return passportRepository.findBySeries(series).orElse(List.of());
    }

    @GraphQLQuery(name = "getPassportByNumber")
    public Optional<Passport> findByNumber(
            @GraphQLArgument(name = "number") int number) {
        return passportRepository.findPassportByNumber(number);
    }

    @GraphQLQuery(name = "getPassportByName")
    public Optional<Passport> findByName(
            @GraphQLArgument(name = "name") String name) {
        return passportRepository.findPassportByName(name);
    }

    public List<Passport> findUnavaliabePassports() {
        return passportRepository.findUnavaliabePassports().orElse(List.of());
    }

    public List<Passport> findReplaceablePassports() {
        return passportRepository.findReplaceablePassports().orElse(List.of());
    }

    public Passport save(Passport passport) {
        return passportRepository.save(passport);
    }

    public void update(Passport passport) {
        Optional<Passport> personFromDB = passportRepository
                .findById(passport.getId());
        if (personFromDB.isPresent()) {
            passport.setId(personFromDB.get().getId());
            passportRepository.save(passport);
        } else {
            passportRepository.save(passport);
        }
    }

    public void delete(long id) {
        passportRepository.delete(passportRepository.findById(id).get());
    }
}
