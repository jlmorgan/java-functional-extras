# `Validation.<F, S>successes(Collection<Validation<F, S>> collection)`

Extracts from a collection of `Validation` all of the `Success` elements in extracted order.

## Arguments

* `collection (Collection<Validation<F, S>>)` - The collection of `Validation`.

## Types

* `F`: The underlying failure type.
* `S`: The underlying success type.

## Returns

* `(Collection<S>)`: The collection of underlying `Success` values.

## Examples

```java
Validation.successes<String, Integer>([
  Validation.<String, Integer>Failure("a"),
  Validation.<String, Integer>Failure("b"),
  Validation.<String, Integer>Success(0),
  Validation.<String, Integer>Success(1)
]);
// => [0, 1]
```
