# `Validation.<F, S>fromSuccess(S defaultValue, Validation<F, S> validation)`

Extracts the value out of a `Success`; otherwise, returns the `defaultValue`.

## Alternatives

* `Validation.<F, S>fromSuccess(S defaultValue).apply(Validation<F, S> validation)`

## Arguments

* `defaultValue (S)`: Value used if the `validation` is not a `Success`.
* `validation (Validation<F, S>)`: The `Validation`.

## Types

* `F`: The underlying failure type.
* `S`: The underlying success type.

## Returns

* `(S)`: The underlying right value or default.

## Examples

```java
Validation.<Integer, String>fromSuccess(0, Validation.<Integer, String>success(1));
// => 1

Validation.<Integer, String>fromSuccess(0, "a");
// => 0

Validation.<Integer, String>fromSuccess(0, Validation.<Integer, String>failure("a"));
// => 0
```
