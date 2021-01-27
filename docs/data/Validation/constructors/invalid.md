# `Validation.<F, S>invalid(F value)`

Encapsulates a invalid value.

## Alternatives

* `Validation.<F, S>invalid(Collection<F> values)`

## Arguments

* `value (F)`: The value.

### Argument Overloads

* `values (Collection<F>)`: The values.

## Types

* `F`: The underlying invalid type.
* `S`: The underlying valid type.

## Returns

* `(Validation<F, S>)`: An `Validation` of the `value`.

## Example

```java
Validation.<Integer, String>invalid(0);
// => Invalid([0])

Validation.<Integer, String>invalid(Arrays.asList(0, 1, 2));
// => Invalid([0, 1, 2])
```
