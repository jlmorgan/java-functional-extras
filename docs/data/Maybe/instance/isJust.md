# `Maybe<A>#isJust()`

Determines whether or not the `Maybe` is a `Just`.

## Types

* `A`: The underlying type.

## Returns

* `(boolean)`: `true` for a `Just`; otherwise, `false`.

## Examples

```java
Maybe.just("a").isJust();
// => true

Maybe.nothing().isJust();
// => false
```
