# `Maybe.<V>listToMaybe(List<V> list)`

Returns `Nothing` for an empty `list` or `Just` of the first element in the `list`.

## Arguments

* `list (List<V>)`: The `list` of values.

## Types

* `V`: The underlying type.

## Returns

* `(Maybe<V>)`: A `Just` of the first non-null value; otherwise, `Nothing`.

## Examples

```java
Maybe.listToMaybe(Arrays.asList(null, 0, null, 1));
// => Just(0)

Maybe.listToMaybe(null);
// => Nothing()

Maybe.listToMaybe(Collections.emptyList());
// => Nothing()

Maybe.listToMaybe(Collections.singletonList(null));
// => Nothing()
```
