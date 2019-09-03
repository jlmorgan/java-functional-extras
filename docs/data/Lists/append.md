# `Lists.<A>append(second, first)`

Appends two lists together.

## Alternatives

* `Lists.<A>append(second).apply(first)`

## Arguments

* `second (List<A>)`: The list to append.
* `first (List<A>)`: The list on which to append.

## Types

* `A`: The underlying type.

## Returns

* `(List<A>)`: The appended list.

## Example

```java
List<int> second = Arrays.asList(2, 3);
List<int> first = Arrays.asList(0, 1);

append(second, first);
// => [0, 1, 2, 3]
```
