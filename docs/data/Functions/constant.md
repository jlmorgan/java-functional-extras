# `constant(a)`

The `K` combinator. Creates a unary function that ignores the input value and returns the original value.

## Arguments

* `a (A)`: A value.

## Types

* `A`: Type of the value to return.
* `B`: Type of the value to ignore.

## Returns

* `(Function<B, A>)`: Returns a unary function that takes a value of type `B` and returns the original value of type `A`.

## Example

```java
Function<Integer, Integer> alwaysOne = constant(1);

alwaysOne.apply(0);
// => 1

alwaysOne.apply(2);
// => 1
```
