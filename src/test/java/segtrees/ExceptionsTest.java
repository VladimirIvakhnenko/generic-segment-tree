package segtrees;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExceptionsTest {
    private final Long[] testArray = {1L, 3L, 5L, 7L, 9L};
    private final SegmentTree<Long, Long> tree = new SegmentTree<>(
            testArray,
            SegmentTreeUtils.minCombiner(),
            SegmentTreeUtils.assignUpdater(),
            SegmentTreeUtils.assignLazyUpdater(),
            Long.MAX_VALUE,
            null
    );

    @Test
    public void testQueryNegativeLeftBound() {
        assertThrows(IllegalArgumentException.class, () -> {
            tree.query(-1, 2);
        });
    }

    @Test
    public void testQueryRightBoundExceedsSize() {
        assertThrows(IllegalArgumentException.class, () -> {
            tree.query(1, testArray.length);
        });
    }

    @Test
    public void testQueryLeftGreaterThanRight() {
        assertThrows(IllegalArgumentException.class, () -> {
            tree.query(3, 2);
        });
    }

    @Test
    public void testUpdateNegativeLeftBound() {
        assertThrows(IllegalArgumentException.class, () -> {
            tree.update(-1, 2, 5L);
        });
    }

    @Test
    public void testUpdateRightBoundExceedsSize() {
        assertThrows(IllegalArgumentException.class, () -> {
            tree.update(1, testArray.length, 5L);
        });
    }

    @Test
    public void testUpdateLeftGreaterThanRight() {
        assertThrows(IllegalArgumentException.class, () -> {
            tree.update(3, 2, 5L);
        });
    }

    @Test
    public void testNullUpdateValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            tree.update(1, 3, null);
        });
    }

    @Test
    public void testEmptyTreeConstruction() {
        Long[] emptyArray = {};
        assertDoesNotThrow(() -> {
            new SegmentTree<>(
                    emptyArray,
                    SegmentTreeUtils.minCombiner(),
                    SegmentTreeUtils.assignUpdater(),
                    SegmentTreeUtils.assignLazyUpdater(),
                    Long.MAX_VALUE,
                    null
            );
        });
    }

    @Test
    public void testNullArrayConstruction() {
        assertThrows(NullPointerException.class, () -> {
            new SegmentTree<>(
                    null,
                    SegmentTreeUtils.minCombiner(),
                    SegmentTreeUtils.assignUpdater(),
                    SegmentTreeUtils.assignLazyUpdater(),
                    Long.MAX_VALUE,
                    null
            );
        });
    }
}