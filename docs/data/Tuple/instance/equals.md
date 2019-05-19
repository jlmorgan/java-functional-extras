# `Tuple<A, B>#equals(Object other)`

Determine whether or not the `other` has the same value as the current `instance`.

## Arguments

* `other (Object)`: The other object.

## Types

* `A`: The `first` type parameter.
* `B`: The `second` type parameter.

## Returns

* `(boolean)`: `true` for equality; otherwise, `false`.

```java
Tuple<String, Integer> tuple = Tuple.of("a", 1);

tuple.equals(null);
// => false

tuple.equals("a");
// => false

tuple.equals(new Object());
// => false

tuple.equals(Tuple.of("a", 1));
// => true
```
