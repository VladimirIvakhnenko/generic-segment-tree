package segtrees;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

public class PerformanceTest {
    private static final int SIZE = 1_000_000;
    private static final int OPERATIONS = 1_000_00;
    private static final int TIME_LIMIT_MS = 2000;
    private static final Random RAND = new Random(1);
    @Test
    void speedLarge() {

        SegmentTree<Long, Long> st = new SegmentTree<>(
                SIZE,
                ( a,  b) -> a + b,
                ( current,  update,  len) -> current + update * len,
                (Long existing, Long newUpdate) -> (Long) existing + (Long)newUpdate,
                0L,
                0L
        );


        long startTime = System.nanoTime();

        for (int i = 0; i < OPERATIONS; i++) {
            int l = RAND.nextInt(SIZE);
            int r = l + RAND.nextInt(SIZE - l);

            if (RAND.nextBoolean()) {

                st.update(l, r, RAND.nextLong(1000));
            } else {

                st.query(l, r);
            }
        }


        long durationMs = (System.nanoTime() - startTime) / 1_000_000;
        assertTrue(durationMs < TIME_LIMIT_MS, () ->
                String.format("Performance test took %d ms (limit: %d ms)", durationMs, TIME_LIMIT_MS));
    }
}