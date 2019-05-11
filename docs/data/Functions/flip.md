# `flip(f)`

Flips the argument order of the specified bi-function or curried binary function.

## Arguments

### Curried Function

* `f (Function<A, Function<B, C>>)`: A curried binary function.

### Bi-Function

* `f (Function<A, B, C>)`: A bi-function.

### Types

* `A`: Type of the first argument.
* `B`: Type of the second argument.
* `C`: Return type of the function (`f`).

## Returns

### Flipped Curried Function

* `(Function<B, Function<A, C>>)`: The curried binary function to flip.

### Flipped Bi-Function

* `(Function<B, A, C>)`: The flipped bi-function.

## Example

```java
BiFunction<String, String, String> append = (a, b) -> a + b;
BiFunction<String, String, String> prepend = flip(append);

prepend.apply("a", "b");
// => "ba"

Function<String, Function<String, String>> first = a -> b -> a;
Function<String, Function<String, String>> second = flip(first);

first.apply("a").apply("b");
// => "a"

second.apply("a").apply("b");
// => "b"
```
