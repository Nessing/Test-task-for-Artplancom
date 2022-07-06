package ru.nessing.test_task.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    private String birthday;

    @OneToOne
    @JoinColumn(name = "sex_id", referencedColumnName = "id")
    private Sex sex;

    @OneToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private TypeOfAnimal typeOfAnimal;

}
