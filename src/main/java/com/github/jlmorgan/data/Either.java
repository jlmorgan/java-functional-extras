package com.github.jlmorgan.data;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link Either} type is a right-biased disjunction that represents two possibilities: either a {@code Left} of
 * {@code a} or a {@code Right} of {@code b}. By convention, the {@link Either} is used to represent a value or an
 * error result of some function or process as a {@code Left} of the error or a {@code Right} of the value.
 * @param <A> The underlying left type.
 * @param <B> The underlying right type.
 */
public interface Either<A, B> {
  /**
   * Curried implementation of {@link Either#eitherMap(Function, Function, Either)}.
   */
  @NotNull
  @Contract(pure = true)
  static <L, R, C> Function<Function<R, C>, Function<Either<L, R>, C>> eitherMap(
    final Function<? super L, ? extends C> leftMorphism
  ) {
    return rightMorphism -> eitherMap(leftMorphism, rightMorphism);
  }

  /**
   * Partially curried implementation of {@link Either#eitherMap(Function, Function, Either)}.
   */
  @NotNull
  @Contract(pure = true)
  static <L, R, C> Function<Either<L, R>, C> eitherMap(
    final Function<? super L, ? extends C> leftMorphism,
    final Function<? super R, ? extends C> rightMorphism
  ) {
    return either -> eitherMap(leftMorphism, rightMorphism, either);
  }

  /**
   * Provides a catamorphism of the {@code either} to a singular value. If the value is {@code Left t}, apply the first
   * function to {@code t}; otherwise, apply the second function to {@code u}.
   * @param leftMorphism Maps the value of a {@code Left t} to {@code c}.
   * @param rightMorphism Maps the value of a {@code Right u} to {@code c}.
   * @param either The {@link Either}.
   * @param <L> The underlying left type.
   * @param <R> The underlying right type.
   * @param <C> The return type.
   * @return The result of the catamorphism of the {@code either}.
   * @see <a href="https://en.wikipedia.org/wiki/Catamorphism">Catamorphism</a>
   * @throws NullPointerException if the {@code leftMorphism}, {@code rightMorphism}, or {@code either} is {@code null}.
   */
  static <L, R, C> C eitherMap(
    final Function<? super L, ? extends C> leftMorphism,
    final Function<? super R, ? extends C> rightMorphism,
    final Either<L, R> either
  ) {
    return requireNonNull(either, "either must not be null").isRight()
      ? requireNonNull(rightMorphism, "rightMorphism must not be null").apply(fromRight(null, either))
      : requireNonNull(leftMorphism, "leftMorphism must not be null").apply(fromLeft(null, either));
  }

  /**
   * Curried implementation of {@link Either#fromLeft(Object, Either)}.
   */
  @NotNull
  @Contract(pure = true)
  static <L, R> Function<Either<L, R>, L> fromLeft(final L defaultValue) {
    return either -> fromLeft(defaultValue, either);
  }

  /**
   * Extracts the value of a {@code Left}; otherwise, returns the {@code defaultValue}.
   * @param defaultValue Value used if the {@code either} is not a {@code Left}.
   * @param either The {@link Either}.
   * @param <L> The underlying left type.
   * @param <R> The underlying right type.
   * @return The underlying left value or the default.
   */
  @Contract("_, null -> param1")
  static <L, R> L fromLeft(final L defaultValue, final Either<L, R> either) {
    return either == null || either.isRight()
      ? defaultValue
      : ((Left<L, R>) either)._value;
  }

  /**
   * Curried implementation of {@link Either#fromRight(Object, Either)}.
   */
  @NotNull
  @Contract(pure = true)
  static <L, R> Function<Either<L, R>, R> fromRight(final R defaultValue) {
    return either -> fromRight(defaultValue, either);
  }

  /**
   * Extracts the value of a {@code Right}; otherwise, returns the {@code defaultValue}.
   * @param defaultValue Value used if the {@code either} is not a {@code Right}.
   * @param either The {@link Either}.
   * @param <L> The underlying left type.
   * @param <R> The underlying right type.
   * @return The underlying right value or the default.
   */
  @Contract("_, null -> param1")
  static <L, R> R fromRight(final R defaultValue, final Either<L, R> either) {
    return either == null || either.isLeft()
      ? defaultValue
      : ((Right<L, R>) either)._value;
  }

  /**
   * Creates a {@code Left} from an arbitrary value.
   * @param value The value.
   * @param <L> The underlying left type.
   * @param <R> The underlying right type.
   * @return A {@code Left} of the value.
   */
  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <L, R> Either<L, R> left(final L value) {
    return new Left<>(value);
  }

