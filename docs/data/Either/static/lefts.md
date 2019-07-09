# `Either.<L, R>lefts(Collection<Either<L, R>> collection)`

Extracts from a collection of `Either` all of the `Left` elements in extracted order.

## Arguments

* `collection (Collection<Either<L, R>>)` - The collection of `Either`.

## Types

* `L`: The underlying left type.
* `R`: The underlying right type.

## Returns

* `(Collection<L>)`: The collection of underlying `Left` values.

## Examples

```java
Either.lefts(Arrays.asList(
  Either.left("a"),
  Either.left("b"),
  Either.right(0),
  Either.right(1)
));
// => ["a", "b"]
```
