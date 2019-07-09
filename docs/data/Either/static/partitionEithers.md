# `Either.<L, R>partitionEithers(Collection<Either<L, R>> collection)`

Partitions a collection of `Either` into two collections. All `Left` elements are extracted, in order, to the first position of the output. Similarly for the `Right` elements in the second position.

## Arguments

* `collection (Collection<Either<L, R>>)` - The collection of `Either`.

## Types

* `L`: The underlying left type.
* `R`: The underlying right type.

## Returns

* `(Tuple<Collection<L>, Collection<R>>)`: A couple of a collection of the underlying `Left` values and a collection of the underlying `Right` values.

## Examples

```java
Either.partitionEithers(Arrays.asList(
  Either.left("a"),
  Either.left("b"),
  Either.right(0),
  Either.right(1)
));
// => Tuple(["a", "b"], [0, 1])
```
