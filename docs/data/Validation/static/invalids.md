# `Validation.<F, S>invalids(Collection<Validation<F, S>> collection)`

Extracts from a collection of `Validation` all of the `Invalid` elements in extracted order.

## Arguments

* `collection (Collection<Validation<F, S>>)` - The collection of `Validation`.

## Types

* `F`: The underlying invalid type.
* `S`: The underlying valid type.

## Returns

* `(Collection<F>)`: The collection of underlying `Invalid` values.

## Examples

```java
Validation.invalids([
  Validation.<String, Integer>invalid("a"),
  Validation.<String, Integer>invalid(Arrays.asList("b", "c")),
  Validation.<String, Integer>valid(0),
  Validation.<String, Integer>valid(1)
]);
// => ["a", "b", "c"]
```
