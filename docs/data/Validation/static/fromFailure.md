# `Validation.<F, S>fromFailure(Collection<F> defaultValues, Validation<F, S> validation)`

Extracts the value out of a `Failure`; otherwise, returns the `defaultValues`.

## Alternatives

* `Validation.<F, S>fromFailure(Collection<F> defaultValues).apply(Validation<F, S> validation)`

## Arguments

* `defaultValues (Collection<F>)`: Values used if the `validation` is not a `Failure`.
* `validation (Validation<F, S>)`: The `Validation`.

## Types

* `F`: The underlying failure type.
* `S`: The underlying success type.

## Returns

* `(Collection<F>)`: The underlying left value or default.

## Examples

```java
Validation.<Integer, String>fromFailure(Collections.singletonList(0), Validation.<Integer, String>failure(1));
// => [1]

Validation.<Integer, String>fromFailure([0], "a");
// => [0]

Validation.<Integer, String>fromFailure([0], Validation.<Integer, String>success("a"));
// => [0]
```
