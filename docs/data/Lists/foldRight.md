# `Lists.<A, B>foldRight(fold, initialValue, list)`

Folds a list from last to head.

## Alternatives

* `Lists.<A, B>foldRight(fold).apply(initialValue).apply(list)`
* `Lists.<A, B>foldRight(fold, initialValue).apply(list)`

## Arguments

* `fold (BiFunction<A, B, B>)`: Folding function.
* `initialValue (B)`: Initial value.
* `list (List<A>)`: The list.

## Types

* `A`: The underlying type.
* `B`: The resulting type.

## Returns

* `(B)`: The result of the fold.

## Throws

* `NullPointerException` if the `fold` is `null`.

## Example

```java
List<String> letters = Arrays.asList("a", "b", "c");
BiFunction<String, String, String> fold = (accumulator, value) -> accumulator + value;
String initialValue = "";

Lists.foldRight(fold, initialValue, letters);
// => 'abc'
```
