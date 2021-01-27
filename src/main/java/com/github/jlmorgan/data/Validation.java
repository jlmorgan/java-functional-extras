package com.github.jlmorgan.data;

import static java.util.Collections.*;
import static java.util.Objects.hash;
import static com.github.jlmorgan.Objects.*;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import com.github.jlmorgan.Objects;

/**
 * The {@link Validation} type is a right-biased disjunction that represents two possibilities: either a {@code Invalid}
 * of {@code a} or a {@code Valid} of {@code b}. By convention, the {@link Validation} is used to represent a value or
 * invalid result of some function or process as a {@code Invalid} of the invalid message or a {@code Valid} of the
 * value.
 * @param <A> The underlying invalid type.
 * @param <B> The underlying valid type.
 */
public interface Validation<A, B> {
  /**
   * Curried implementation of {@link Validation#concat(Validation, Validation)}.
   */
  @NotNull
  @Contract(pure = true)
  static <F, S> Function<Validation<F, S>, Validation<F, S>> concat(final Validation<F, S> second) {
    return first -> concat(second, first);
  }

  /**
   * Concatenates two {@code Invalid} values together, replacing a {@code Valid} with the {@code Invalid}; otherwise,
   * take the first {@code Valid}.
   * @param second The second {@link Validation}.
   * @param first The first {@link Validation}.
   * @param <F> The underlying invalid type.
   * @param <S> The underlying valid type.
   * @return The first {@code Valid} for two valids, the first {@code Invalid} for mixed; otherwise, a
   * {@code Invalid} of the concatenation of the invalid values.
   * @throws IllegalArgumentException if the either {@link Validation} is {@code null}.
   */
  static <F, S> Validation<F, S> concat(final Validation<F, S> second, final Validation<F, S> first) {
    requireNonNull(second, "second validation must not be null");
    requireNonNull(first, "first validation must not be null");

    Validation<F, S> result = first;

    if (first.isValid() && second.isInvalid()) {
      result = second;
    } else if (second.isInvalid()) {
      result = invalid(Stream.of(first, second)
        .map(fromInvalid(emptyList()))
        .flatMap(Collection::stream)
        .collect(Collectors.toList())
      );
    }

    return result;
  }

  /**
   * Creates a {@code Invalid} from an arbitrary value.
   * @param value The value.
   * @param <F> The underlying invalid type.
   * @param <S> The underlying valid type.
   * @return A {@code Invalid} of the value.
   */
  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <F, S> Validation<F, S> invalid(final F value) {
    return new Invalid<>(value);
  }

  /**
   * Creates a {@code Invalid} from an arbitrary collection of values.
   * @param values The values.
   * @param <F> The underlying invalid type.
   * @param <S> The underlying valid type.
   * @return A {@code Invalid} of the values.
   */
  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <F, S> Validation<F, S> invalid(final Collection<F> values) {
    return new Invalid<>(values);
  }

  /**
   * Extracts from a collection of {@link Validation} all of the {@code Invalid} elements in extracted order. The
   * underlying collections are concatenated together.
   * @param collection The collection of {@link Validation}.
   * @param <F> The underlying invalid type.
   * @param <S> The underlying valid type.
   * @return The collection of underlying {@code Invalid} values.
   */
  static <F, S> List<F> invalids(final Collection<Validation<F, S>> collection) {
    return Optional.ofNullable(collection)
      .map(Collection::stream)
      .orElseGet(Stream::empty)
      .filter(Objects::isNotNull)
      .filter(Validation::isInvalid)
      .map(fromInvalid(null))
      .flatMap(Collection::stream)
      .collect(Collectors.toList());
  }

  /**
   * Curried implementation of {@link Validation#fromInvalid(Collection, Validation)}.
   */
  @NotNull
  @Contract(pure = true)
  static <F, S> Function<Validation<F, S>, Collection<F>> fromInvalid(final Collection<F> defaultValues) {
    return validation -> fromInvalid(defaultValues, validation);
  }

  /**
   * Extracts the value(s) of a {@code Invalid}; otherwise, returns the {@code defaultValues}.
   * @param defaultValues Values used if the {@code validation} is not a {@code Invalid}.
   * @param validation The {@link Validation}.
   * @param <F> The underlying invalid type.
   * @param <S> The underlying valid type.
   * @return The underlying invalid value(s) or the defaults.
   */
  @Contract("_, null -> param1")
  static <F, S> Collection<F> fromInvalid(final Collection<F> defaultValues, final Validation<F, S> validation) {
    return validation == null || validation.isValid()
      ? defaultValues
      : ((Invalid<F, S>) validation)._values;
  }

