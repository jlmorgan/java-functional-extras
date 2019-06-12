# `Maybe.<V>fromMaybe(V defaultValue, Maybe<V> maybe)`

Takes a `defaultValue` and a `Maybe` value. If the `Maybe` is `Nothing`, it returns the `defaultValue`; otherwise, it returns the underlying value of the `Just`.

## Alternatives

* `Maybe.<V>fromMaybe(V defaultValue).apply(Maybe<V> maybe)`

## Arguments

* `defaultValue (V)`: The value to use if the `maybe` is `null` or `Nothing`.
* `maybe (Maybe<V>)`: The `Maybe`.

## Types

* `V`: The underlying type.

## Returns

* `(V)`: The underlying value for a `Just`; otherwise, the `defaultValue`.

## Examples

```java
Maybe.fromMaybe(0, Maybe.nothing());
// => 0

Maybe.fromMaybe(0, Maybe.just(1));
// => 1
```
