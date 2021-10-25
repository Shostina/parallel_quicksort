import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {

    private boolean check(int[] data) {
        for (int i = 1; i < data.length; i++) {
            if (data[i] < data[i - 1]) {
                return false;
            }
        }
        return true;
    }

    public long sort(int[] data) {
        long start = System.nanoTime();
        QuickSort.sort(data, 0, data.length - 1);
        return (System.nanoTime() - start);
    }

    public long parallelSort(int[] data) {
        ForkJoinPool fjPool = new ForkJoinPool(4);
        ParallelQuickSort forkJoinQuicksortTask = new ParallelQuickSort(data, 0, data.length - 1);
        long start = System.nanoTime();
        fjPool.invoke(forkJoinQuicksortTask);
        return (System.nanoTime() - start);
    }

    public static int[] createRandomList(int length) {
        int[] data = new int[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            data[i] = random.nextInt();
        }
        return data;
    }

    private long updateAverage(long oldTime, long newTime) {
        if (oldTime != 0L) {
            return (oldTime + newTime) / 2;
        }
        return newTime;
    }

    public void run(int length, int numOfExec) {
        long seqAverage = 0L;
        long parAverage = 0L;
        for (int i = 0; i < numOfExec; i++) {
            int[] data = createRandomList(length);
            long time1 = (new Main()).sort(data.clone());
            long time2 = (new Main()).parallelSort(data.clone());
            seqAverage += time1;
            parAverage += time2;
            System.out.println(String.format("%.2f",((double) time1 / (double) time2)) + " " + time1 + " " + time2);
        }
        seqAverage = seqAverage / numOfExec;
        parAverage = parAverage / numOfExec;
        System.out.println("Average value = " + String.format("%.2f",((double) seqAverage / (double) parAverage)) +
                " " + seqAverage + " " + parAverage);
    }

    public static void main(String[] args) {
        (new Main()).run(100_000_000, 5);
    }
}
