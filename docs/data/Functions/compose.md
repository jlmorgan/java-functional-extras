# `compose(g, f) | compose(g).apply(f)`

Composes two functions `g` after `f`.

## Arguments

* `g (Function<B, C>)`: The second function.
* `f (Function<A, B>)`: The first function.

### Types

* `A`: Input type to the first function (`f`) in the composition.
* `B`: Output type of the first function (`f`) and input type to the second (`g`).
* `C`: Output type of the second function (`g`).

## Returns

* `(Function<A, C>)`: Returns a function that maps a value of type `A` to type `C`.

## Example

```java
Function<Integer, Function<Integer, Integer>> add = a -> b -> a + 1;
Function<Integer, Integer> square = a -> a * a;
Function<Integer, Integer> decrementAndSquare = compose(square, add.apply(-1));
Function<Integer, Integer> incrementAndSquare = Functions.<Integer, Integer, Integer>compose(square).apply(add.apply(1));

decrementAndSquare.apply(3);
// => 4

incrementAndSquare.apply(2);
// => 9
```
