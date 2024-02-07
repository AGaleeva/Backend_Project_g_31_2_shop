package de.aittr.g_31_2_shop.scheduling;

import de.aittr.g_31_2_shop.domain.jpa.Task;
import de.aittr.g_31_2_shop.services.jpa.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@EnableScheduling
@EnableAsync
public class ScheduleExecutor {

    private TaskService taskService;
    private static Logger logger = LoggerFactory.getLogger(ScheduleExecutor.class);

    public ScheduleExecutor(TaskService taskService) {
        this.taskService = taskService;
    }

    /*@Scheduled(fixedDelay = 10000)
    public void fixedDelayTask() {
        taskService.createTask("Fixed delay task");
    }*/

   /* @Scheduled(fixedDelay = 10000)
    public void fixedDelayTask() {
        taskService.createTask("Fixed delay task that runs in 3000 ms");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/

    /*@Scheduled(fixedDelay = 5000)
    public void fixedDelayTask() {
        taskService.createTask("Fixed delay task that runs in 7000 ms");
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/

    /*@Scheduled(fixedRate = 10000)
    public void fixedRateTask() {
        taskService.createTask("Fixed rate task that runs in 3000 ms");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/

    /*@Scheduled(fixedRate = 5000) // здесь они выполняются в однопоточном режиме, т.е. 2-я стартанет после 1-й
    public void fixedRateTask() {
        taskService.createTask("Fixed rate task that runs in 7000 ms");
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/

    /*@Scheduled(fixedRate = 5000)
    @Async
    public void fixedRateAsyncTask() {
        taskService.createTask("Fixed rate async task that runs in 7000 ms");
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/

    /*@Scheduled(fixedDelay = 5000, initialDelay = 20000)
    public void fixedDelayScheduledTask() {
        taskService.createTask("Fixed delay scheduled task with initial delay");
    }*/

    // 2 hours -> 7_200_000 ms -> PT2H (period of time - PT 2 hours; s - seconds)

    /*@Scheduled(fixedDelayString = "PT5S")
    public void anotherDelayScheduledTask() {
        taskService.createTask("Another delay format task");
    }*/

    // 55 * * * * * -> every minute at :55 sec
    // 0 10,20 * * * * -> every hour at 10 and 20 min
    // 0 15 9-17 * * MON-FRI -> по будням с 9 до 17 в 15 минут каждого часа

    /*@Scheduled(cron = "10,30 * * * * *")
    public void cronExpressionTask() {
        taskService.createTask("Cron expression task");
    }*/

    /*public static void scheduleAndExecuteTask(Task task) {
        TaskScheduler scheduler = new DefaultManagedTaskScheduler();
        scheduler.schedule(
                () -> logger.info(task.getDescription()),
                new CronTrigger("0,10,20,30,40,50 * * * * *")
        );
    }*/

    public static void scheduleAndExecuteTask(Task task) {
        TaskScheduler scheduler = new DefaultManagedTaskScheduler();
        Instant instant = Instant.now().plusSeconds(30);

        scheduler.schedule(
                () -> logger.info(task.getDescription()),
                instant
        );
    }
}
