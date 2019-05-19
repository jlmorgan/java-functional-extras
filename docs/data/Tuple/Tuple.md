# `Tuple<A, B>`

## Types

* `A`: The `first` type parameter.
* `B`: The `second` type parameter.

## Static Methods

* [`Function<B, Tuple<A, B>> of<A, B>(A first)`][of]
* [`Tuple<A, B> of<A, B>(A first, B second)`][of]

## Instance Methods

See [SimpleTuple](../../../src/main/java/com/github/jlmorgan/data/Tuple.java)

* `A first()`: The first element.
* `B second()`: The second element.
* `Tuple<B, A> swap()`: Creates a new `Tuple` with swapped values.

[of]: ./static/of.md
