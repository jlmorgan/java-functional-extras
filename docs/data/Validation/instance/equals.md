# `Validation#<A, B>equals(Object other)`

Determines whether or not the `other` has the same value as the current `instance`.

## Arguments

* `other (Object)`: The other object.

## Types

* `A`: The underlying failure type.
* `B`: The underlying success type.

## Returns

* `(boolean)`: `true` for equality; otherwise, `false`.

## Examples

```java
Validation<String, String> failure = Validation.<String, String>failure("a");
Validation<String, String> success = Validation.<String, String>success("a");

success.equals("a");
// => false

success.equals(failure);
// => false

success.equals(Validation.<String, String>success("a"));
// => true

failure.equals(Validation.<String, String>failure("a"));
// => true
```