  /**
   * Curried implementation of {@link Validation#fromValid(Object, Validation)}.
   */
  @NotNull
  @Contract(pure = true)
  static <F, S> Function<Validation<F, S>, S> fromValid(final S defaultValue) {
    return validation -> fromValid(defaultValue, validation);
  }

  /**
   * Extracts the value of a {@code Valid}; otherwise, returns the {@code defaultValue}.
   * @param defaultValue Value used if the {@code validation} is not a {@code Valid}.
   * @param validation The {@link Validation}.
   * @param <F> The underlying invalid type.
   * @param <S> The underlying valid type.
   * @return The underlying valid value or the default.
   */
  @Contract("_, null -> param1")
  static <F, S> S fromValid(final S defaultValue, final Validation<F, S> validation) {
    return validation == null || validation.isInvalid()
      ? defaultValue
      : ((Valid<F, S>) validation)._value;
  }

  /**
   * Partitions a collection of {@link Validation} into two collections. All {@code Invalid} elements are extracted, in
   * order, to the first position of the output. Similarly for the {@code Valid} elements in the second position.
   * @param collection The collection of {@link Validation}.
   * @param <F> The underlying invalid type.
   * @param <S> The underlying valid type.
   * @return A couple of a collection of the underlying {@code Invalid} values and a collection of the underlying
   * {@code Valid} values.
   */
  @NotNull
  static <F, S> Tuple<Collection<F>, Collection<S>> partitionValidations(
    final Collection<Validation<F, S>> collection
  ) {
    return Tuple.of(invalids(collection), valids(collection));
  }

  /**
   * Creates a {@code Valid} from an arbitrary value.
   * @param value The value.
   * @param <F> The underlying invalid type.
   * @param <S> The underlying valid type.
   * @return A {@code Valid} of the value.
   */
  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <F, S> Validation<F, S> valid(final S value) {
    return new Valid<>(value);
  }

  /**
   * Extracts from a collection of {@link Validation} all of the {@code Valid} elements in extracted order.
   * @param collection The collection of {@link Validation}.
   * @param <F> The underlying invalid type.
   * @param <S> The underlying valid type.
   * @return The collection of underlying {@code Valid} values.
   */
  static <F, S> Collection<S> valids(final Collection<Validation<F, S>> collection) {
    return Optional.ofNullable(collection)
      .map(Collection::stream)
      .orElseGet(Stream::empty)
      .filter(Objects::isNotNull)
      .filter(Validation::isValid)
      .map(fromValid(null))
      .collect(Collectors.toList());
  }

  /**
   * Curried implementation of {@link Validation#validate(Predicate, Object, Object)}.
   */
  @NotNull
  @Contract(pure = true)
  static <F, S> Function<F, Function<S, Validation<F, S>>> validate(final Predicate<S> predicate) {
    return invalidValue -> validate(predicate, invalidValue);
  }

  /**
   * Partially curried implementation of {@link Validation#validate(Predicate, Object, Object)}.
   */
  @NotNull
  @Contract(pure = true)
  static <F, S> Function<S, Validation<F, S>> validate(final Predicate<S> predicate, final F invalidValue) {
    return value -> validate(predicate, invalidValue, value);
  }

  /**
   * Validates a value {@code b} and a {@code Valid} of {@code b} if the {@code predicate} returns
   * {@code true}; otherwise, a {@code Invalid} of {@code a}.
   * @param predicate The predicate.
   * @param invalidValue The invalid value.
   * @param value The value to test.
   * @param <F> The underlying invalid type.
   * @param <S> The underlying valid type.
   * @return A {@code Valid} of the {@code value} if the {@code predicate} returns {@code true}; otherwise, a
   * {@code Invalid} of {@code invalidValue}.
   */
  static <F, S> Validation<F, S> validate(final Predicate<S> predicate, final F invalidValue, final S value) {
    return requireNonNull(predicate, "predicate must not be null").test(value)
      ? valid(value)
      : invalid(invalidValue);
  }

  /**
   * Curried implementation of {@link Validation#validationMap(Function, Function, Validation)}.
   */
  @NotNull
  @Contract(pure = true)
  static <F, S, C> Function<Function<S, C>, Function<Validation<F, S>, C>> validationMap(
    final Function<? super Collection<F>, ? extends C> invalidMorphism
  ) {
    return validMorphism -> validationMap(invalidMorphism, validMorphism);
  }

