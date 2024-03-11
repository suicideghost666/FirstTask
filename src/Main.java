import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.err.println("Выберите количество разрядов: 7 или 10");
        int category = 0;
        while (scan.hasNext()) {
            category = scan.nextInt();
            if (category == 7 || category == 10){
                break;
            }
        }
        System.err.println("Введите количество потоков: ");
        int threads = scan.nextInt();

        long leftBound = (long) Math.pow(10, category - 1);
        long rightBound = (long) Math.pow(10, category) - 1;
        long stepFixed = (rightBound+1-leftBound) / threads; //fix;
        long stepCycle = stepFixed + leftBound;
        long start = 0;
        for (long i = 1; i <= threads; i++) {
            if (i == 1) {
                start = System.currentTimeMillis();
            }
            Thread runTask = new Thread(new RunnableTask(leftBound,stepCycle));
            ThreadTask threadTask = new ThreadTask(leftBound,stepCycle);
            runTask.start();
            threadTask.start();
            try {
                runTask.join();
                threadTask.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (stepCycle + stepFixed > rightBound) {

                leftBound = stepCycle;
                stepCycle = rightBound;
            } else {
                leftBound = stepCycle;
                stepCycle += stepFixed;
            }
        }
        RunnableTask run = new RunnableTask();
        run.getEvensAndOdds();
        run.getDifferenceBetweenTime(start);
        ThreadTask t = new ThreadTask();
        t.getEvensAndOdds();
        t.getDifferenceBetweenTime(start);
    }
}