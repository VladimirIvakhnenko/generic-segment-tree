# Generic Segment Tree

Разработан Java-класс обобщённого дерева отрезков (Segment Tree) с поддержкой отложенных операций (lazy propagation).

## Внешнее API

### Основной класс `SegmentTree<T, U>`

#### Конструкторы
```java
// Создание дерева из массива
public SegmentTree(
    T[] array,
    Combiner<T> combiner,
    Updater<T, U> updater,
    LazyUpdater<U> lazyUpdater,
    T neutral,
    U noUpdate
)

// Создание  дерева  размера size
public SegmentTree(
    int size,
    Combiner<T> combiner,
    Updater<T, U> updater,
    LazyUpdater<U> lazyUpdater,
    T neutral,
    U noUpdate
)
```
#### Запрос и обновление

#####  Запрос на отрезке [l, r]
```java
public T query(int l, int r)
```

##### Обновление на отрезке [l, r] значением value
```java
public void update(int l, int r, U value)
```

### Операции агрегации и апдейта

#### Агрегация `Combiner<T>`
```java
@FunctionalInterface
public interface Combiner<T> {
     T combine(T left, T right);
}
```
#### Апдейт `Updater<T>`
```java
@FunctionalInterface
public interface Updater<T, U> {
    T apply(T current, U update, int length);
}
```

#### Апдейт отложенных операций `LazyUpdater`
```java
@FunctionalInterface
public interface LazyUpdater<U> {
    U compose(U existing, U newUpdate);
}
```
