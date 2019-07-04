# `Validation#<A, B>isFailure()`

Determines whether or not the `Validation` is a `Failure`.

## Types

* `A`: The underlying failure type.
* `B`: The underlying success type.

## Returns

* `(Boolean)`: `true` for a `Failure`; otherwise, `false`.

## Examples

```java
Validation.<String, String>failure("a").isFailure();
// => true

Validation.<String, String>success("a").isFailure();
// => false
```
