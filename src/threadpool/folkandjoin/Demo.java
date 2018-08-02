package threadpool.folkandjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by xuchengyun on 8/1/18.
 */

// folk and join usually to solve divide and conquer problem
public class Demo extends RecursiveTask{

    final static int THRESHOLD = 100;
    long[] array;
    int start;
    int end;

    Demo(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("compute %d-%d = %d", start, end, sum));
            return sum;
        }

        // divide and conquer
        int middle = (end + start) / 2;
        System.out.println(String.format("split %d-%d ==> %d-%d, %d-%d", start, end,
                start, middle, middle, end));
        Demo subTask1 = new Demo(this.array, start, middle);
        Demo subTask2 = new Demo(this.array, middle, end);
        invokeAll(subTask1, subTask2);
        Long subResult1 = (Long) subTask1.join();
        Long subResult2 = (Long) subTask2.join();
        Long result = subResult1 + subResult2;
        System.out.println("result = " + subResult1 + " + " + subResult2 + " ==> " + result);
        return result;
    }

    public static void main(String[] args) {

        long[] array = new long[400];
        fillArray(array);
        ForkJoinPool fjp = new ForkJoinPool(4);
        ForkJoinTask<Long> task = new Demo(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        Long result = fjp.invoke(task);
        long endTime = System.currentTimeMillis();
        System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");

    }

    private static void fillArray(long[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
    }
}
