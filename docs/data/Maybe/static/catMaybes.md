# `Maybe.<V>catMaybes(List<Maybe<V>> list)`

Takes a list of `Maybe` and a list of the `Just` values.

## Arguments

* `list (List<Maybe<V>>)`: List of `Maybe`.

## Types

* `V`: The underlying type.

## Returns

* `(List<V>)`: A list of the `Just` values.

## Examples

```java
Maybe.catMaybes(Arrays.asList(
  Maybe.just(1),
  Maybe.nothing(),
  Maybe.just(2),
  Maybe.nothing()
));
// => [1, 2]
```
