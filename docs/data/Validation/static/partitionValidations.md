# `Validation.partitionValidations<F, S>(Collection<Validation<F, S>> collection)`

Partitions a collection of `Validation` into two collections. All `Invalid` elements are extracted, in order, to the first position of the output. Similarly for the `Valid` elements in the second position.

## Arguments

* `collection (Collection<Validation<F, S>>)` - The collection of `Validation`.

## Types

* `F`: The underlying invalid type.
* `S`: The underlying valid type.

## Returns

* `(Tuple<Collection<F>, Collection<S>>)`: A couple of a collection of the underlying `Invalid` values and a collection of the underlying `Valid` values.

## Examples

```java
Validation.partitionValidations<String, Integer>([
  Validation.<String, Integer>invalid("a"),
  Validation.<String, Integer>invalid(Arrays.asList("b", "c")),
  Validation.<String, Integer>valid(0),
  Validation.<String, Integer>valid(1)
]);
// => Tuple(["a", "b", "c"], [0, 1])
```