  /**
   * Extracts from a collection of {@link Either} all of the {@code Left} elements in extracted order.
   * @param <L> The underlying left type.
   * @param <R> The underlying right type.
   * @param collection The collection of {@link Either}.
   * @return The collection of underlying {@code Left} values.
   */
  static <L, R> Collection<L> lefts(final Collection<Either<L, R>> collection) {
    return Optional.ofNullable(collection)
      .map(Collection::stream)
      .orElseGet(Stream::empty)
      .filter(Objects::nonNull)
      .filter(Either::isLeft)
      .map(fromLeft(null))
      .collect(Collectors.toList());
  }

  /**
   * Partitions a collection of {@link Either} into two lists. All {@code Left} elements are extracted, in order, to the
   * first position of the output. Similarly for the {@code Right} elements in the second position.
   * @param <L> The underlying left type.
   * @param <R> The underlying right type.
   * @param collection The collection of {@link Either}.
   * @return A couple of a collection of the underlying {@code Left} values and a collection of the underlying
   * {@code Right} values.
   */
  @NotNull
  static <L, R> Tuple<Collection<L>, Collection<R>> partitionEithers(final Collection<Either<L, R>> collection) {
    return Tuple.of(lefts(collection), rights(collection));
  }

  /**
   * Creates a {@code Right} from an arbitrary value.
   * @param value The value.
   * @param <L> The underlying left type.
   * @param <R> The underlying right type.
   * @return A {@code Right} of the value.
   */
  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <L, R> Either<L, R> right(final R value) {
    return new Right<>(value);
  }

  /**
   * Extracts from a collection of {@link Either} all of the {@code Right} elements in extracted order.
   * @param <L> The underlying left type.
   * @param <R> The underlying right type.
   * @param collection The collection of {@link Either}.
   * @return The collection of underlying {@code Right} values.
   */
  static <L, R> List<R> rights(final Collection<Either<L, R>> collection) {
    return Optional.ofNullable(collection)
      .map(Collection::stream)
      .orElseGet(Stream::empty)
      .filter(Objects::nonNull)
      .filter(Either::isRight)
      .map(fromRight(null))
      .collect(Collectors.toList());
  }

  /**
   * Determines whether or not the instance is a {@code Right}.
   * @return {@code true} for a {@code Right}; otherwise, {@code false}.
   */
  boolean isRight();

  /**
   * Determines whether or not the instance is a {@code Left}.
   * @return {@code true} for a {@code Left}; otherwise, {@code false}.
   */
  default boolean isLeft() {
    return !isRight();
  }

  /**
   * Encapsulates the left value of the disjunction.
   * @param <A> The underlying left type.
   * @param <B> The underlying right type.
   */
  class Left<A, B> implements Either<A, B> {
    private final A _value;

    Left(final A value) {
      _value = value;
    }

    /**
     * Determine whether or not the {@code other} has the same value as the current {@code instance}.
     * @param other The other object.
     * @return {@code true} for equality; otherwise, {@code false}.
     */
    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(final Object other) {
      boolean result = false;

      if (this == other) {
        result = true;
      } else if (other != null && getClass() == other.getClass()) {
        final Left<?, ?> left = (Left<?, ?>) other;

        result = _value.equals(left._value);
      }

      return result;
    }

    /**
     * Generates a hash code representing the underlying values of the {@link Either}.
     * @return The hash code.
     */
    @Override
    public int hashCode() {
      return Objects.hash(_value);
    }

    /**
     * Determines whether or not the instance is a {@code Right}.
     * @return {@code true} for a {@code Right}; otherwise, {@code false}.
     */
    @Override
    public boolean isRight() {
      return false;
    }

    /**
     * Converts the {@code instance} to a {@link String} representation.
     * @return The {@code instance} as a {@link String}.
     */
    @Override
    public String toString() {
      return String.format("Left(%s)", _value);
    }
  }

  /**
   * Encapsulates the right value of the disjunction.
   * @param <A> The underlying left type.
   * @param <B> The underlying right type.
   */
  class Right<A, B> implements Either<A, B> {
    private final B _value;

    Right(final B value) {
      _value = value;
    }

    /**
     * Determine whether or not the {@code other} has the same value as the current {@code instance}.
     * @param other The other object.
     * @return {@code true} for equality; otherwise, {@code false}.
     */
    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(final Object other) {
      boolean result = false;

      if (this == other) {
        result = true;
      } else if (other != null && getClass() == other.getClass()) {
        final Right<?, ?> right = (Right<?, ?>) other;

        result = _value.equals(right._value);
      }

      return result;
    }

    /**
     * Generates a hash code representing the underlying values of the {@link Either}.
     * @return The hash code.
     */
    @Override
    public int hashCode() {
      return Objects.hash(_value);
    }

    /**
     * Determines whether or not the instance is a {@code Right}.
     * @return {@code true} for a {@code Right}; otherwise, {@code false}.
     */
    @Override
    public boolean isRight() {
      return true;
    }

    /**
     * Converts the {@code instance} to a {@link String} representation.
     * @return The {@code instance} as a {@link String}.
     */
    @Override
    public String toString() {
      return String.format("Right(%s)", _value);
    }
  }
}
