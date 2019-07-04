# `Validation#<A, B>toString()`

Converts the `instance` to a `String` representation.

## Types

* `A`: The underlying failure type.
* `B`: The underlying success type.

## Returns

* `(String)`: The `instance` as a `String`.

## Examples

```java
Validation.<String, String>failure("a").toString();
// => "Failure(a)"

Validation.<String, String>success("a").toString();
// => "Success(a)"
```
