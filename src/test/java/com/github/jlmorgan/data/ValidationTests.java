package com.github.jlmorgan.data;

import static java.util.Collections.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Validation")
class ValidationTests {
  @Nested
  class DescribeStaticMethods {
    @Nested
    class DescribeConcat {
      @Test
      void shouldThrowExceptionForNullSecond() {
        final Validation<String, UUID> testSecond = null;
        final Validation<String, UUID> testFirst = Validation.success(UUID.randomUUID());

        // noinspection ConstantConditions
        assertThrows(
          IllegalArgumentException.class,
          () -> Validation.concat(testSecond).apply(testFirst)
        );
      }

      @Test
      void shouldThrowExceptionForNullFirst() {
        final Validation<String, UUID> testSecond = Validation.success(UUID.randomUUID());
        final Validation<String, UUID> testFirst = null;

        // noinspection ConstantConditions
        assertThrows(
          IllegalArgumentException.class,
          () -> Validation.concat(testSecond).apply(testFirst)
        );
      }

      @Test
      void shouldReturnFirstForBothSuccesses() {
        final Validation<String, UUID> testSecond = Validation.success(UUID.randomUUID());
        final Validation<String, UUID> testFirst = Validation.success(UUID.randomUUID());
        // noinspection UnnecessaryLocalVariable
        final Validation<String, UUID> expectedResult = testFirst;
        final Validation<String, UUID> actualResult = Validation.concat(testSecond).apply(testFirst);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnFirstForFirstFailure() {
        final Validation<String, UUID> testSecond = Validation.success(UUID.randomUUID());
        final Validation<String, UUID> testFirst = Validation.failure(UUID.randomUUID().toString());
        // noinspection UnnecessaryLocalVariable
        final Validation<String, UUID> expectedResult = testFirst;
        final Validation<String, UUID> actualResult = Validation.concat(testSecond).apply(testFirst);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnSecondForSecondFailure() {
        final Validation<String, UUID> testSecond = Validation.failure(UUID.randomUUID().toString());
        final Validation<String, UUID> testFirst = Validation.success(UUID.randomUUID());
        // noinspection UnnecessaryLocalVariable
        final Validation<String, UUID> expectedResult = testSecond;
        final Validation<String, UUID> actualResult = Validation.concat(testSecond).apply(testFirst);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnedConcatenatedFailures() {
        final String testValue1 = UUID.randomUUID().toString();
        final String testValue2 = UUID.randomUUID().toString();
        final Validation<String, UUID> testSecond = Validation.failure(testValue2);
        final Validation<String, UUID> testFirst = Validation.failure(testValue1);
        final Validation<String, UUID> expectedResult = Validation.failure(Arrays.asList(testValue1, testValue2));
        final Validation<String, UUID> actualResult = Validation.concat(testSecond).apply(testFirst);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeFailures {
      @Test
      void shouldReturnEmptyCollectionForNullCollection() {
        final Collection<Validation<String, UUID>> testCollection = null;
        final Collection<String> expectedResult = emptyList();
        // noinspection ConstantConditions
        final Collection<String> actualResult = Validation.failures(testCollection);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyCollectionForEmptyCollection() {
        final Collection<Validation<String, UUID>> testCollection = emptyList();
        final Collection<String> expectedResult = emptyList();
        final Collection<String> actualResult = Validation.failures(testCollection);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyCollectionForBlankCollection() {
        final Collection<Validation<String, UUID>> testCollection = singletonList(null);
        final Collection<String> expectedResult = emptyList();
        final Collection<String> actualResult = Validation.failures(testCollection);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnCollectionOfFailureValuesForMixedCollection() {
        final String testFailureValue1 = UUID.randomUUID().toString();
        final String testFailureValue2 = UUID.randomUUID().toString();
        final UUID testSuccessValue1 = UUID.randomUUID();
        final UUID testSuccessValue2 = UUID.randomUUID();
        final Collection<Validation<String, UUID>> testCollection = Arrays.asList(
          Validation.failure(testFailureValue1),
          Validation.failure(testFailureValue2),
          Validation.success(testSuccessValue1),
          Validation.success(testSuccessValue2)
        );
        final Collection<String> expectedResult = Arrays.asList(
          testFailureValue1,
          testFailureValue2
        );
        final Collection<String> actualResult = Validation.failures(testCollection);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeFromFailure {
      private final Collection<String> _testDefaultValues = Arrays.asList(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString()
      );

      @Test
      void shouldReturnDefaultValueForNullValidation() {
        final Validation<String, UUID> testValidation = null;
        // noinspection UnnecessaryLocalVariable
        final Collection<String> expectedResult = _testDefaultValues;
        // noinspection ConstantConditions
        final Collection<String> actualResult = Validation.<String, UUID>fromFailure(_testDefaultValues)
          .apply(testValidation);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnDefaultValueForSuccess() {
        final Validation<String, UUID> testValidation = Validation.success(UUID.randomUUID());
        // noinspection UnnecessaryLocalVariable
        final Collection<String> expectedResult = _testDefaultValues;
        final Collection<String> actualResult = Validation.<String, UUID>fromFailure(_testDefaultValues)
          .apply(testValidation);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnFailureValueFromFailure() {
        final String testFailureValue = UUID.randomUUID().toString();
        final Validation<String, UUID> testValidation = Validation.failure(testFailureValue);
        final Collection<String> expectedResult = singletonList(testFailureValue);
        final Collection<String> actualResult = Validation.<String, UUID>fromFailure(_testDefaultValues)
          .apply(testValidation);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeFromSuccess {
      private final UUID _testDefaultValue = UUID.randomUUID();

      @Test
      void shouldReturnDefaultValueForNullValidation() {
        final Validation<String, UUID> testValidation = null;
        // noinspection UnnecessaryLocalVariable
        final UUID expectedResult = _testDefaultValue;
        // noinspection ConstantConditions
        final UUID actualResult = Validation.<String, UUID>fromSuccess(_testDefaultValue)
          .apply(testValidation);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnDefaultValueForSuccess() {
        final Validation<String, UUID> testValidation = Validation.failure(UUID.randomUUID().toString());
        // noinspection UnnecessaryLocalVariable
        final UUID expectedResult = _testDefaultValue;
        final UUID actualResult = Validation.<String, UUID>fromSuccess(_testDefaultValue)
          .apply(testValidation);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnSuccessValueForSuccess() {
        final UUID testSuccessValue = UUID.randomUUID();
        final Validation<String, UUID> testValidation = Validation.success(testSuccessValue);
        // noinspection UnnecessaryLocalVariable
        final UUID expectedResult = testSuccessValue;
        final UUID actualResult = Validation.<String, UUID>fromSuccess(_testDefaultValue)
          .apply(testValidation);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribePartitionValidations {
      @Test
      void shouldReturnEmptyCollectionsForNullCollection() {
        final Collection<Validation<String, UUID>> testCollection = null;
        final Tuple<Collection<String>, Collection<UUID>> expectedResult = Tuple.of(
          emptyList(),
          emptyList()
        );
        // noinspection ConstantConditions
        final Tuple<Collection<String>, Collection<UUID>> actualResult = Validation.partitionValidations(
          testCollection
        );

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyCollectionsForEmptyCollection() {
        final Collection<Validation<String, UUID>> testCollection = emptyList();
        final Tuple<Collection<String>, Collection<UUID>> expectedResult = Tuple.of(
          emptyList(),
          emptyList()
        );
        final Tuple<Collection<String>, Collection<UUID>> actualResult = Validation.partitionValidations(
          testCollection
        );

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyCollectionsForBlankCollection() {
        final Collection<Validation<String, UUID>> testCollection = singletonList(null);
        final Tuple<Collection<String>, Collection<UUID>> expectedResult = Tuple.of(
          emptyList(),
          emptyList()
        );
        final Tuple<Collection<String>, Collection<UUID>> actualResult = Validation.partitionValidations(
          testCollection
        );

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnCollectionsForMixedCollection() {
        final String testFailureValue1 = UUID.randomUUID().toString();
        final String testFailureValue2 = UUID.randomUUID().toString();
        final UUID testSuccessValue1 = UUID.randomUUID();
        final UUID testSuccessValue2 = UUID.randomUUID();
        final Collection<Validation<String, UUID>> testCollection = Arrays.asList(
          Validation.failure(testFailureValue1),
          Validation.failure(testFailureValue2),
          Validation.success(testSuccessValue1),
          Validation.success(testSuccessValue2)
        );
        final Tuple<Collection<String>, Collection<UUID>> expectedResult = Tuple.of(
          Arrays.asList(
            testFailureValue1,
            testFailureValue2
          ),
          Arrays.asList(
            testSuccessValue1,
            testSuccessValue2
          )
        );
        final Tuple<Collection<String>, Collection<UUID>> actualResult = Validation.partitionValidations(
          testCollection
        );

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeSuccesses {
      @Test
      void shouldReturnEmptyCollectionForNullCollection() {
        final Collection<Validation<String, UUID>> testCollection = null;
        final Collection<UUID> expectedResult = emptyList();
        // noinspection ConstantConditions
        final Collection<UUID> actualResult = Validation.successes(testCollection);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyCollectionForEmptyCollection() {
        final Collection<Validation<String, UUID>> testCollection = emptyList();
        final Collection<UUID> expectedResult = emptyList();
        final Collection<UUID> actualResult = Validation.successes(testCollection);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyCollectionForBlankCollection() {
        final Collection<Validation<String, UUID>> testCollection = singletonList(null);
        final Collection<UUID> expectedResult = emptyList();
        final Collection<UUID> actualResult = Validation.successes(testCollection);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnCollectionOfFailureValuesForMixedCollection() {
        final String testFailureValue1 = UUID.randomUUID().toString();
        final String testFailureValue2 = UUID.randomUUID().toString();
        final UUID testSuccessValue1 = UUID.randomUUID();
        final UUID testSuccessValue2 = UUID.randomUUID();
        final Collection<Validation<String, UUID>> testCollection = Arrays.asList(
          Validation.failure(testFailureValue1),
          Validation.failure(testFailureValue2),
          Validation.success(testSuccessValue1),
          Validation.success(testSuccessValue2)
        );
        final Collection<UUID> expectedResult = Arrays.asList(
          testSuccessValue1,
          testSuccessValue2
        );
        final Collection<UUID> actualResult = Validation.successes(testCollection);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeValidate {
      private final String _testFailureValue = UUID.randomUUID().toString();
      private final UUID _testValue = UUID.randomUUID();

      @Test
      void shouldThrowExceptionForNullPredicate() {
        final Predicate<UUID> testPredicate = null;

        // noinspection ConstantConditions
        assertThrows(
          IllegalArgumentException.class,
          () -> Validation.<String, UUID>validate(testPredicate).apply(_testFailureValue).apply(_testValue)
        );
      }

      @Test
      void shouldReturnFailureForFalsePredicate() {
        final Predicate<UUID> testPredicate = ignored -> false;
        final Validation<String, UUID> expectedResult = Validation.failure(_testFailureValue);
        final Validation<String, UUID> actualResult = Validation.<String, UUID>validate(testPredicate)
          .apply(_testFailureValue)
          .apply(_testValue);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnSuccessForTruePredicate() {
        final Predicate<UUID> testPredicate = ignored -> true;
        final Validation<String, UUID> expectedResult = Validation.success(_testValue);
        final Validation<String, UUID> actualResult = Validation.<String, UUID>validate(testPredicate)
          .apply(_testFailureValue)
          .apply(_testValue);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeValidationMap {
      private final Function<Collection<RuntimeException>, String> _testFailureMorphism = collection -> collection
        .stream()
        .map(RuntimeException::getMessage)
        .collect(Collectors.joining(". "));
      private final Function<UUID, String> _testSuccessMorphism = UUID::toString;

      @Test
      void shouldThrowExceptionForNullFailureMorphism() {
        final Function<Collection<RuntimeException>, String> testFailureMorphism = null;
        final Validation<RuntimeException, UUID> testValidation = Validation.failure(
          new RuntimeException(UUID.randomUUID().toString())
        );

        // noinspection ConstantConditions
        assertThrows(
          IllegalArgumentException.class,
          () -> Validation.<RuntimeException, UUID, String>validationMap(testFailureMorphism)
            .apply(_testSuccessMorphism)
            .apply(testValidation)
        );
      }

      @Test
      void shouldThrowExceptionForNullSuccessMorphism() {
        final Function<UUID, String> testSuccessMorphism = null;
        final Validation<RuntimeException, UUID> testValidation = Validation.success(UUID.randomUUID());

        // noinspection ConstantConditions
        assertThrows(
          IllegalArgumentException.class,
          () -> Validation.<RuntimeException, UUID, String>validationMap(_testFailureMorphism)
            .apply(testSuccessMorphism)
            .apply(testValidation)
        );
      }

      @Test
      void shouldThrowExceptionForNullValidation() {
        final Validation<RuntimeException, UUID> testValidation = null;

        // noinspection ConstantConditions
        assertThrows(
          IllegalArgumentException.class,
          () -> Validation.<RuntimeException, UUID, String>validationMap(_testFailureMorphism)
            .apply(_testSuccessMorphism)
            .apply(testValidation)
        );
      }

      @Test
      void shouldReturnMappedValueForFailure() {
        final Collection<RuntimeException> testFailureValues = Arrays.asList(
          new RuntimeException(UUID.randomUUID().toString()),
          new RuntimeException(UUID.randomUUID().toString())
        );
        final Validation<RuntimeException, UUID> testValidation = Validation.failure(testFailureValues);
        final String expectedResult = _testFailureMorphism.apply(testFailureValues);
        final String actualResult = Validation.<RuntimeException, UUID, String>validationMap(_testFailureMorphism)
          .apply(_testSuccessMorphism)
          .apply(testValidation);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnMappedValueForSuccess() {
        final UUID testValue = UUID.randomUUID();
        final Validation<RuntimeException, UUID> testValidation = Validation.success(testValue);
        final String expectedResult = testValue.toString();
        final String actualResult = Validation.<RuntimeException, UUID, String>validationMap(_testFailureMorphism)
          .apply(_testSuccessMorphism)
          .apply(testValidation);

        assertEquals(expectedResult, actualResult);
      }
    }
  }

  @Nested
  class DescribeFailure {
    private final String _testFailureValue = UUID.randomUUID().toString();
    private final Validation<String, UUID> _testValidation = Validation.failure(_testFailureValue);

    @Nested
    class DescribeEquals {
      @Test
      void shouldReturnFalseForNull() {
        final Validation<String, UUID> testOther = null;

        // noinspection ConstantConditions, SimplifiableJUnitAssertion
        assertFalse(_testValidation.equals(testOther));
      }

      @Test
      void shouldReturnFalseForDifferingType() {
        final Object testOther = new Object();

        // noinspection SimplifiableJUnitAssertion
        assertFalse(_testValidation.equals(testOther));
      }

      @Test
      void shouldReturnFalseForDifferingValues() {
        final Validation<String, UUID> testOther = Validation.failure(UUID.randomUUID().toString());

        // noinspection SimplifiableJUnitAssertion
        assertFalse(_testValidation.equals(testOther));
      }

      @Test
      void shouldReturnTrueForSameInstance() {
        // noinspection EqualsWithItself, SimplifiableJUnitAssertion
        assertTrue(_testValidation.equals(_testValidation));
      }

      @Test
      void shouldReturnTrueForSameValue() {
        final Validation<String, UUID> testOther = Validation.failure(_testFailureValue);

        // noinspection SimplifiableJUnitAssertion
        assertTrue(_testValidation.equals(testOther));
      }
    }

    @Nested
    class DescribeHashCode {
      @Test
      void shouldReturnDifferingHashCodeForDifferingValues() {
        final Validation<String, UUID> testOther = Validation.failure(UUID.randomUUID().toString());

        assertNotEquals(_testValidation.hashCode(), testOther.hashCode());
      }

      @Test
      void shouldReturnSameHashCodeForSameValues() {
        final Validation<String, UUID> testOther = Validation.failure(_testFailureValue);

        assertEquals(_testValidation.hashCode(), testOther.hashCode());
      }
    }

    @Nested
    class DescribeIsFailure {
      @Test
      void shouldReturnTrue() {
        assertTrue(_testValidation.isFailure());
      }
    }

    @Nested
    class DescribeIsSuccess {
      @Test
      void shouldReturnFalse() {
        assertFalse(_testValidation.isSuccess());
      }
    }

    @Nested
    class DescribeToString {
      @Test
      void shouldReturnFormattedString() {
        final Collection<String> testFailureValues = Arrays.asList(
          UUID.randomUUID().toString(),
          UUID.randomUUID().toString()
        );
        final Validation<String, UUID> testValidation = Validation.failure(testFailureValues);
        final String expectedResult = String.format("Failure([%s])", String.join(",", testFailureValues));
        final String actualResult = testValidation.toString();

        assertEquals(expectedResult, actualResult);
      }
    }
  }

  @Nested
  class DescribeSuccess {
    private final UUID _testSuccessValue = UUID.randomUUID();
    private final Validation<String, UUID> _testValidation = Validation.success(_testSuccessValue);

    @Nested
    class DescribeEquals {
      @Test
      void shouldReturnFalseForNull() {
        final Validation<String, UUID> testOther = null;

        // noinspection ConstantConditions, SimplifiableJUnitAssertion
        assertFalse(_testValidation.equals(testOther));
      }

      @Test
      void shouldReturnFalseForDifferingType() {
        final Object testOther = new Object();

        // noinspection SimplifiableJUnitAssertion
        assertFalse(_testValidation.equals(testOther));
      }

      @Test
      void shouldReturnFalseForDifferingValues() {
        final Validation<String, UUID> testOther = Validation.success(UUID.randomUUID());

        // noinspection SimplifiableJUnitAssertion
        assertFalse(_testValidation.equals(testOther));
      }

      @Test
      void shouldReturnTrueForSameInstance() {
        // noinspection EqualsWithItself, SimplifiableJUnitAssertion
        assertTrue(_testValidation.equals(_testValidation));
      }

      @Test
      void shouldReturnTrueForSameValue() {
        final Validation<String, UUID> testOther = Validation.success(_testSuccessValue);

        // noinspection SimplifiableJUnitAssertion
        assertTrue(_testValidation.equals(testOther));
      }
    }

    @Nested
    class DescribeHashCode {
      @Test
      void shouldReturnDifferingHashCodeForDifferingValues() {
        final Validation<String, UUID> testOther = Validation.success(UUID.randomUUID());

        assertNotEquals(_testValidation.hashCode(), testOther.hashCode());
      }

      @Test
      void shouldReturnSameHashCodeForSameValues() {
        final Validation<String, UUID> testOther = Validation.success(_testSuccessValue);

        assertEquals(_testValidation.hashCode(), testOther.hashCode());
      }
    }

    @Nested
    class DescribeIsFailure {
      @Test
      void shouldReturnFalse() {
        assertFalse(_testValidation.isFailure());
      }
    }

    @Nested
    class DescribeIsSuccess {
      @Test
      void shouldReturnTrue() {
        assertTrue(_testValidation.isSuccess());
      }
    }

    @Nested
    class DescribeToString {
      @Test
      void shouldReturnFormattedString() {
        final UUID testValue = UUID.randomUUID();
        final Validation<String, UUID> testValidation = Validation.success(testValue);
        final String expectedResult = String.format("Success(%s)", testValue);
        final String actualResult = testValidation.toString();

        assertEquals(expectedResult, actualResult);
      }
    }
  }
}
