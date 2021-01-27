# `Validation.<F, S>valid(S value)`

Encapsulates a valid value.

## Arguments

* `value (S)`: The value.

## Types

* `F`: The underlying invalid type.
* `S`: The underlying valid type.

## Returns

* `(Validation<F, S>)`: An `Validation` of the `value`.

## Example

```java
Validation.<Integer, String>valid("a");
// => Valid("a")
```
