package ru.nessing.test_task.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "attempt")
    private Integer attempt;

    @Column(name = "time_count_start")
    private Long timeCountStart;

    @Column(name = "time_count_end")
    private Long timeCountEnd;

    @OneToMany
    @JoinTable(name = "animals_of_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_id"))
    private Set<Animal> animals;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
