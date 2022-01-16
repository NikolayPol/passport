package ru.job4j.passport.service;

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
 * @version 1.0 15.01.2022
 */
@Service
@AllArgsConstructor
public class PassportService {
    private final PassportRepository passportRepository;

    public List<Passport> findAll() {
        return passportRepository.findAll();
    }

    public Optional<Passport> findById(long id) {
        return passportRepository.findById(id);
    }

    public List<Passport> findBySeries(int series) {
        return passportRepository.findBySeries(series).orElse(List.of());
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
