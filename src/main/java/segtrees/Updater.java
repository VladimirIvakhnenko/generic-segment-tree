package segtrees;

@FunctionalInterface
public interface Updater<T, U> {
    T apply(T current, U update, int length);
}