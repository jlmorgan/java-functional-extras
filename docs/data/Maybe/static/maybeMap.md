# `Maybe.<V, R>maybeMap(R defaultValue, Function<V, R> morphism, Maybe<V> maybe)`

If the `Maybe` value is a `Nothing`, it returns the `defaultValue`; otherwise, applies the `morphism` to the underlying value in the `Just` and returns the result.

## Alternatives

* `Maybe.<V, R>maybeMap(R defaultValue, Function<V, R> morphism).apply(Maybe<V> maybe)`
* `Maybe.<V, R>maybeMap(R defaultValue).apply(Function<V, R> morphism).apply(Maybe<V> maybe)`

## Arguments

* `defaultValue (V)`: The value to use if the `maybe` is `null` or `Nothing`.
* `morphism (Function<V, R>)`: The function to map the underlying value of the `maybe` to the same type as the return type.
* `maybe (Maybe<V>)`: The `Maybe`.

## Types

* `(R)`: The default value and return type.
* `(V)`: The underlying type of the `maybe`.

## Returns

* `(R)`: The mapped underlying value for a `Just`; otherwise, the `defaultValue`.

## Throws

* `NullPointerException` if the `morphism` is `null`.

## Examples

```java
Maybe.maybeMap(0, value -> value + 1, Maybe.just(1));
// => 2

Maybe.maybeMap(0, value -> value + 1, null);
// => 1

Maybe.maybeMap(0, value -> value + 1, Maybe.nothing());
// => 1
```
