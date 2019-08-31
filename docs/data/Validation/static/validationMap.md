# `Validation.<F, S, C>validationMap(Function<F, C> failureMap, Function<S, C> successMap, Validation<F, S> validation)`

Provides a catamorphism of the `validation` to a singular value. If the value is `Failure a`, apply the first function to `a`; otherwise, apply the second function to `b`.

## Alternatives

* `Validation.<F, S, C>validationMap(Function<F, C> failureMap, Function<S, C> successMap).apply(Validation<F, S> validation)`
* `Validation.<F, S, C>validationMap(Function<F, C> failureMap).apply(Function<S, C> successMap).apply(Validation<F, S> validation)`

## Arguments

* `failureMap (Function<F, C>)`: Maps the value of a `Failure a` to `c`.
* `successMap (Function<S, C>)`: Maps the value of a `Success b` to `c`.
* `validation (Validation<F, S>)`: The `Validation`.

## Types

* `F`: The underlying failure type.
* `S`: The underlying success type.

## Returns

* `(C)`: The result of the catamorphism of the `validation`.

## Throws

* `NullPointerError` if the `failureMap` or `successMap` is `null`.
* `NullPointerError` if the `validation` is `null`.

## Examples

```java
Validation.<Exception, Integer, String>validationMap(
  exceptions -> exceptions.stream()
    .map(Exception::getMessage)
    .collect(Collectors.joining(", ")),
  value -> "The value is " + value % 2 == 0 ? "even" : "odd",
  Validation.<Exception, Integer>success(1)
);
// => "The value is odd"

Validation.<Exception, Integer, String>validationMap(
  exceptions -> exceptions.stream()
    .map(Exception::getMessage)
    .collect(Collectors.joining(", ")),
  value -> "The value is " + value % 2 == 0 ? "even" : "odd",
  Validation.<Exception, Integer>failure(new RuntimeException("The value is not a number"))
);
// => "The value is not a number"
```
