package ru.job4j.passport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.job4j.passport.model.Passport;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс PersonRepository
 *
 * @author Nikolay Polegaev
 * @version 1.0 15.01.2022
 */
public interface PassportRepository extends JpaRepository<Passport, Long> {
    Optional<List<Passport>> findBySeries(int series);

    @Query(value = "SELECT * FROM passport.passport "
            + "WHERE passport.replacement_date < current_timestamp ",
    nativeQuery = true)
    Optional<List<Passport>> findUnavaliabePassports();

    @Query(value = "SELECT * FROM passport.passport "
            + "WHERE replacement_date BETWEEN current_timestamp "
            + "AND current_timestamp + INTERVAL '3 month'",
    nativeQuery = true)
    Optional<List<Passport>> findReplaceablePassports();

    Optional<Passport> findPassportByNumber(int number);

    @Query("select p from Passport p where p.name = :name1")
    Optional<Passport> findPassportByName(String name1);
}
