# `Either.<L, R>fromLeft(L defaultValue, Either<L, R> either)`

Extracts the value out of a `Left`; otherwise, returns the `defaultValue`.

## Alternatives

* `Either.<L, R>fromLeft(L defaultValue).apply(Either<L, R> either)`

## Arguments

* `defaultValue (L)`: Value used if the `either` is not a `Left`.
* `either (Either<L, R>)`: The `Either`.

## Types

* `L`: The underlying left type.
* `R`: The underlying right type.

## Returns

* `(L)`: The underlying left value.

## Examples

```java
Either.fromLeft(0, Either.<Integer, String>Left(1));
// => 1

Either.<Integer, String>fromLeft(0)
  .apply(Either.<Integer, String>Right("a"));
// => 0
```
