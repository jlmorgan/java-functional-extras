# `Maybe.<V>maybeToList(Maybe<V> maybe)`

An empty list for `Nothing`; otherwise, a singleton list of the underlying value of the `Just`.

## Arguments

* `maybe (Maybe<V>)`: The `Maybe`.

## Types

* `(V)`: The underlying type.

## Returns

* `(List<V>)`: A singleton list of the underlying value within the `maybe` for a `Just`; otherwise, an empty list for `Nothing`.

## Examples

```java
Maybe.maybeToList(Maybe.just(1));
// => [1]

Maybe.maybeToList(Maybe.nothing());
// => []

Maybe.maybeToList(null);
// => []
```
