package dev.cheng.spring2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "contact_first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "contact_last_name")),
            @AttributeOverride(name = "phone", column = @Column(name = "contact_phone"))
    })
    private ContactPerson contactPerson;
}
