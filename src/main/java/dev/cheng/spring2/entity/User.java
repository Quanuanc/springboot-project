package dev.cheng.spring2.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Basic
    @Column(name = "email", nullable = false, length = 250, unique = true)
    private String email;

    @Basic
    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Basic
    @Column(name = "name", length = 50)
    private String name;

    @Basic
    @Column(name = "birth_date", length = 50)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Transient
    private Integer age;

    public Integer getAge() {
        LocalDate now = LocalDate.now();
        long age = birthDate.until(now, ChronoUnit.YEARS);
        return (int) age;
    }
}
