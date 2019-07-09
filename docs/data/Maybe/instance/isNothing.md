# `Maybe#<A>isNothing()`

Determines whether or not the `Maybe` is a `Nothing`.

## Types

* `A`: The underlying type.

## Returns

* `(boolean)`: `true` for a `Nothing`; otherwise, `false`.

## Examples

```java
Maybe.just("a").isNothing();
// => false

Maybe.nothing().isNothing();
// => true
```
