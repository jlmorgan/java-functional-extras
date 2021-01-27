# `Validation.<F, S>fromInvalid(Collection<F> defaultValues, Validation<F, S> validation)`

Extracts the value out of a `Invalid`; otherwise, returns the `defaultValues`.

## Alternatives

* `Validation.<F, S>fromInvalid(Collection<F> defaultValues).apply(Validation<F, S> validation)`

## Arguments

* `defaultValues (Collection<F>)`: Values used if the `validation` is not a `Invalid`.
* `validation (Validation<F, S>)`: The `Validation`.

## Types

* `F`: The underlying invalid type.
* `S`: The underlying valid type.

## Returns

* `(Collection<F>)`: The underlying left value or default.

## Examples

```java
Validation.<Integer, String>fromInvalid(Collections.singletonList(0), Validation.<Integer, String>invalid(1));
// => [1]

Validation.<Integer, String>fromInvalid([0], "a");
// => [0]

Validation.<Integer, String>fromInvalid([0], Validation.<Integer, String>valid("a"));
// => [0]
```
