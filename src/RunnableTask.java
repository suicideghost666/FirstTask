public class RunnableTask implements Runnable {
    @Override
    public void run() {
        boolean isEven = true; // even - 2, 4, 6
                               // odd  - 1, 3, 5
        for (long i = leftBound; i <= rightBound; i++) {
            isEven = i % 2 == 0;
            String numStr = String.valueOf(i);
            if (isNeighbours(numStr)) {
                if (isEven) {
                    countEven++;
                }
                else {
                    countOdd++;
                }
            }
        }
    }
    private boolean isNeighbours(String str) {
        for (int j = 1; j < str.length()-1; j++) {
            if(((str.charAt(j)+1 == str.charAt(j-1)) || (str.charAt(j)-1 == str.charAt(j-1)))
                    && ((str.charAt(j)+1 == str.charAt(j+1)) || (str.charAt(j)-1 == str.charAt(j+1))));
            else return false;
        }
        return true;
    }
    long leftBound;
    long rightBound;
    static int countEven;
    static int countOdd;
    public RunnableTask(long leftBound, long rightBound) {
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }
    public RunnableTask(){

    }

    void getEvensAndOdds() {
        System.err.println("Runnable:");
        System.err.println("Чётных: "+countEven +
                " \nНечётных: "+countOdd);
        System.err.println();
    }
}
