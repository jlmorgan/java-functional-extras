# `Either#<A, B>equals(Object other)`

Determine whether or not the `other` has the same value as the current `instance`.

## Arguments

* `other (Object)`: The other object.

## Types

* `A`: The underlying left type.
* `B`: The underlying right type.

## Returns

* `(boolean)`: `true` for equality; otherwise, `false`.

## Examples

```java
const left = Either.<String, String>Left("a");
const right = Either.<String, String>Right("a");

right.equals("a");
// => false

right.equals(left);
// => false

right.equals(Either.<String, String>Right("a"));
// => true

left.equals(Either.<String, String>Left("a"));
// => true
```
