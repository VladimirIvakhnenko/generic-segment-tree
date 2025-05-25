package segtrees;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SumPlusAddTest {

    @Test
    public void pointRangeUpdates() {
        Long[] array = {5L, 3L, 8L, 2L, 7L};
        SegmentTree<Long, Long> st = createTree(array);
        NaiveSumArray naive = new NaiveSumArray(array);

        st.update(2, 2, -1L);
        naive.update(2, 2, -1L);
        assertEquals(naive.query(2, 2), st.query(2, 2));
    }

    @Test
    public void fullRangeOperations() {
        Long[] array = {5L, 3L, 8L, 2L, 7L};
        SegmentTree<Long, Long> st = createTree(array);
        NaiveSumArray naive = new NaiveSumArray(array);

        st.update(0, 4, 4L);
        naive.update(0, 4, 4L);
        assertEquals(naive.query(0, 4), st.query(0, 4));
    }

    @Test
    public void overlappingRanges() {
        Long[] array = {5L, 3L, 8L, 2L, 7L};
        SegmentTree<Long, Long> st = createTree(array);
        NaiveSumArray naive = new NaiveSumArray(array);

        st.update(1, 3, 5L);
        naive.update(1, 3, 5L);
        assertEquals(naive.query(0, 2), st.query(0, 2));
    }

    @Test
    public void boundaryPositions() {
        Long[] array = {5L, 3L, 8L, 2L, 7L};
        SegmentTree<Long, Long> st = createTree(array);
        NaiveSumArray naive = new NaiveSumArray(array);

        st.update(0, 0, 1L);
        st.update(4, 4, 1L);
        naive.update(0, 0, 1L);
        naive.update(4, 4, 1L);
        assertEquals(naive.query(0, 4), st.query(0, 4));
    }

    @Test
    public void consecutiveUpdates() {
        Long[] array = {5L, 3L, 8L, 2L, 7L};
        SegmentTree<Long, Long> st = createTree(array);
        NaiveSumArray naive = new NaiveSumArray(array);

        st.update(1, 3, 4L);
        st.update(2, 4, 2L);
        naive.update(1, 3, 4L);
        naive.update(2, 4, 2L);
        assertEquals(naive.query(0, 4), st.query(0, 4));
    }

    private SegmentTree<Long, Long> createTree(Long[] array) {
        return new SegmentTree<>(
                array,
                SegmentTreeUtils.sumCombiner(),
                SegmentTreeUtils.sumUpdater(),
                SegmentTreeUtils.sumLazyUpdater(),
                0L,
                0L
        );
    }

    private static class NaiveSumArray {
        private final long[] array;
        public NaiveSumArray(Long[] input) {
            this.array = new long[input.length];
            for (int i = 0; i < input.length; i++) {
                array[i] = input[i];
            }
        }
        public void update(int l, int r, long x) {
            for (int i = l; i <= r; i++) {
                array[i] += x;
            }
        }
        public long query(int l, int r) {
            long sum = 0;
            for (int i = l; i <= r; i++) {
                sum += array[i];
            }
            return sum;
        }
    }
}