# `Validation.<F, S>valids(Collection<Validation<F, S>> collection)`

Extracts from a collection of `Validation` all of the `Valid` elements in extracted order.

## Arguments

* `collection (Collection<Validation<F, S>>)` - The collection of `Validation`.

## Types

* `F`: The underlying invalid type.
* `S`: The underlying valid type.

## Returns

* `(Collection<S>)`: The collection of underlying `Valid` values.

## Examples

```java
Validation.valids<String, Integer>([
  Validation.<String, Integer>Invalid("a"),
  Validation.<String, Integer>Invalid("b"),
  Validation.<String, Integer>Valid(0),
  Validation.<String, Integer>Valid(1)
]);
// => [0, 1]
```
