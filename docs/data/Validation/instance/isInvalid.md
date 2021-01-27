# `Validation#<A, B>isInvalid()`

Determines whether or not the `Validation` is a `Invalid`.

## Types

* `A`: The underlying invalid type.
* `B`: The underlying valid type.

## Returns

* `(Boolean)`: `true` for a `Invalid`; otherwise, `false`.

## Examples

```java
Validation.<String, String>invalid("a").isInvalid();
// => true

Validation.<String, String>valid("a").isInvalid();
// => false
```
