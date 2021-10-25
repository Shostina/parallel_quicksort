import java.util.concurrent.RecursiveAction;

public class ParallelQuickSort extends RecursiveAction {
    private final int BORDER = 10000;

    int[] a;
    int left;
    int right;

    public ParallelQuickSort(int[] a) {
        this(a, 0, a.length - 1);
    }

    public ParallelQuickSort(int[] a, int left, int right) {
        this.a = a;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if ( right - left < BORDER) {
            QuickSort.sort(a, left, right);
        } else {
            int i, j, x;
            i = left;
            j = right;
            x = a[i];
            while (i < j) {
                while (i < j && a[j] > x)
                    j--;
                if (i < j)
                    a[i++] = a[j];
                while (i < j && a[i] < x)
                    i++;
                if (i < j)
                    a[j--] = a[i];
            }
            a[i] = x;
            ParallelQuickSort t1 = new ParallelQuickSort(a, left, i - 1);
            ParallelQuickSort t2 = new ParallelQuickSort(a, i + 1, right);
            t1.fork();
            t2.compute();
            t1.join();
        }
    }
}
