# `Either.<L, R>rights(Collection<Either<L, R>> collection)`

Extracts from a collection of `Either` all of the `Right` elements in extracted order.

## Arguments

* `collection (Collection<Either<L, R>>)` - The collection of `Either`.

## Types

* `L`: The underlying left type.
* `R`: The underlying right type.

## Returns

* `(Collection<R>)`: The collection of underlying `Right` values.

## Examples

```java
Either.rights(Arrays.asList(
  Either.left("a"),
  Either.left("b"),
  Either.right(0),
  Either.right(1)
));
// => [0, 1]
```
