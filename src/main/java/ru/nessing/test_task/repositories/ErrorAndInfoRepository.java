package ru.nessing.test_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nessing.test_task.entities.ErrorAndInfo;

@Repository
public interface ErrorAndInfoRepository extends JpaRepository<ErrorAndInfo, Long> {
    ErrorAndInfo getErrorById(Long id);
}
