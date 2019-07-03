# `Maybe#<A>equals(Object other)`

Determine whether or not the `other` has the same value as the current `instance`.

## Arguments

* `other (Object)`: The other object.

## Types

* `A`: The underlying type.

## Returns

* `(boolean)`: `true` for equality; otherwise, `false`.

## Examples

```java
Maybe<String> maybe = Maybe.just("a");

maybe.equals(null);
// => false

maybe.equals("a");
// => false

maybe.equals(new Object());
// => false

maybe.equals(Maybe.nothing());
// => false

maybe.equals(Maybe.just("a"));
// => true

Maybe.nothing().equals(Maybe.nothing());
// => true
```
