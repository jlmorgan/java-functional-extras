# `Validation#<A, B>equals(Object other)`

Determines whether or not the `other` has the same value as the current `instance`.

## Arguments

* `other (Object)`: The other object.

## Types

* `A`: The underlying invalid type.
* `B`: The underlying valid type.

## Returns

* `(boolean)`: `true` for equality; otherwise, `false`.

## Examples

```java
Validation<String, String> invalid = Validation.<String, String>invalid("a");
Validation<String, String> valid = Validation.<String, String>valid("a");

valid.equals("a");
// => false

valid.equals(invalid);
// => false

valid.equals(Validation.<String, String>valid("a"));
// => true

invalid.equals(Validation.<String, String>invalid("a"));
// => true
```
