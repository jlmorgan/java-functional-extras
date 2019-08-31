# `Validation#<A, B>isSuccess()`

Determines whether or not the `Validation` is a `Success`.

## Types

* `A`: The underlying failure type.
* `B`: The underlying success type.

## Returns

* `(Boolean)`: `true` for a `Success`; otherwise, `false`.

## Examples

```java
Validation.<String, String>failure("a").isSuccess();
// => false

Validation.<String, String>success("a").isSuccess();
// => true
```
