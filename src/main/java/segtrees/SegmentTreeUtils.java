package segtrees;

public class SegmentTreeUtils {
    public static Combiner<Long> sumCombiner() {
        return Long::sum;
    }

    public static Updater<Long, Long> sumUpdater() {
        return (current, update, length) -> current + update * length;
    }

    public static LazyUpdater<Long> sumLazyUpdater() {
        return Long::sum;
    }

    public static Combiner<Long> minCombiner() {
        return Math::min;
    }

    public static Updater<Long, Long> assignUpdater() {
        return (current, update, length) -> update;
    }

    public static LazyUpdater<Long> assignLazyUpdater() {
        return (existing, newUpdate) -> newUpdate;
    }
}