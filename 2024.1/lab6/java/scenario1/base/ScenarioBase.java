import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScenarioBase {

    private static BlockingQueue<Task1> queue = new LinkedBlockingDeque<>();

    public static void main(String[] args) {

        TaskProducer1[] taskProducers = new TaskProducer1[] {
                new TaskProducer1(queue, "p1"),
                new TaskProducer1(queue, "p2"),
                new TaskProducer1(queue, "p3"),
                new TaskProducer1(queue, "p4"),
                new TaskProducer1(queue, "p5"),
        };

        Node1[] taskConsumers = new Node1[] {
                new Node1(queue),
                new Node1(queue),
                new Node1(queue),
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
            for (TaskProducer1 tp : taskProducers) {
                System.out.println("Produtor: " + tp.id);
                for (Task1 task : tp.tasks) {
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
