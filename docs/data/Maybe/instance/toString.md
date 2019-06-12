# `Maybe<A>#toString()`

Converts the `instance` to a `String` representation.

## Types

* `A`: The underlying type.

## Returns

* `(String)`: The `instance` as a `String`.

## Examples

```java
Maybe<String> maybe = Maybe.just("a");

maybe.toString();
// => "Just(a)"

Maybe.nothing<String>().toString();
// => "Nothing()"
```
