# `pipe(f, g) | pipe(f).apply(g)`

Composes two functions `f` before `g`.

## Arguments

* `f (Function<A, B>)`: The first function.
* `g (Function<B, C>)`: The second function.

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
Function<Integer, Integer> decrementAndSquare = pipe(add.apply(-1), square);
Function<Integer, Integer> incrementAndSquare = Functions.<Integer, Integer, Integer>pipe(add.apply(1)).apply(square);

decrementAndSquare.apply(3);
// => 4

incrementAndSquare.apply(2);
// => 9
```
