import java.util.Random;

public class Task1 {
    String id;
    private Long startingTime;
    private Long finishTime;
    

    public Task1(String id) {
        this.id = id;
        this.startingTime = System.currentTimeMillis();;
    }

    public void execute() {
        try {
            // generating a number between 1000 and 15000
            long execDuration = 1000 + (long) (new Random().nextFloat() * (15000 - 1000));
            Thread.sleep(execDuration);
            this.finishTime = System.currentTimeMillis();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean hasFinished() {
        return finishTime != null;
    }

    public Long getExecutionTime() {
        if (hasFinished()) {
            return finishTime - startingTime;
        }
        return null;
    }

}
