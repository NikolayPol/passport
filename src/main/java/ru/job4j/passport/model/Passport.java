package ru.job4j.passport.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Класс PassportEntity
 *
 * @author Nikolay Polegaev
 * @version 1.0 15.01.2022
 */
@Entity
@Table(name = "passport", schema = "passport", catalog = "passport")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private Integer series;
    private Integer number;
    private Timestamp issueDate;
    private Timestamp replacementDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Passport passport = (Passport) o;
        return id == passport.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Passport{"
                + "id=" + id
                + ", series=" + series
                + ", number=" + number
                + '}';
    }
}
