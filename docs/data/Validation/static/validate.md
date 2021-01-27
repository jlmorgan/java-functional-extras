# `Validation.<F, S>validate(Predicate<S> predicate, F invalidValue, S value)`

Validates a value `b` and a `Valid` of `b` if the `predicate` returns `true`; otherwise, a `Invalid` of `a`.

## Alternatives

* `Validation.<F, S>validate(Predicate<S> predicate, F invalidValue).apply(S value)`
* `Validation.<F, S>validate(Predicate<S> predicate).apply(F invalidValue).apply(S value)`

## Arguments

* `predicate (Predicate<S>)`: The predicate.
* `invalidValue (F)`: The invalid value.
* `value (S)`: The value to test.

## Types

* `F`: The underlying invalid type.
* `S`: The underlying valid type.

## Returns

* `(Validation<F, S>)`: A `Valid` of the `value` if the `predicate` returns `true`; otherwise, a `Invalid` of `invalidValue`.

## Throws

* `NullPointerException` if the `predicate` is `null`.

## Examples

```java
Validation.<String, Integer>validate(
  value -> value % 2 == 0,
  "The value must be even",
  0
);
// => Valid(0)

Validation.<String, Integer>validate(
  value -> value % 2 == 0,
  "The value must be even",
  1
);
// => Invalid(["The value must be even"])
```
