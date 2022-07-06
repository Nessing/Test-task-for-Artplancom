package ru.nessing.test_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nessing.test_task.entities.Animal;


@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Animal getAnimalById(Long id);
    Animal getAnimalByName(String name);
}
