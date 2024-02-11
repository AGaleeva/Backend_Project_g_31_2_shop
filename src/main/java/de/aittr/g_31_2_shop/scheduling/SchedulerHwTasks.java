package de.aittr.g_31_2_shop.scheduling;

import de.aittr.g_31_2_shop.domain.jpa.JpaProduct;
import de.aittr.g_31_2_shop.domain.jpa.Task;
import de.aittr.g_31_2_shop.repositories.jpa.JpaProductRepository;
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

@Component
@EnableScheduling
public class SchedulerHwTasks {

    private TaskService taskService;
    private TaskRepository taskRepository;
    private JpaProductRepository productRepository;
    private static Logger logger = LoggerFactory.getLogger(SchedulerHwTasks.class);

    public SchedulerHwTasks(TaskService taskService, TaskRepository taskRepository, JpaProductRepository productRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
        this.productRepository = productRepository;
    }

    /*@Scheduled(fixedRate = 30000)
    public void delayedTaskEvery30Sec() {
        System.out.println(new Date(System.currentTimeMillis()));
        System.out.println(repository.findTop5ByOrderByExecutedAtDescSortedByAsc());
    }*/

    public Task createSavedTask() {
        JpaProduct product=  productRepository.findFirstByOrderByIdDesc();
        Task task = TaskService.createTask("The last saved product is %s".formatted(product.getName()));
        return task;
    }

    @Scheduled(cron = "15,45 * * * * *")
    public void scheduleAndExecuteTask() {
       Task task = createSavedTask();
    }
}
