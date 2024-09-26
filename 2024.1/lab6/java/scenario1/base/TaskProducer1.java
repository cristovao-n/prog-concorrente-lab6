import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskProducer1 implements Runnable {

    public String id;
    private BlockingQueue<Task1> queue;
    private Long currentId = 0L;
    public List<Task1> tasks = new ArrayList<>();


    public TaskProducer1 (BlockingQueue<Task1> queue, String id) {
        this.id = id;
        this.queue = queue;
    }

    @Override
    public void run() {
        ScheduledExecutorService executorService =
                Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(() -> {
            Task1 task = new Task1(id + "_" + ++currentId);
            tasks.add(task);
            try {
                // adicionar tarefa na fila
                queue.put(task);
            } catch(Exception e) {}

        }, 1, 5, TimeUnit.SECONDS);
    }
}