# `uncons<A>(list)`

Decomposes a list into its head and tail. Returns `Nothing<Tuple<A, List<A>>>` if the `list` is empty; otherwise a `Just<Tuple<A, List<A>>> (x, xs)` where `x` is the head of the `list` and `xs` is the tail.

## Arguments

* `list (List<A>)`: The list.

## Types

* `A`: The underlying type.

## Returns

* `(Maybe<Tuple<A, List<A>>>)`: `Just` of the head and tail; otherwise, `Nothing`.

## Example

```csharp
uncons(new List<Integer>());
// => Nothing<Tuple<Integer, List<Integer>>>

uncons(new List<Integer> { 0 });
// => Just<Tuple<Integer, List<Integer>>> (0, List<Integer> { })

uncons(new List<Integer> { 1, 2, 3 });
// => Just<Tuple<Integer, List<Integer>>> (0, List<Integer> { 2, 3 })
```
