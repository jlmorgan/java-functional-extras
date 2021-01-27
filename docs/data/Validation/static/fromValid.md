# `Validation.<F, S>fromValid(S defaultValue, Validation<F, S> validation)`

Extracts the value out of a `Valid`; otherwise, returns the `defaultValue`.

## Alternatives

* `Validation.<F, S>fromValid(S defaultValue).apply(Validation<F, S> validation)`

## Arguments

* `defaultValue (S)`: Value used if the `validation` is not a `Valid`.
* `validation (Validation<F, S>)`: The `Validation`.

## Types

* `F`: The underlying invalid type.
* `S`: The underlying valid type.

## Returns

* `(S)`: The underlying right value or default.

## Examples

```java
Validation.<Integer, String>fromValid(0, Validation.<Integer, String>valid(1));
// => 1

Validation.<Integer, String>fromValid(0, "a");
// => 0

Validation.<Integer, String>fromValid(0, Validation.<Integer, String>invalid("a"));
// => 0
```