  /**
   * Partially curried implementation of {@link Validation#validationMap(Function, Function, Validation)}.
   */
  @NotNull
  @Contract(pure = true)
  static <F, S, C> Function<Validation<F, S>, C> validationMap(
    final Function<? super Collection<F>, ? extends C> invalidMorphism,
    final Function<? super S, ? extends C> validMorphism
  ) {
    return validation -> validationMap(invalidMorphism, validMorphism, validation);
  }

  /**
   * Provides a catamorphism of the {@code validation} to a singular value. If the value is {@code Invalid f}, apply the
   * first function to {@code f}; otherwise, apply the second function to {@code s}.
   * @param invalidMorphism Maps the values of a {@code Invalid a} to {@code c}.
   * @param validMorphism Maps the value of a {@code Valid b} to {@code c}.
   * @param validation The {@link Validation}.
   * @param <F> The underlying invalid type.
   * @param <S> The underlying valid type.
   * @param <C> The return type.
   * @return The result of the catamorphism of the {@code validation}.
   * @throws IllegalArgumentException if the {@code invalidMorphism}, {@code validMorphism}, or {@code validation} is
   * {@code null}.
   */
  static <F, S, C> C validationMap(
    final Function<? super Collection<F>, ? extends C> invalidMorphism,
    final Function<? super S, ? extends C> validMorphism,
    final Validation<F, S> validation
  ) {
    return requireNonNull(validation, "validation must not be null").isValid()
      ? requireNonNull(validMorphism, "validMorphism must not be null")
        .apply(fromValid(null, validation))
      : requireNonNull(invalidMorphism, "invalidMorphism must not be null")
        .apply(fromInvalid(emptyList(), validation));
  }

  /**
   * Determines whether or not the instance is a {@code Invalid}.
   * @return {@code true} for a {@code Invalid}; otherwise, {@code false}.
   */
  default boolean isInvalid() {
    return !isValid();
  }

  /**
   * Determines whether or not the instance is a {@code Valid}.
   * @return {@code true} for a {@code Valid}; otherwise, {@code false}.
   */
  boolean isValid();

  /**
   * Encapsulates the invalid value(s) of the disjunction.
   * @param <A> The underlying invalid type.
   * @param <B> The underlying valid type.
   */
  class Invalid<A, B> implements Validation<A, B> {
    private final Collection<A> _values;

    Invalid(final A value) {
      _values = singletonList(value);
    }

    Invalid(final Collection<A> values) {
      _values = Optional.ofNullable(values)
        .orElseGet(Collections::emptyList);
    }

    /**
     * Determines whether or not the {@code other} has the same value as the current {@code instance}.
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
        final Invalid<?, ?> invalid = (Invalid<?, ?>) other;

        result = _values.equals(invalid._values);
      }

      return result;
    }

    /**
     * Generates a hash code representing the underlying values of the {@link Validation}.
     * @return The hash code.
     */
    @Override
    public int hashCode() {
      return hash(_values);
    }

    /**
     * Determines whether or not the instance is a {@code Valid}.
     * @return {@code true} for a {@code Valid}; otherwise, {@code false}.
     */
    @Override
    public boolean isValid() {
      return false;
    }

    /**
     * Converts the {@code instance} to a {@link String} representation. The collection of underlying values are joined
     * via a comma ({@code ,}).
     * @return The {@code instance} as a {@link String}.
     */
    @Override
    public String toString() {
      return String.format(
        "Invalid([%s])",
        _values.stream()
          .map(Object::toString)
          .collect(Collectors.joining(","))
      );
    }
  }

  /**
   * Encapsulates the valid value of the disjunction.
   * @param <A> The underlying invalid type.
   * @param <B> The underlying valid type.
   */
  class Valid<A, B> implements Validation<A, B> {
    private final B _value;

    Valid(final B value) {
      _value = value;
    }

    /**
     * Determines whether or not the {@code other} has the same value as the current {@code instance}.
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
        final Valid<?, ?> valid = (Valid<?, ?>) other;

        result = _value.equals(valid._value);
      }

      return result;
    }

    /**
     * Generates a hash code representing the underlying values of the {@link Validation}.
     * @return The hash code.
     */
    @Override
    public int hashCode() {
      return hash(_value);
    }

    /**
     * Determines whether or not the instance is a {@code Valid}.
     * @return {@code true} for a {@code Valid}; otherwise, {@code false}.
     */
    @Override
    public boolean isValid() {
      return true;
    }

    /**
     * Converts the {@code instance} to a {@link String} representation.
     * @return The {@code instance} as a {@link String}.
     */
    @Override
    public String toString() {
      return String.format("Valid(%s)", _value);
    }
  }
}
