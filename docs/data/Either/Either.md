# `Either<A, B>`

## Types

* `A`: The underlying left type.
* `B`: The underlying right type.

## Constructors

* [`Either<L, R> left(L value)`][left]
* [`Either<L, R> right(R value)`][right]

## Static Methods

* [`Function<Function<R, C>, Function<Either<L, R>, C>> eitherMap(Function<L, C>)`][eitherMap]
* [`Function<Either<L, R>, C> eitherMap(Function<L, C>, Function<R, C>)`][eitherMap]
* [`C eitherMap(Function<L, C>, Function<R, C>, Either<L, R>)`][eitherMap]
* [`Function<Either<L, R>, L> fromLeft(L defaultValue)`][fromLeft]
* [`L fromLeft(L defaultValue, Either<L, R> either)`][fromLeft]
* [`Function<Either<L, R>, R> fromRight(R defaultValue)`][fromRight]
* [`R fromRight(R defaultValue, Either<L, R> either)`][fromRight]
* [`Collection<L> lefts(Collection<Either<L, R>> collection)`][lefts]
* [`Tuple<Collection<L>, Collection<R>> partitionEithers(Collection<Either<L, R>> collection)`][partitionEithers]
* [`Collection<R> rights(Collection<Either<L, R>> collection)`][rights]

## Instance Methods

* `boolean isLeft()`: Determines whether or not the `Either` is a `Left`.
* `boolean isRight()`: Determines whether or not the `Either` is a `Right`.

[eitherMap]: ./static/eitherMap.md
[fromLeft]: ./static/fromLeft.md
[fromRight]: ./static/fromRight.md
[left]: ./constructors/left.md
[lefts]: ./static/lefts.md
[partitionEithers]: ./static/partitionEithers.md
[right]: ./constructors/right.md
[rights]: ./static/rights.md
