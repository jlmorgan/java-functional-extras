# `Validation.<F, S>concat(Validation<F, S> second, Validation<F, S> first)`

Concatenates two `Failure` values together, replacing a `Success` with the `Failure`; otherwise, take the first `Success`.

## Alternatives

* `Validation.<F, S>concat(Validation<F, S> second).apply(Validation<F, S> first)`

## Arguments

* `second (Validation<F, S>)`: The second `Validation`.
* `first (Validation<F, S>)`: The first `Validation`.

## Types

* `F`: The underlying failure type.
* `S`: The underlying success type.

## Returns

* `(Validation<F, S>)`: The first `Success` for two successes, the first `Failure` for mixed; otherwise, a `Failure` of the concatenation of the failure values.

## Throws

* `NullPointerException` if either value is `null`.

## Examples

```java
Validation.concat(
  Validation.<String, Integer>success(0),
  Validation.<String, Integer>success(1)
);
// => Success(0)

Validation.concat(
  Validation.<String, Integer>success(0),
  Validation.<String, Integer>failure("a")
);
// => Failure(["a"])

Validation.concat(
  Validation.<String, Integer>failure("a"),
  Validation.<String, Integer>success(0)
);
// => Failure(["a"])

Validation.concat(
  Validation.<String, Integer>failure("b"),
  Validation.<String, Integer>failure("a")
);
// => Failure(["a", "b"])
```
