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
 * The {@link Validation} type is a right-biased disjunction that represents two possibilities: either a {@code Failure}
 * of {@code a} or a {@code Success} of {@code b}. By convention, the {@link Validation} is used to represent a value or
 * failure result of some function or process as a {@code Failure} of the failure message or a {@code Success} of the
 * value.
 * @param <A> The underlying failure type.
 * @param <B> The underlying success type.
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
   * Concatenates two {@code Failure} values together, replacing a {@code Success} with the {@code Failure}; otherwise,
   * take the first {@code Success}.
   * @param second The second {@link Validation}.
   * @param first The first {@link Validation}.
   * @param <F> The underlying failure type.
   * @param <S> The underlying success type.
   * @return The first {@code Success} for two successes, the first {@code Failure} for mixed; otherwise, a
   * {@code Failure} of the concatenation of the failure values.
   * @throws IllegalArgumentException if the either {@link Validation} is {@code null}.
   */
  static <F, S> Validation<F, S> concat(final Validation<F, S> second, final Validation<F, S> first) {
    requireNonNull(second, "second validation must not be null");
    requireNonNull(first, "first validation must not be null");

    Validation<F, S> result = first;

    if (first.isSuccess() && second.isFailure()) {
      result = second;
    } else if (second.isFailure()) {
      result = failure(Stream.of(first, second)
        .map(fromFailure(emptyList()))
        .flatMap(Collection::stream)
        .collect(Collectors.toList())
      );
    }

    return result;
  }

  /**
   * Creates a {@code Failure} from an arbitrary value.
   * @param value The value.
   * @param <F> The underlying failure type.
   * @param <S> The underlying success type.
   * @return A {@code Failure} of the value.
   */
  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <F, S> Validation<F, S> failure(final F value) {
    return new Failure<>(value);
  }

  /**
   * Creates a {@code Failure} from an arbitrary collection of values.
   * @param values The values.
   * @param <F> The underlying failure type.
   * @param <S> The underlying success type.
   * @return A {@code Failure} of the values.
   */
  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <F, S> Validation<F, S> failure(final Collection<F> values) {
    return new Failure<>(values);
  }

  /**
   * Extracts from a collection of {@link Validation} all of the {@code Failure} elements in extracted order. The
   * underlying collections are concatenated together.
   * @param collection The collection of {@link Validation}.
   * @param <F> The underlying failure type.
   * @param <S> The underlying success type.
   * @return The collection of underlying {@code Failure} values.
   */
  static <F, S> List<F> failures(final Collection<Validation<F, S>> collection) {
    return Optional.ofNullable(collection)
      .map(Collection::stream)
      .orElseGet(Stream::empty)
      .filter(Objects::isNotNull)
      .filter(Validation::isFailure)
      .map(fromFailure(null))
      .flatMap(Collection::stream)
      .collect(Collectors.toList());
  }

  /**
   * Curried implementation of {@link Validation#fromFailure(Collection, Validation)}.
   */
  @NotNull
  @Contract(pure = true)
  static <F, S> Function<Validation<F, S>, Collection<F>> fromFailure(final Collection<F> defaultValues) {
    return validation -> fromFailure(defaultValues, validation);
  }

  /**
   * Extracts the value(s) of a {@code Failure}; otherwise, returns the {@code defaultValues}.
   * @param defaultValues Values used if the {@code validation} is not a {@code Failure}.
   * @param validation The {@link Validation}.
   * @param <F> The underlying failure type.
   * @param <S> The underlying success type.
   * @return The underlying failure value(s) or the defaults.
   */
  @Contract("_, null -> param1")
  static <F, S> Collection<F> fromFailure(final Collection<F> defaultValues, final Validation<F, S> validation) {
    return validation == null || validation.isSuccess()
      ? defaultValues
      : ((Failure<F, S>) validation)._values;
  }

  /**
   * Curried implementation of {@link Validation#fromSuccess(Object, Validation)}.
   */
  @NotNull
  @Contract(pure = true)
  static <F, S> Function<Validation<F, S>, S> fromSuccess(final S defaultValue) {
    return validation -> fromSuccess(defaultValue, validation);
  }

  /**
   * Extracts the value of a {@code Success}; otherwise, returns the {@code defaultValue}.
   * @param defaultValue Value used if the {@code validation} is not a {@code Success}.
   * @param validation The {@link Validation}.
   * @param <F> The underlying failure type.
   * @param <S> The underlying success type.
   * @return The underlying success value or the default.
   */
  @Contract("_, null -> param1")
  static <F, S> S fromSuccess(final S defaultValue, final Validation<F, S> validation) {
    return validation == null || validation.isFailure()
      ? defaultValue
      : ((Success<F, S>) validation)._value;
  }

  /**
   * Partitions a collection of {@link Validation} into two collections. All {@code Failure} elements are extracted, in
   * order, to the first position of the output. Similarly for the {@code Success} elements in the second position.
   * @param collection The collection of {@link Validation}.
   * @param <F> The underlying failure type.
   * @param <S> The underlying success type.
   * @return A couple of a collection of the underlying {@code Failure} values and a collection of the underlying
   * {@code Success} values.
   */
  @NotNull
  static <F, S> Tuple<Collection<F>, Collection<S>> partitionValidations(
    final Collection<Validation<F, S>> collection
  ) {
    return Tuple.of(failures(collection), successes(collection));
  }

  /**
   * Creates a {@code Success} from an arbitrary value.
   * @param value The value.
   * @param <F> The underlying failure type.
   * @param <S> The underlying success type.
   * @return A {@code Success} of the value.
   */
  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <F, S> Validation<F, S> success(final S value) {
    return new Success<>(value);
  }

  /**
   * Extracts from a collection of {@link Validation} all of the {@code Success} elements in extracted order.
   * @param collection The collection of {@link Validation}.
   * @param <F> The underlying failure type.
   * @param <S> The underlying success type.
   * @return The collection of underlying {@code Success} values.
   */
  static <F, S> Collection<S> successes(final Collection<Validation<F, S>> collection) {
    return Optional.ofNullable(collection)
      .map(Collection::stream)
      .orElseGet(Stream::empty)
      .filter(Objects::isNotNull)
      .filter(Validation::isSuccess)
      .map(fromSuccess(null))
      .collect(Collectors.toList());
  }

  /**
   * Curried implementation of {@link Validation#validate(Predicate, Object, Object)}.
   */
  @NotNull
  @Contract(pure = true)
  static <F, S> Function<F, Function<S, Validation<F, S>>> validate(final Predicate<S> predicate) {
    return failureValue -> validate(predicate, failureValue);
  }

  /**
   * Partially curried implementation of {@link Validation#validate(Predicate, Object, Object)}.
   */
  @NotNull
  @Contract(pure = true)
  static <F, S> Function<S, Validation<F, S>> validate(final Predicate<S> predicate, final F failureValue) {
    return value -> validate(predicate, failureValue, value);
  }

  /**
   * Validates a value {@code b} and a {@code Success} of {@code b} if the {@code predicate} returns
   * {@code true}; otherwise, a {@code Failure} of {@code a}.
   * @param predicate The predicate.
   * @param failureValue The failure value.
   * @param value The value to test.
   * @param <F> The underlying failure type.
   * @param <S> The underlying success type.
   * @return A {@code Success} of the {@code value} if the {@code predicate} returns {@code true}; otherwise, a
   * {@code Failure} of {@code failureValue}.
   */
  static <F, S> Validation<F, S> validate(final Predicate<S> predicate, final F failureValue, final S value) {
    return requireNonNull(predicate, "predicate must not be null").test(value)
      ? success(value)
      : failure(failureValue);
  }

  /**
   * Curried implementation of {@link Validation#validationMap(Function, Function, Validation)}.
   */
  @NotNull
  @Contract(pure = true)
  static <F, S, C> Function<Function<S, C>, Function<Validation<F, S>, C>> validationMap(
    final Function<? super Collection<F>, ? extends C> failureMorphism
  ) {
    return successMorphism -> validationMap(failureMorphism, successMorphism);
  }

  /**
   * Partially curried implementation of {@link Validation#validationMap(Function, Function, Validation)}.
   */
  @NotNull
  @Contract(pure = true)
  static <F, S, C> Function<Validation<F, S>, C> validationMap(
    final Function<? super Collection<F>, ? extends C> failureMorphism,
    final Function<? super S, ? extends C> successMorphism
  ) {
    return validation -> validationMap(failureMorphism, successMorphism, validation);
  }

  /**
   * Provides a catamorphism of the {@code validation} to a singular value. If the value is {@code Failure f}, apply the
   * first function to {@code f}; otherwise, apply the second function to {@code s}.
   * @param failureMorphism Maps the values of a {@code Failure a} to {@code c}.
   * @param successMorphism Maps the value of a {@code Success b} to {@code c}.
   * @param validation The {@link Validation}.
   * @param <F> The underlying failure type.
   * @param <S> The underlying success type.
   * @param <C> The return type.
   * @return The result of the catamorphism of the {@code validation}.
   * @throws IllegalArgumentException if the {@code failureMorphism}, {@code successMorphism}, or {@code validation} is
   * {@code null}.
   */
  static <F, S, C> C validationMap(
    final Function<? super Collection<F>, ? extends C> failureMorphism,
    final Function<? super S, ? extends C> successMorphism,
    final Validation<F, S> validation
  ) {
    return requireNonNull(validation, "validation must not be null").isSuccess()
      ? requireNonNull(successMorphism, "successMorphism must not be null")
        .apply(fromSuccess(null, validation))
      : requireNonNull(failureMorphism, "failureMorphism must not be null")
        .apply(fromFailure(emptyList(), validation));
  }

  /**
   * Determines whether or not the instance is a {@code Failure}.
   * @return {@code true} for a {@code Failure}; otherwise, {@code false}.
   */
  default boolean isFailure() {
    return !isSuccess();
  }

  /**
   * Determines whether or not the instance is a {@code Success}.
   * @return {@code true} for a {@code Success}; otherwise, {@code false}.
   */
  boolean isSuccess();

  /**
   * Encapsulates the failure value(s) of the disjunction.
   * @param <A> The underlying failure type.
   * @param <B> The underlying success type.
   */
  class Failure<A, B> implements Validation<A, B> {
    private final Collection<A> _values;

    Failure(final A value) {
      _values = singletonList(value);
    }

    Failure(final Collection<A> values) {
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
        final Failure<?, ?> failure = (Failure<?, ?>) other;

        result = _values.equals(failure._values);
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
     * Determines whether or not the instance is a {@code Success}.
     * @return {@code true} for a {@code Success}; otherwise, {@code false}.
     */
    @Override
    public boolean isSuccess() {
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
        "Failure([%s])",
        _values.stream()
          .map(Object::toString)
          .collect(Collectors.joining(","))
      );
    }
  }

  /**
   * Encapsulates the success value of the disjunction.
   * @param <A> The underlying failure type.
   * @param <B> The underlying success type.
   */
  class Success<A, B> implements Validation<A, B> {
    private final B _value;

    Success(final B value) {
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
        final Success<?, ?> success = (Success<?, ?>) other;

        result = _value.equals(success._value);
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
     * Determines whether or not the instance is a {@code Success}.
     * @return {@code true} for a {@code Success}; otherwise, {@code false}.
     */
    @Override
    public boolean isSuccess() {
      return true;
    }

    /**
     * Converts the {@code instance} to a {@link String} representation.
     * @return The {@code instance} as a {@link String}.
     */
    @Override
    public String toString() {
      return String.format("Success(%s)", _value);
    }
  }
}
