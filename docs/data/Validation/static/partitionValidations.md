# `Validation.partitionValidations<F, S>(Collection<Validation<F, S>> collection)`

Partitions a collection of `Validation` into two collections. All `Failure` elements are extracted, in order, to the first position of the output. Similarly for the `Success` elements in the second position.

## Arguments

* `collection (Collection<Validation<F, S>>)` - The collection of `Validation`.

## Types

* `F`: The underlying failure type.
* `S`: The underlying success type.

## Returns

* `(Tuple<Collection<F>, Collection<S>>)`: A couple of a collection of the underlying `Failure` values and a collection of the underlying `Success` values.

## Examples

```java
Validation.partitionValidations<String, Integer>([
  Validation.<String, Integer>failure("a"),
  Validation.<String, Integer>failure(Arrays.asList("b", "c")),
  Validation.<String, Integer>success(0),
  Validation.<String, Integer>success(1)
]);
// => Tuple(["a", "b", "c"], [0, 1])
```
