# `Maybe.<V>fromJust(Maybe<V> maybe)`

Extracts the value out of a `Just` and throws an error if its argument is a `Nothing`.

## Arguments

* `maybe (Maybe<V>)`: The `Maybe`.

## Types

* `V`: The underlying type.

## Returns

* `(V)`: The underlying value.

## Throws

* `IllegalArgumentException` if the `maybe` is `null` or `Nothing`.

## Examples

```java
Maybe.fromJust(Maybe.just(1));
// => 1

Maybe.fromJust(Maybe.nothing());
// => throws IllegalArgumentException
```
