import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExample {

    public static void main(String[] args) {
        // Create a fixed thread pool with 4 threads
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        // Submit tasks to the thread pool
        // So here 10 tasks are assingned to 4 Threads
        //The task is the print statement
        for (int i = 0; i < 10; i++) {
            int taskNumber = i;
            executorService.submit(() -> {
                System.out.println("Executing task " + taskNumber + " by thread " + Thread.currentThread().getName());
            });
        }

        // Shutdown the executor service
        executorService.shutdown();
    }
}


