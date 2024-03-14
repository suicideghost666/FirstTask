import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.err.println("Выберите количество разрядов: 8 или 10");
        int category = 0;
        while (scan.hasNext()) {
            category = scan.nextInt();
            if (category == 8 || category == 10){
                break;
            }
        }
        System.err.println("Введите количество потоков: ");
        int threads = scan.nextInt();

        long leftBound = (long) Math.pow(10, category - 1);
        long rightBound = (long) Math.pow(10, category) - 1;
        final long stepStatic = (rightBound+1-leftBound) / threads;
        long stepCycle = stepStatic + leftBound;
        ArrayList<ThreadTask> threadTasks = new ArrayList<ThreadTask>();
        ArrayList<Thread> runTasks = new ArrayList<Thread>();
        for (int i = 0; i < threads; i++) {
            threadTasks.add(new ThreadTask(leftBound,stepCycle)) ;
            runTasks.add(new Thread(new RunnableTask(leftBound,stepCycle)));
            if (stepCycle + stepStatic > rightBound) {
                leftBound = stepCycle;
                stepCycle = rightBound;
            } else {
                leftBound = stepCycle;
                stepCycle += stepStatic;
            }
        }

        long finish = 0;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threads; i++) {
            try {
                threadTasks.get(i).start();
                threadTasks.get(i).join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        finish = System.currentTimeMillis();
        printElapsedTime(startTime,finish,"Thread");

        startTime = System.currentTimeMillis();
        for (int i = 0; i < threads; i++) {
            try {
                runTasks.get(i).start();
                runTasks.get(i).join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        finish = System.currentTimeMillis();
        printElapsedTime(startTime,finish,"Runnable");
        RunnableTask run = new RunnableTask();
        run.getEvensAndOdds();

        ThreadTask t = new ThreadTask();
        t.getEvensAndOdds();
    }
    static void printElapsedTime(long start, long finish, String taskType) {
        System.out.println(taskType+" time:\n"+(finish-start)+" ms");
    }
}
// 10 разрядов
// 1 поток: Thread-154616 Runnable-162997
// 6 потоков
// 12 потоков
// 24 потока