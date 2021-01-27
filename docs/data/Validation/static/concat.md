# `Validation.<F, S>concat(Validation<F, S> second, Validation<F, S> first)`

Concatenates two `Invalid` values together, replacing a `Valid` with the `Invalid`; otherwise, take the first `Valid`.

## Alternatives

* `Validation.<F, S>concat(Validation<F, S> second).apply(Validation<F, S> first)`

## Arguments

* `second (Validation<F, S>)`: The second `Validation`.
* `first (Validation<F, S>)`: The first `Validation`.

## Types

* `F`: The underlying invalid type.
* `S`: The underlying valid type.

## Returns

* `(Validation<F, S>)`: The first `Valid` for two valids, the first `Invalid` for mixed; otherwise, a `Invalid` of the concatenation of the invalid values.

## Throws

* `NullPointerException` if either value is `null`.

## Examples

```java
Validation.concat(
  Validation.<String, Integer>valid(0),
  Validation.<String, Integer>valid(1)
);
// => Valid(0)

Validation.concat(
  Validation.<String, Integer>valid(0),
  Validation.<String, Integer>invalid("a")
);
// => Invalid(["a"])

Validation.concat(
  Validation.<String, Integer>invalid("a"),
  Validation.<String, Integer>valid(0)
);
// => Invalid(["a"])

Validation.concat(
  Validation.<String, Integer>invalid("b"),
  Validation.<String, Integer>invalid("a")
);
// => Invalid(["a", "b"])
```
