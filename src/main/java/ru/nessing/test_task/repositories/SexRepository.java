package ru.nessing.test_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nessing.test_task.entities.Sex;

@Repository
public interface SexRepository extends JpaRepository<Sex, Long> {
    Sex getSexByName(String name);
}
