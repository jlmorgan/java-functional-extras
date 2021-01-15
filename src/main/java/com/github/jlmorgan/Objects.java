package com.github.jlmorgan;

/**
 * Collection of utility methods for dealing with objects.
 */
public class Objects {
  /**
   * Determines whether or not the {@code instance} is {@code null}.
   *
   * @param instance The instance.
   * @return {@code true} is the {@code instance} is {@code null}; otherwise, {@code false}.
   */
  public static boolean isNull(final Object instance) {
    return instance == null;
  }

  /**
   * Determines whether or not the {@code instance} is not {@code null}.
   *
   * @param instance The instance.
   * @return {@code true} is the {@code instance} is not {@code null}; otherwise, {@code false}.
   */
  public static boolean isNotNull(Object instance) {
    return !isNull(instance);
  }
  /**
   * Throws an exception of the {@code instance} is {@code null}.
   *
   * @param instance The instance.
   * @param message The message for the exception.
   * @param <T> The type of the instance.
   * @return The instance.
   * @throws IllegalArgumentException if the {@code instance} is {@code null}.
   */
  public static <T> T requireNonNull(final T instance, final String message) {
    if (instance == null) {
      throw new IllegalArgumentException(message);
    }

    return instance;
  }
}
