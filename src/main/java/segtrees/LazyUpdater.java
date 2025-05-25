package segtrees;


@FunctionalInterface
public interface LazyUpdater<U> {
    U compose(U existing, U newUpdate);
}