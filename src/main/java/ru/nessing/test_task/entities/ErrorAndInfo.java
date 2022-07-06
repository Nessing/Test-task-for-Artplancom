package ru.nessing.test_task.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "errors_and_info")
public class ErrorAndInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "info")
    private String info;

    public ErrorAndInfo(Long id, String info) {
        this.id = id;
        this.info = info;
    }
}
