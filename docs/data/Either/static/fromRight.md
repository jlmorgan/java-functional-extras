# `Either.<L, R>fromRight(R defaultValue, Either<L, R> either)`

Extracts the value out of a `Right`; otherwise, returns the `defaultValue`.

## Alternatives

* `Either.<L, R>fromRight(R defaultValue).apply(Either<L, R> either)`

## Arguments

* `defaultValue (R)`: Value used if the `either` is not a `Right`.
* `either (Either<L, R>)`: The `Either`.

## Types

* `L`: The underlying left type.
* `R`: The underlying right type.

## Returns

* `(R)`: The underlying right value or default.

## Examples

```java
Either.fromRight(0, Either.<String, Integer>Right(1));
// => 1

Either.<String, Integer>fromRight(0)
  .apply(Either.<String, Integer>Left("a"));
// => 0
```
