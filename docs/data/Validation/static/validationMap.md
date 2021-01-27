# `Validation.<F, S, C>validationMap(Function<F, C> invalidMap, Function<S, C> validMap, Validation<F, S> validation)`

Provides a catamorphism of the `validation` to a singular value. If the value is `Invalid a`, apply the first function to `a`; otherwise, apply the second function to `b`.

## Alternatives

* `Validation.<F, S, C>validationMap(Function<F, C> invalidMap, Function<S, C> validMap).apply(Validation<F, S> validation)`
* `Validation.<F, S, C>validationMap(Function<F, C> invalidMap).apply(Function<S, C> validMap).apply(Validation<F, S> validation)`

## Arguments

* `invalidMap (Function<F, C>)`: Maps the value of a `Invalid a` to `c`.
* `validMap (Function<S, C>)`: Maps the value of a `Valid b` to `c`.
* `validation (Validation<F, S>)`: The `Validation`.

## Types

* `F`: The underlying invalid type.
* `S`: The underlying valid type.

## Returns

* `(C)`: The result of the catamorphism of the `validation`.

## Throws

* `NullPointerError` if the `invalidMap` or `validMap` is `null`.
* `NullPointerError` if the `validation` is `null`.

## Examples

```java
Validation.<Exception, Integer, String>validationMap(
  exceptions -> exceptions.stream()
    .map(Exception::getMessage)
    .collect(Collectors.joining(", ")),
  value -> "The value is " + value % 2 == 0 ? "even" : "odd",
  Validation.<Exception, Integer>valid(1)
);
// => "The value is odd"

Validation.<Exception, Integer, String>validationMap(
  exceptions -> exceptions.stream()
    .map(Exception::getMessage)
    .collect(Collectors.joining(", ")),
  value -> "The value is " + value % 2 == 0 ? "even" : "odd",
  Validation.<Exception, Integer>invalid(new RuntimeException("The value is not a number"))
);
// => "The value is not a number"
```
