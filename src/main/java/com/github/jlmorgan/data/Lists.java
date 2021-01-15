package com.github.jlmorgan.data;

import static com.github.jlmorgan.data.Tuple.tupleMap;
import static java.util.Collections.emptyList;
import static com.github.jlmorgan.Objects.*;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Lists {
  /**
   * Curry implementation of {@link Lists#append(List, List)}.
   */
  @NotNull
  @Contract(pure = true)
  public static <A> Function<List<A>, List<A>> append(final List<A> second) {
    return first -> append(second, first);
  }

  /**
   * Appends two lists together. {@code null} lists are treated as empty lists.
   *
   * @param second The list to append.
   * @param first The list on which to append.
   * @param <A> The underlying type.
   * @return The appended list.
   */
  public static <A> List<A> append(final List<A> second, final List<A> first) {
    return Stream.concat(
      Optional.ofNullable(first).map(List::stream).orElseGet(Stream::empty),
      Optional.ofNullable(second).map(List::stream).orElseGet(Stream::empty)
    ).collect(Collectors.toList());
  }

  /**
   * Creates an empty list.
   * @param <A> The underlying type.
   * @return The empty list.
   */
  @NotNull
  @Contract(pure = true)
  public static <A> List<A> empty() {
    return emptyList();
  }

  /**
   * Curried implementation of {@link Lists#foldLeft(BiFunction, Object, List)}.
   */
  @NotNull
  @Contract(pure = true)
  public static <A, B> Function<B, Function<List<A>, B>> foldLeft(final BiFunction<B, A, B> fold) {
    return initialValue -> foldLeft(fold, initialValue);
  }

  /**
   * Partially curried implementation of {@link Lists#foldLeft(BiFunction, Object, List)}.
   */
  @NotNull
  @Contract(pure = true)
  public static <A, B> Function<List<A>, B> foldLeft(final BiFunction<B, A, B> fold, final B initialValue) {
    return list -> foldLeft(fold, initialValue, list);
  }

  /**
   * Folds a list from last to head.
   *
   * @param fold Folding function.
   * @param initialValue Initial value.
   * @param list The list.
   * @param <A> The underlying type.
   * @param <B> The resulting type.
   * @return The result of the fold.
   * @throws IllegalArgumentException if the {@code fold} is {@code null}.
   */
  public static <A, B> B foldLeft(final BiFunction<B, A, B> fold, final B initialValue, final List<A> list) {
    requireNonNull(fold, "fold must not be null");
    B result = initialValue;

    if (isNotNull(list)) {
      final ListIterator<A> iterator = list.listIterator(list.size());

      while (iterator.hasPrevious()) {
        result = fold.apply(result, iterator.previous());
      }
    }

    return result;
  }

  /**
   * Curried implementation of {@link Lists#foldRight(BiFunction, Object, List)}.
   */
  @NotNull
  @Contract(pure = true)
  public static <A, B> Function<B, Function<List<A>, B>> foldRight(final BiFunction<A, B, B> fold) {
    return initialValue -> foldRight(fold, initialValue);
  }

  /**
   * Partially curried implementation of {@link Lists#foldRight(BiFunction, Object, List)}.
   */
  @NotNull
  @Contract(pure = true)
  public static <A, B> Function<List<A>, B> foldRight(final BiFunction<A, B, B> fold, final B initialValue) {
    return list -> foldRight(fold, initialValue, list);
  }

  /**
   * Folds a list from head to last.
   *
   * @param fold Folding function.
   * @param initialValue Starting value.
   * @param list The list.
   * @param <A> The underlying type.
   * @param <B> The resulting type.
   * @return The result of the fold.
   * @throws IllegalArgumentException if the {@code fold} is {@code null}.
   */
  public static <A, B> B foldRight(final BiFunction<A, B, B> fold, final B initialValue, final List<A> list) {
    requireNonNull(fold, "fold must not be null");
    B result = initialValue;

    if (isNotNull(list)) {
      for (final A value : list) {
        result = fold.apply(value, result);
      }
    }

    return result;
  }

  /**
   * Extracts the first element of a non-null, non-empty list.
   *
   * @param list The list.
   * @param <A> The underlying type.
   * @return The first element.
   * @throws IllegalArgumentException if the {@code list} is {@code null} or empty.
   */
  public static <A> A head(final List<A> list) {
    return Optional.ofNullable(list)
      .filter(Lists::isNotEmpty)
      .map(items -> items.get(0))
      .orElseThrow(() -> new IllegalArgumentException("list must not be null or empty"));
  }

  /**
   * Extracts the elements of a non-null, non-empty list excluding the last element.
   *
   * @param list The list.
   * @param <A> The underlying type.
   * @return The initial part of the list.
   * @throws IllegalArgumentException if the {@code list} is {@code null} or empty.
   */
  public static <A> List<A> init(final List<A> list) {
    return Optional.ofNullable(list)
      .filter(Lists::isNotEmpty)
      .map(items -> items.subList(0, items.size() - 1))
      .orElseThrow(() -> new IllegalArgumentException("list must not be null or empty"));
  }

  /**
   * Determines whether or not the list is {@code null} or empty.
   *
   * @param list The list.
   * @param <A> The underlying type.
   * @return {@code true} if the {@code list} is empty; otherwise, {@code false}.
   */
  public static <A> boolean isEmpty(final List<A> list) {
    return isNull(list) || list.size() == 0;
  }

  /**
   * Determines whether or not the list is not {@code null} or empty.
   *
   * @param list The list.
   * @param <A> The underlying type.
   * @return {@code true} if the {@code list} is not empty; otherwise, {@code false}.
   */
  public static <A> boolean isNotEmpty(final List<A> list) {
    return !isEmpty(list);
  }

  /**
   * Extracts the last element of a non-null, non-empty list.
   *
   * @param list The list.
   * @param <A> The underlying type.
   * @return The last element.
   * @throws IllegalArgumentException if the {@code list} is {@code null} or empty.
   */
  public static <A> A last(final List<A> list) {
    return Optional.ofNullable(list)
      .filter(Lists::isNotEmpty)
      .map(items -> items.get(items.size() - 1))
      .orElseThrow(() -> new IllegalArgumentException("list must not be null or empty"));
  }

  /**
   * Gets the length of the {@code list}; otherwise, defaults to {@code 0}.
   *
   * @param list The list.
   * @param <A> The underlying type.
   * @return The length of the list.
   */
  public static <A> int length(final List<A> list) {
    return Optional.ofNullable(list)
      .filter(Lists::isNotEmpty)
      .map(List::size)
      .orElse(0);
  }

  /**
   * Curried implementation of {@link Lists#map(Function, List)}.
   */
  @NotNull
  @Contract(pure = true)
  public static <A, B> Function<List<A>, List<B>> map(final Function<A, B> morphism) {
    return list -> map(morphism, list);
  }

  /**
   * Maps the values of the {@code list} into a new list.
   *
   * @param morphism The mapping function.
   * @param list The list.
   * @param <A> The underlying type.
   * @param <B> The mapped type.
   * @return The mapped list.
   * @throws IllegalArgumentException if the {@code morphism} is {@code null}.
   */
  public static <A, B> List<B> map(final Function<A, B> morphism, final List<A> list) {
    requireNonNull(morphism, "morphism must not be null");

    return Optional.ofNullable(list)
      .orElseGet(Lists::empty)
      .stream()
      .map(morphism)
      .collect(Collectors.toList());
  }

  /**
   * All elements of the list but the first or an empty list.
   *
   * @param list The list.
   * @param <A> The underlying type.
   * @return The tail part of the list.
   * @throws IllegalArgumentException if the {@code list} is {@code null} or empty.
   */
  public static <A> List<A> tail(final List<A> list) {
    return Optional.ofNullable(list)
      .filter(Lists::isNotEmpty)
      .map(items -> items.subList(1, items.size()))
      .orElseThrow(() -> new IllegalArgumentException("list must not be null or empty"));
  }

  /**
   * Decomposes a list into its head and tail. Returns {@code Nothing} if the {@code list} is empty; otherwise, a
   * {@code Just (x, xs)} where {@code x} is the head of the {@code list} and {@code xs} is the tail.
   *
   * @param list The list.
   * @param <A> The underlying type.
   * @return {@code Just} of the head and tail; otherwise, {@code Nothing}.
   */
  public static <A> Maybe<Tuple<A, List<A>>> uncons(final List<A> list) {
    return Maybe.of(list)
      .filter(Lists::isNotEmpty)
      .fmap(tupleMap(Lists::head, Lists::tail));
  }
}
