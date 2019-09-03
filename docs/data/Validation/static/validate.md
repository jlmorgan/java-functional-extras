# `Validation.<F, S>validate(Predicate<S> predicate, F failureValue, S value)`

Validates a value `b` and a `Success` of `b` if the `predicate` returns `true`; otherwise, a `Failure` of `a`.

## Alternatives

* `Validation.<F, S>validate(Predicate<S> predicate, F failureValue).apply(S value)`
* `Validation.<F, S>validate(Predicate<S> predicate).apply(F failureValue).apply(S value)`

## Arguments

* `predicate (Predicate<S>)`: The predicate.
* `failureValue (F)`: The failure value.
* `value (S)`: The value to test.

## Types

* `F`: The underlying failure type.
* `S`: The underlying success type.

## Returns

* `(Validation<F, S>)`: A `Success` of the `value` if the `predicate` returns `true`; otherwise, a `Failure` of `failureValue`.

## Throws

* `NullPointerException` if the `predicate` is `null`.

## Examples

```java
Validation.<String, Integer>validate(
  value -> value % 2 == 0,
  "The value must be even",
  0
);
// => Success(0)

Validation.<String, Integer>validate(
  value -> value % 2 == 0,
  "The value must be even",
  1
);
// => Failure(["The value must be even"])
```
