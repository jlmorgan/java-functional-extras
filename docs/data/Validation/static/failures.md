# `Validation.<F, S>failures(Collection<Validation<F, S>> collection)`

Extracts from a collection of `Validation` all of the `Failure` elements in extracted order.

## Arguments

* `collection (Collection<Validation<F, S>>)` - The collection of `Validation`.

## Types

* `F`: The underlying failure type.
* `S`: The underlying success type.

## Returns

* `(Collection<F>)`: The collection of underlying `Failure` values.

## Examples

```java
Validation.failures([
  Validation.<String, Integer>failure("a"),
  Validation.<String, Integer>failure(Arrays.asList("b", "c")),
  Validation.<String, Integer>success(0),
  Validation.<String, Integer>success(1)
]);
// => ["a", "b", "c"]
```
