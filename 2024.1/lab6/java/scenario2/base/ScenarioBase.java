import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScenarioBase {

    private static BlockingQueue<Task2> queue = new PriorityBlockingQueue<>();

    public static void main(String[] args) {

        TaskProducer2[] taskProducers = new TaskProducer2[] {
                new TaskProducer2(queue, "p1"),
                new TaskProducer2(queue, "p2"),
                new TaskProducer2(queue, "p3"),
        };

        Node2[] taskConsumers = new Node2[] {
                new Node2(queue),
                new Node2(queue),
                new Node2(queue),
        };

        ExecutorService producerService = Executors.newFixedThreadPool(5);
        producerService.execute(taskProducers[0]);
        producerService.execute(taskProducers[1]);
        producerService.execute(taskProducers[2]);
        producerService.execute(taskProducers[3]);
        producerService.execute(taskProducers[4]);

        ExecutorService consumerService = Executors.newFixedThreadPool(3);
        consumerService.execute(taskConsumers[0]);
        consumerService.execute(taskConsumers[1]);
        consumerService.execute(taskConsumers[2]);

        ScheduledExecutorService monitorSchedule = Executors.newSingleThreadScheduledExecutor();

        monitorSchedule.scheduleAtFixedRate(() -> {
            for (TaskProducer2 tp : taskProducers) {
                System.out.println("Produtor: " + tp.id);
                for (Task2 task : tp.tasks) {
                    if (task.hasFinished()) {
                        System.out.println(task.id + ": " + task.getExecutionTime() + "ms");
                    }
                }
                System.out.println();
            }
            System.out.println("\n===========================\n");
        }, 1, 5, TimeUnit.SECONDS);
    }
}
