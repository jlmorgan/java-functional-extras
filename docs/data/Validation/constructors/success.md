# `Validation.<F, S>success(S value)`

Encapsulates a success value.

## Arguments

* `value (S)`: The value.

## Types

* `F`: The underlying failure type.
* `S`: The underlying success type.

## Returns

* `(Validation<F, S>)`: An `Validation` of the `value`.

## Example

```java
Validation.<Integer, String>success("a");
// => Success("a")
```
