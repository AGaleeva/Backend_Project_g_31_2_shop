package de.aittr.g_31_2_shop.services.jpa;

import de.aittr.g_31_2_shop.domain.jpa.Task;
import de.aittr.g_31_2_shop.repositories.jpa.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private static TaskRepository repository;
//    private TaskRepository repository;
    private static Logger logger = LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository repository) {
        TaskService.repository = repository;
    }
//    public TaskService(TaskRepository repository) {
//        this.repository = repository;
//    }

   /* public void createTask(String description) {
        logger.info(description);
        Task task = new Task(description);
        repository.save(task);
    }*/

    public static Task createTask(String description) {
        logger.info(description);
        Task task = new Task(description);
        repository.save(task);
        return task;
    }
}
