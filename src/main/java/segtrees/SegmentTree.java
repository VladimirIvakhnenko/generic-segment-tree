package segtrees;

import java.util.Arrays;

public class SegmentTree<T, U> {
    private final int n;
    private final T[] tree;
    private final U[] lazy;
    private final Combiner<T> combiner;
    private final Updater<T, U> updater;
    private final LazyUpdater<U> lazyUpdater;
    private final T neutral;
    private final U noUpdate;

    @SuppressWarnings("unchecked")
    public SegmentTree(int size,
                       Combiner<T> combiner,
                       Updater<T, U> updater,
                       LazyUpdater<U> lazyUpdater,
                       T neutral,
                       U noUpdate) {
        this.n = size;
        this.combiner = combiner;
        this.updater = updater;
        this.lazyUpdater = lazyUpdater;
        this.neutral = neutral;
        this.noUpdate = noUpdate;


        this.tree = (T[]) new Object[4 * size];
        this.lazy = (U[]) new Object[4 * size];
        Arrays.fill(lazy, noUpdate);


        if (neutral != null) {
            Arrays.fill(tree, neutral);
        }
    }

    @SuppressWarnings("unchecked")
    public SegmentTree(T[] array, Combiner<T> combiner, Updater<T, U> updater,
                       LazyUpdater<U> lazyUpdater, T neutral, U noUpdate) {
        this.n = array.length;
        this.combiner = combiner;
        this.updater = updater;
        this.lazyUpdater = lazyUpdater;
        this.neutral = neutral;
        this.noUpdate = noUpdate;


        tree = (T[]) new Object[4 * n];
        lazy = (U[]) new Object[4 * n];


        Arrays.fill(lazy, noUpdate);

        if (n != 0) {
            build(array, 0, n - 1, 0);
        }
    }

    private void build(T[] array, int left, int right, int node) {
        if (left == right) {
            tree[node] = array[left];
        } else {
            int mid = (left + right) / 2;
            build(array, left, mid, 2 * node + 1);
            build(array, mid + 1, right, 2 * node + 2);
            tree[node] = combiner.combine(tree[2 * node + 1], tree[2 * node + 2]);
        }
    }

    public T query(int l, int r) {
        if (l < 0 || r >= n || l > r) {
            throw new IllegalArgumentException("Invalid query range");
        }
        return query(0, n - 1, l, r, 0);
    }

    private T query(int nodeLeft, int nodeRight, int l, int r, int node) {
        propagateLazy(node, nodeLeft, nodeRight);

        if (r < nodeLeft || l > nodeRight) {
            return neutral;
        }

        if (l <= nodeLeft && nodeRight <= r) {
            return tree[node];
        }

        int mid = (nodeLeft + nodeRight) / 2;
        T left = query(nodeLeft, mid, l, r, 2 * node + 1);
        T right = query(mid + 1, nodeRight, l, r, 2 * node + 2);
        return combiner.combine(left, right);
    }

    public void update(int l, int r, U value) {
        if (l < 0 || r >= n || l > r) {
            throw new IllegalArgumentException("Invalid update range");
        }
        if (value == null) {
            throw new IllegalArgumentException("Update value cannot be null");
        }
        update(0, n - 1, l, r, 0, value);
    }

    private void update(int nodeLeft, int nodeRight, int l, int r, int node, U value) {
        propagateLazy(node, nodeLeft, nodeRight);

        if (r < nodeLeft || l > nodeRight) {
            return;
        }

        if (l <= nodeLeft && nodeRight <= r) {
            lazy[node] = value;
            propagateLazy(node, nodeLeft, nodeRight);
            return;
        }

        int mid = (nodeLeft + nodeRight) / 2;
        update(nodeLeft, mid, l, r, 2 * node + 1, value);
        update(mid + 1, nodeRight, l, r, 2 * node + 2, value);
        tree[node] = combiner.combine(tree[2 * node + 1], tree[2 * node + 2]);
    }

    private void propagateLazy(int node, int nodeLeft, int nodeRight) {
        if (lazy[node] == null || lazy[node].equals(noUpdate)) {
            return;
        }


        tree[node] = updater.apply(tree[node], lazy[node], nodeRight - nodeLeft + 1);

        if (nodeLeft != nodeRight) {
            int leftChild = 2 * node + 1;
            int rightChild = 2 * node + 2;


            lazy[leftChild] = lazyUpdater.compose(lazy[leftChild], lazy[node]);
            lazy[rightChild] = lazyUpdater.compose(lazy[rightChild], lazy[node]);
        }

        lazy[node] = noUpdate;
    }
}