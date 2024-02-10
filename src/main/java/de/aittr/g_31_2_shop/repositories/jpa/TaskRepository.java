package de.aittr.g_31_2_shop.repositories.jpa;

import de.aittr.g_31_2_shop.domain.jpa.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query(value = "SELECT * FROM (SELECT * FROM 31_2_shop.task ORDER BY executed_at DESC LIMIT 5) AS subquery ORDER BY executed_at ASC", nativeQuery = true)
    List<Task> findTop5ByOrderByExecutedAtDescSortedByAsc();
}
