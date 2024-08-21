package dev.cheng.spring2.entity;

import java.time.LocalDate;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Company {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String address;
    private String phone;
    private LocalDate expiryDate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "contact_first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "contact_last_name")),
            @AttributeOverride(name = "phone", column = @Column(name = "contact_phone"))
    })
    private ContactPerson contactPerson;
}
