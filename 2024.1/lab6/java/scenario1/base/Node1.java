import java.util.concurrent.BlockingQueue;

public class Node1 implements Runnable {

    private BlockingQueue<Task1> queue;

    public Node1(BlockingQueue<Task1> queue) {
      this.queue = queue;
    }

    @Override
    public void run() {
      while (true) {
        try {
          Task1 task = queue.take();
          task.execute();
        } catch (InterruptedException e) {}
      }
    }
}
