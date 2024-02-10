package de.aittr.g_31_2_shop.scheduling;

import de.aittr.g_31_2_shop.domain.jpa.Task;
import de.aittr.g_31_2_shop.repositories.jpa.TaskRepository;
import de.aittr.g_31_2_shop.services.jpa.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableScheduling
public class SchedulerHwTasks {

    private TaskService taskService;
    private TaskRepository repository;
    private static Logger logger = LoggerFactory.getLogger(SchedulerHwTasks.class);

    public SchedulerHwTasks(TaskService taskService, TaskRepository repository) {
        this.taskService = taskService;
        this.repository = repository;
    }

    /*@Scheduled(fixedRate = 30000)
    public void delayedTaskEvery30Sec() {
        System.out.println(new Date(System.currentTimeMillis()));
        System.out.println(repository.findTop5ByOrderByExecutedAtDescSortedByAsc());
    }*/

    public static void scheduleAndExecuteTask(Task task) {
        TaskScheduler scheduler = new DefaultManagedTaskScheduler();
        scheduler.schedule(
                () -> {
                    TaskService.createTask(task.getDescription());
                    logger.info(task.getDescription());
                },
                new CronTrigger("15,45 * * * * *")
        );
    }
}
