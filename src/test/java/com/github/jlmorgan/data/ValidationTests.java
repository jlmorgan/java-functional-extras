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
        final Validation<String, UUID> testFirst = Validation.valid(UUID.randomUUID());

        // noinspection ConstantConditions
        assertThrows(
          IllegalArgumentException.class,
          () -> Validation.concat(testSecond).apply(testFirst)
        );
      }

      @Test
      void shouldThrowExceptionForNullFirst() {
        final Validation<String, UUID> testSecond = Validation.valid(UUID.randomUUID());
        final Validation<String, UUID> testFirst = null;

        // noinspection ConstantConditions
        assertThrows(
          IllegalArgumentException.class,
          () -> Validation.concat(testSecond).apply(testFirst)
        );
      }

      @Test
      void shouldReturnFirstForBothValids() {
        final Validation<String, UUID> testSecond = Validation.valid(UUID.randomUUID());
        final Validation<String, UUID> testFirst = Validation.valid(UUID.randomUUID());
        // noinspection UnnecessaryLocalVariable
        final Validation<String, UUID> expectedResult = testFirst;
        final Validation<String, UUID> actualResult = Validation.concat(testSecond).apply(testFirst);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnFirstForFirstInvalid() {
        final Validation<String, UUID> testSecond = Validation.valid(UUID.randomUUID());
        final Validation<String, UUID> testFirst = Validation.invalid(UUID.randomUUID().toString());
        // noinspection UnnecessaryLocalVariable
        final Validation<String, UUID> expectedResult = testFirst;
        final Validation<String, UUID> actualResult = Validation.concat(testSecond).apply(testFirst);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnSecondForSecondInvalid() {
        final Validation<String, UUID> testSecond = Validation.invalid(UUID.randomUUID().toString());
        final Validation<String, UUID> testFirst = Validation.valid(UUID.randomUUID());
        // noinspection UnnecessaryLocalVariable
        final Validation<String, UUID> expectedResult = testSecond;
        final Validation<String, UUID> actualResult = Validation.concat(testSecond).apply(testFirst);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnedConcatenatedInvalids() {
        final String testValue1 = UUID.randomUUID().toString();
        final String testValue2 = UUID.randomUUID().toString();
        final Validation<String, UUID> testSecond = Validation.invalid(testValue2);
        final Validation<String, UUID> testFirst = Validation.invalid(testValue1);
        final Validation<String, UUID> expectedResult = Validation.invalid(Arrays.asList(testValue1, testValue2));
        final Validation<String, UUID> actualResult = Validation.concat(testSecond).apply(testFirst);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeInvalids {
      @Test
      void shouldReturnEmptyCollectionForNullCollection() {
        final Collection<Validation<String, UUID>> testCollection = null;
        final Collection<String> expectedResult = emptyList();
        // noinspection ConstantConditions
        final Collection<String> actualResult = Validation.invalids(testCollection);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyCollectionForEmptyCollection() {
        final Collection<Validation<String, UUID>> testCollection = emptyList();
        final Collection<String> expectedResult = emptyList();
        final Collection<String> actualResult = Validation.invalids(testCollection);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyCollectionForBlankCollection() {
        final Collection<Validation<String, UUID>> testCollection = singletonList(null);
        final Collection<String> expectedResult = emptyList();
        final Collection<String> actualResult = Validation.invalids(testCollection);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnCollectionOfInvalidValuesForMixedCollection() {
        final String testInvalidValue1 = UUID.randomUUID().toString();
        final String testInvalidValue2 = UUID.randomUUID().toString();
        final UUID testValidValue1 = UUID.randomUUID();
        final UUID testValidValue2 = UUID.randomUUID();
        final Collection<Validation<String, UUID>> testCollection = Arrays.asList(
          Validation.invalid(testInvalidValue1),
          Validation.invalid(testInvalidValue2),
          Validation.valid(testValidValue1),
          Validation.valid(testValidValue2)
        );
        final Collection<String> expectedResult = Arrays.asList(
          testInvalidValue1,
          testInvalidValue2
        );
        final Collection<String> actualResult = Validation.invalids(testCollection);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeFromInvalid {
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
        final Collection<String> actualResult = Validation.<String, UUID>fromInvalid(_testDefaultValues)
          .apply(testValidation);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnDefaultValueForValid() {
        final Validation<String, UUID> testValidation = Validation.valid(UUID.randomUUID());
        // noinspection UnnecessaryLocalVariable
        final Collection<String> expectedResult = _testDefaultValues;
        final Collection<String> actualResult = Validation.<String, UUID>fromInvalid(_testDefaultValues)
          .apply(testValidation);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnInvalidValueFromInvalid() {
        final String testInvalidValue = UUID.randomUUID().toString();
        final Validation<String, UUID> testValidation = Validation.invalid(testInvalidValue);
        final Collection<String> expectedResult = singletonList(testInvalidValue);
        final Collection<String> actualResult = Validation.<String, UUID>fromInvalid(_testDefaultValues)
          .apply(testValidation);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeFromValid {
      private final UUID _testDefaultValue = UUID.randomUUID();

      @Test
      void shouldReturnDefaultValueForNullValidation() {
        final Validation<String, UUID> testValidation = null;
        // noinspection UnnecessaryLocalVariable
        final UUID expectedResult = _testDefaultValue;
        // noinspection ConstantConditions
        final UUID actualResult = Validation.<String, UUID>fromValid(_testDefaultValue)
          .apply(testValidation);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnDefaultValueForValid() {
        final Validation<String, UUID> testValidation = Validation.invalid(UUID.randomUUID().toString());
        // noinspection UnnecessaryLocalVariable
        final UUID expectedResult = _testDefaultValue;
        final UUID actualResult = Validation.<String, UUID>fromValid(_testDefaultValue)
          .apply(testValidation);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnValidValueForValid() {
        final UUID testValidValue = UUID.randomUUID();
        final Validation<String, UUID> testValidation = Validation.valid(testValidValue);
        // noinspection UnnecessaryLocalVariable
        final UUID expectedResult = testValidValue;
        final UUID actualResult = Validation.<String, UUID>fromValid(_testDefaultValue)
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
        final String testInvalidValue1 = UUID.randomUUID().toString();
        final String testInvalidValue2 = UUID.randomUUID().toString();
        final UUID testValidValue1 = UUID.randomUUID();
        final UUID testValidValue2 = UUID.randomUUID();
        final Collection<Validation<String, UUID>> testCollection = Arrays.asList(
          Validation.invalid(testInvalidValue1),
          Validation.invalid(testInvalidValue2),
          Validation.valid(testValidValue1),
          Validation.valid(testValidValue2)
        );
        final Tuple<Collection<String>, Collection<UUID>> expectedResult = Tuple.of(
          Arrays.asList(
            testInvalidValue1,
            testInvalidValue2
          ),
          Arrays.asList(
            testValidValue1,
            testValidValue2
          )
        );
        final Tuple<Collection<String>, Collection<UUID>> actualResult = Validation.partitionValidations(
          testCollection
        );

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeValids {
      @Test
      void shouldReturnEmptyCollectionForNullCollection() {
        final Collection<Validation<String, UUID>> testCollection = null;
        final Collection<UUID> expectedResult = emptyList();
        // noinspection ConstantConditions
        final Collection<UUID> actualResult = Validation.valids(testCollection);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyCollectionForEmptyCollection() {
        final Collection<Validation<String, UUID>> testCollection = emptyList();
        final Collection<UUID> expectedResult = emptyList();
        final Collection<UUID> actualResult = Validation.valids(testCollection);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyCollectionForBlankCollection() {
        final Collection<Validation<String, UUID>> testCollection = singletonList(null);
        final Collection<UUID> expectedResult = emptyList();
        final Collection<UUID> actualResult = Validation.valids(testCollection);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnCollectionOfInvalidValuesForMixedCollection() {
        final String testInvalidValue1 = UUID.randomUUID().toString();
        final String testInvalidValue2 = UUID.randomUUID().toString();
        final UUID testValidValue1 = UUID.randomUUID();
        final UUID testValidValue2 = UUID.randomUUID();
        final Collection<Validation<String, UUID>> testCollection = Arrays.asList(
          Validation.invalid(testInvalidValue1),
          Validation.invalid(testInvalidValue2),
          Validation.valid(testValidValue1),
          Validation.valid(testValidValue2)
        );
        final Collection<UUID> expectedResult = Arrays.asList(
          testValidValue1,
          testValidValue2
        );
        final Collection<UUID> actualResult = Validation.valids(testCollection);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeValidate {
      private final String _testInvalidValue = UUID.randomUUID().toString();
      private final UUID _testValue = UUID.randomUUID();

      @Test
      void shouldThrowExceptionForNullPredicate() {
        final Predicate<UUID> testPredicate = null;

        // noinspection ConstantConditions
        assertThrows(
          IllegalArgumentException.class,
          () -> Validation.<String, UUID>validate(testPredicate).apply(_testInvalidValue).apply(_testValue)
        );
      }

      @Test
      void shouldReturnInvalidForFalsePredicate() {
        final Predicate<UUID> testPredicate = ignored -> false;
        final Validation<String, UUID> expectedResult = Validation.invalid(_testInvalidValue);
        final Validation<String, UUID> actualResult = Validation.<String, UUID>validate(testPredicate)
          .apply(_testInvalidValue)
          .apply(_testValue);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnValidForTruePredicate() {
        final Predicate<UUID> testPredicate = ignored -> true;
        final Validation<String, UUID> expectedResult = Validation.valid(_testValue);
        final Validation<String, UUID> actualResult = Validation.<String, UUID>validate(testPredicate)
          .apply(_testInvalidValue)
          .apply(_testValue);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeValidationMap {
      private final Function<Collection<RuntimeException>, String> _testInvalidMorphism = collection -> collection
        .stream()
        .map(RuntimeException::getMessage)
        .collect(Collectors.joining(". "));
      private final Function<UUID, String> _testValidMorphism = UUID::toString;

      @Test
      void shouldThrowExceptionForNullInvalidMorphism() {
        final Function<Collection<RuntimeException>, String> testInvalidMorphism = null;
        final Validation<RuntimeException, UUID> testValidation = Validation.invalid(
          new RuntimeException(UUID.randomUUID().toString())
        );

        // noinspection ConstantConditions
        assertThrows(
          IllegalArgumentException.class,
          () -> Validation.<RuntimeException, UUID, String>validationMap(testInvalidMorphism)
            .apply(_testValidMorphism)
            .apply(testValidation)
        );
      }

      @Test
      void shouldThrowExceptionForNullValidMorphism() {
        final Function<UUID, String> testValidMorphism = null;
        final Validation<RuntimeException, UUID> testValidation = Validation.valid(UUID.randomUUID());

        // noinspection ConstantConditions
        assertThrows(
          IllegalArgumentException.class,
          () -> Validation.<RuntimeException, UUID, String>validationMap(_testInvalidMorphism)
            .apply(testValidMorphism)
            .apply(testValidation)
        );
      }

      @Test
      void shouldThrowExceptionForNullValidation() {
        final Validation<RuntimeException, UUID> testValidation = null;

        // noinspection ConstantConditions
        assertThrows(
          IllegalArgumentException.class,
          () -> Validation.<RuntimeException, UUID, String>validationMap(_testInvalidMorphism)
            .apply(_testValidMorphism)
            .apply(testValidation)
        );
      }

      @Test
      void shouldReturnMappedValueForInvalid() {
        final Collection<RuntimeException> testInvalidValues = Arrays.asList(
          new RuntimeException(UUID.randomUUID().toString()),
          new RuntimeException(UUID.randomUUID().toString())
        );
        final Validation<RuntimeException, UUID> testValidation = Validation.invalid(testInvalidValues);
        final String expectedResult = _testInvalidMorphism.apply(testInvalidValues);
        final String actualResult = Validation.<RuntimeException, UUID, String>validationMap(_testInvalidMorphism)
          .apply(_testValidMorphism)
          .apply(testValidation);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnMappedValueForValid() {
        final UUID testValue = UUID.randomUUID();
        final Validation<RuntimeException, UUID> testValidation = Validation.valid(testValue);
        final String expectedResult = testValue.toString();
        final String actualResult = Validation.<RuntimeException, UUID, String>validationMap(_testInvalidMorphism)
          .apply(_testValidMorphism)
          .apply(testValidation);

        assertEquals(expectedResult, actualResult);
      }
    }
  }

  @Nested
  class DescribeInvalid {
    private final String _testInvalidValue = UUID.randomUUID().toString();
    private final Validation<String, UUID> _testValidation = Validation.invalid(_testInvalidValue);

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
        final Validation<String, UUID> testOther = Validation.invalid(UUID.randomUUID().toString());

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
        final Validation<String, UUID> testOther = Validation.invalid(_testInvalidValue);

        // noinspection SimplifiableJUnitAssertion
        assertTrue(_testValidation.equals(testOther));
      }
    }

    @Nested
    class DescribeHashCode {
      @Test
      void shouldReturnDifferingHashCodeForDifferingValues() {
        final Validation<String, UUID> testOther = Validation.invalid(UUID.randomUUID().toString());

        assertNotEquals(_testValidation.hashCode(), testOther.hashCode());
      }

      @Test
      void shouldReturnSameHashCodeForSameValues() {
        final Validation<String, UUID> testOther = Validation.invalid(_testInvalidValue);

        assertEquals(_testValidation.hashCode(), testOther.hashCode());
      }
    }

    @Nested
    class DescribeIsInvalid {
      @Test
      void shouldReturnTrue() {
        assertTrue(_testValidation.isInvalid());
      }
    }

    @Nested
    class DescribeIsValid {
      @Test
      void shouldReturnFalse() {
        assertFalse(_testValidation.isValid());
      }
    }

    @Nested
    class DescribeToString {
      @Test
      void shouldReturnFormattedString() {
        final Collection<String> testInvalidValues = Arrays.asList(
          UUID.randomUUID().toString(),
          UUID.randomUUID().toString()
        );
        final Validation<String, UUID> testValidation = Validation.invalid(testInvalidValues);
        final String expectedResult = String.format("Invalid([%s])", String.join(",", testInvalidValues));
        final String actualResult = testValidation.toString();

        assertEquals(expectedResult, actualResult);
      }
    }
  }

  @Nested
  class DescribeValid {
    private final UUID _testValidValue = UUID.randomUUID();
    private final Validation<String, UUID> _testValidation = Validation.valid(_testValidValue);

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
        final Validation<String, UUID> testOther = Validation.valid(UUID.randomUUID());

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
        final Validation<String, UUID> testOther = Validation.valid(_testValidValue);

        // noinspection SimplifiableJUnitAssertion
        assertTrue(_testValidation.equals(testOther));
      }
    }

    @Nested
    class DescribeHashCode {
      @Test
      void shouldReturnDifferingHashCodeForDifferingValues() {
        final Validation<String, UUID> testOther = Validation.valid(UUID.randomUUID());

        assertNotEquals(_testValidation.hashCode(), testOther.hashCode());
      }

      @Test
      void shouldReturnSameHashCodeForSameValues() {
        final Validation<String, UUID> testOther = Validation.valid(_testValidValue);

        assertEquals(_testValidation.hashCode(), testOther.hashCode());
      }
    }

    @Nested
    class DescribeIsInvalid {
      @Test
      void shouldReturnFalse() {
        assertFalse(_testValidation.isInvalid());
      }
    }

    @Nested
    class DescribeIsValid {
      @Test
      void shouldReturnTrue() {
        assertTrue(_testValidation.isValid());
      }
    }

    @Nested
    class DescribeToString {
      @Test
      void shouldReturnFormattedString() {
        final UUID testValue = UUID.randomUUID();
        final Validation<String, UUID> testValidation = Validation.valid(testValue);
        final String expectedResult = String.format("Valid(%s)", testValue);
        final String actualResult = testValidation.toString();

        assertEquals(expectedResult, actualResult);
      }
    }
  }
}
