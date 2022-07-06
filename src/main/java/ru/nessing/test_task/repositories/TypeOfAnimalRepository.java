package ru.nessing.test_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nessing.test_task.entities.TypeOfAnimal;

@Repository
public interface TypeOfAnimalRepository extends JpaRepository<TypeOfAnimal, Long> {
    TypeOfAnimal getTypeOfAnimalByName(String name);
}
