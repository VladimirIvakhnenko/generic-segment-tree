package segtrees;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Random;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class StressTest {
    private static final int SIZE = 1_000_000;
    private static final int OPERATIONS = 100_000;
    private static final Random RAND = new Random(12345);
    private static final long TIME_LIMIT_MS = 2000;

    @Test
    @Timeout(value = TIME_LIMIT_MS, unit = TimeUnit.MILLISECONDS)
    void performanceTest() {

        Long[] array = new Long[SIZE];
        Arrays.fill(array, 0L);


        SegmentTree<Long, Long> st = new SegmentTree<>(
                array,
                (a, b) -> a + b,
                (current, update, len) -> current + update * len,
                (existing, newUpdate) -> existing + newUpdate,
                0L,
                0L
        );


        long startTime = System.nanoTime();

        for (int i = 0; i < OPERATIONS; i++) {
            int l = RAND.nextInt(SIZE);
            int r = l + RAND.nextInt(SIZE - l);

            if (i % 2 == 0) {
                st.update(l, r, (long) (RAND.nextInt(100) + 1));
            } else {
                st.query(l, r);
            }
        }

        long durationMs = (System.nanoTime() - startTime) / 1_000_000;
        System.out.printf("Performance: %d ops in %d ms%n", OPERATIONS, durationMs);
    }
}