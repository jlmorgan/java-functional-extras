# `bind(g, f)`

Composes a sequence of two functions `g` after `f` where `f` maps the input value to the first argument of `g`.

## Alternatives

* `bind(g).apply(f)`

## Arguments

* `g (Function<A, B, C>)`: The second function of the sequence.
* `f (Function<A, B>)`: The first function of the sequence.

## Types

* `A`: Input type for the first function (`f`) and the second argument of the second function (`g`).
* `B`: Output type of the first function (`f`) and the first argument of the second function (`g`).
* `C`: Output type of the second function (`g`).

## Returns

* `(Function<A, C>)`: A function that takes the value and returns the result of the sequence.

## Example

```java
Function<Double, Double, Double> subtract = (b, a) => b - a;
Function<Double, Double> square = a => Math.pow(a, 2);
Function<Double, Double> subtractSquare = bind(Subtract, Square);

subtractSquare(3); // (3 * 3) - 3
// => 6

subtractSquare(5); // (5 * 5) - 5
// => 20
```
