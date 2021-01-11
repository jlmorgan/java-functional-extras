package com.github.jlmorgan.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.function.Function;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Either")
class EitherTests {
  @Nested
  class DescribeStaticMethods {
    @Nested
    class DescribeEitherMap {
      private final Function<RuntimeException, String> _testLeftMorphism = RuntimeException::getMessage;
      private final Function<UUID, String> _testRightMorphism = UUID::toString;

      @Test
      void shouldThrowExceptionForNullLeftMorphism() {
        final Function<RuntimeException, String> testLeftMorphism = null;
        final Either<RuntimeException, UUID> testEither = Either.left(null);

        // noinspection ConstantConditions
        assertThrows(
          IllegalArgumentException.class,
          () -> Either.<RuntimeException, UUID, String>eitherMap(testLeftMorphism)
            .apply(_testRightMorphism)
            .apply(testEither)
        );
      }

      @Test
      void shouldThrowExceptionForNullRightMorphism() {
        final Function<UUID, String> testRightMorphism = null;
        final Either<RuntimeException, UUID> testEither = Either.right(null);

        // noinspection ConstantConditions
        assertThrows(
          IllegalArgumentException.class,
          () -> Either.<RuntimeException, UUID, String>eitherMap(_testLeftMorphism)
            .apply(testRightMorphism)
            .apply(testEither)
        );
      }

      @Test
      void shouldThrowExceptionForNullEither() {
        final Either<RuntimeException, UUID> testEither = null;

        // noinspection ConstantConditions
        assertThrows(
          IllegalArgumentException.class,
          () -> Either.<RuntimeException, UUID, String>eitherMap(_testLeftMorphism)
            .apply(_testRightMorphism)
            .apply(testEither)
        );
      }

      @Test
      void shouldReturnMappedValueForLeft() {
        final RuntimeException testLeftValue = new RuntimeException(UUID.randomUUID().toString());
        final Either<RuntimeException, UUID> testEither = Either.left(testLeftValue);
        final String expectedResult = testLeftValue.getMessage();
        final String actualResult = Either.eitherMap(_testLeftMorphism, _testRightMorphism).apply(testEither);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnMappedValueForRight() {
        final UUID testRightValue = UUID.randomUUID();
        final Either<RuntimeException, UUID> testEither = Either.right(testRightValue);
        final String expectedResult = testRightValue.toString();
        final String actualResult = Either.eitherMap(_testLeftMorphism, _testRightMorphism, testEither);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeFromLeft {
      private final RuntimeException _testDefaultValue = new RuntimeException(UUID.randomUUID().toString());

      @Test
      void shouldReturnDefaultValueForNull() {
        final Either<RuntimeException, UUID> testEither = null;
        // noinspection UnnecessaryLocalVariable
        final RuntimeException expectedResult = _testDefaultValue;
        // noinspection ConstantConditions
        final RuntimeException actualResult = Either.<RuntimeException, UUID>fromLeft(_testDefaultValue)
          .apply(testEither);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnDefaultValueForRight() {
        final Either<RuntimeException, UUID> testEither = Either.right(null);
        // noinspection UnnecessaryLocalVariable
        final RuntimeException expectedResult = _testDefaultValue;
        final RuntimeException actualResult = Either.<RuntimeException, UUID>fromLeft(_testDefaultValue)
          .apply(testEither);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnValueForLeft() {
        final RuntimeException testLeftValue = new RuntimeException(UUID.randomUUID().toString());
        final Either<RuntimeException, UUID> testEither = Either.left(testLeftValue);
        // noinspection UnnecessaryLocalVariable
        final RuntimeException expectedResult = testLeftValue;
        final RuntimeException actualResult = Either.<RuntimeException, UUID>fromLeft(_testDefaultValue)
          .apply(testEither);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeFromRight {
      private final UUID _testDefaultValue = UUID.randomUUID();

      @Test
      void shouldReturnDefaultValueForNull() {
        final Either<RuntimeException, UUID> testEither = null;
        // noinspection UnnecessaryLocalVariable
        final UUID expectedResult = _testDefaultValue;
        // noinspection ConstantConditions
        final UUID actualResult = Either.<RuntimeException, UUID>fromRight(_testDefaultValue).apply(testEither);

        assertEquals(expectedResult, actualResult);

        Either.lefts(Arrays.asList(
          Either.left("a"),
          Either.left("b"),
          Either.right(0),
          Either.right(1)
        ));
      }

      @Test
      void shouldReturnDefaultValueForRight() {
        final Either<RuntimeException, UUID> testEither = Either.left(null);
        // noinspection UnnecessaryLocalVariable
        final UUID expectedResult = _testDefaultValue;
        final UUID actualResult = Either.<RuntimeException, UUID>fromRight(_testDefaultValue).apply(testEither);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnValueForRight() {
        final UUID testRightValue = UUID.randomUUID();
        final Either<RuntimeException, UUID> testEither = Either.right(testRightValue);
        // noinspection UnnecessaryLocalVariable
        final UUID expectedResult = testRightValue;
        final UUID actualResult = Either.<RuntimeException, UUID>fromRight(_testDefaultValue).apply(testEither);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeLefts {
      @Test
      void shouldReturnEmptyListForNullCollection() {
        final Collection<Either<String, UUID>> testList = null;
        final Collection<String> expectedResult = Collections.emptyList();
        // noinspection ConstantConditions
        final Collection<String> actualResult = Either.lefts(testList);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyListForEmptyCollection() {
        final Collection<Either<String, UUID>> testList = Collections.emptyList();
        final Collection<String> expectedResult = Collections.emptyList();
        final Collection<String> actualResult = Either.lefts(testList);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyListForBlankCollection() {
        final Collection<Either<String, UUID>> testList = Collections.singletonList(null);
        final Collection<String> expectedResult = Collections.emptyList();
        final Collection<String> actualResult = Either.lefts(testList);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnListOfLeftValuesForMixedCollection() {
        final String testLeftValue1 = UUID.randomUUID().toString();
        final String testLeftValue2 = UUID.randomUUID().toString();
        final UUID testRightValue1 = UUID.randomUUID();
        final UUID testRightValue2 = UUID.randomUUID();
        final Collection<Either<String, UUID>> testList = Arrays.asList(
          Either.left(testLeftValue1),
          Either.left(testLeftValue2),
          Either.right(testRightValue1),
          Either.right(testRightValue2)
        );
        final Collection<String> expectedResult = Arrays.asList(
          testLeftValue1,
          testLeftValue2
        );
        final Collection<String> actualResult = Either.lefts(testList);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribePartitionEithers {
      @Test
      void shouldReturnEmptyListsForNullCollection() {
        final Collection<Either<String, UUID>> testList = null;
        final Tuple<Collection<String>, Collection<UUID>> expectedResult = Tuple.of(
          Collections.emptyList(),
          Collections.emptyList()
        );
        // noinspection ConstantConditions
        final Tuple<Collection<String>, Collection<UUID>> actualResult = Either.partitionEithers(testList);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyListsForEmptyCollection() {
        final Collection<Either<String, UUID>> testList = Collections.emptyList();
        final Tuple<Collection<String>, Collection<UUID>> expectedResult = Tuple.of(
          Collections.emptyList(),
          Collections.emptyList()
        );
        final Tuple<Collection<String>, Collection<UUID>> actualResult = Either.partitionEithers(testList);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyListsForBlankCollection() {
        final Collection<Either<String, UUID>> testList = Collections.singletonList(null);
        final Tuple<Collection<String>, Collection<UUID>> expectedResult = Tuple.of(
          Collections.emptyList(),
          Collections.emptyList()
        );
        final Tuple<Collection<String>, Collection<UUID>> actualResult = Either.partitionEithers(testList);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnPartitionedListsForMixedCollection() {
        final String testLeftValue1 = UUID.randomUUID().toString();
        final String testLeftValue2 = UUID.randomUUID().toString();
        final UUID testRightValue1 = UUID.randomUUID();
        final UUID testRightValue2 = UUID.randomUUID();
        final Collection<Either<String, UUID>> testList = Arrays.asList(
          Either.left(testLeftValue1),
          Either.left(testLeftValue2),
          Either.right(testRightValue1),
          Either.right(testRightValue2)
        );
        final Tuple<Collection<String>, Collection<UUID>> expectedResult = Tuple.of(
          Arrays.asList(testLeftValue1, testLeftValue2),
          Arrays.asList(testRightValue1, testRightValue2)
        );
        final Tuple<Collection<String>, Collection<UUID>> actualResult = Either.partitionEithers(testList);

        assertEquals(expectedResult, actualResult);
      }
    }

    @Nested
    class DescribeRights {
      @Test
      void shouldReturnEmptyListForNullCollection() {
        final Collection<Either<String, UUID>> testList = null;
        final Collection<UUID> expectedResult = Collections.emptyList();
        // noinspection ConstantConditions
        final Collection<UUID> actualResult = Either.rights(testList);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyListForEmptyCollection() {
        final Collection<Either<String, UUID>> testList = Collections.emptyList();
        final Collection<UUID> expectedResult = Collections.emptyList();
        final Collection<UUID> actualResult = Either.rights(testList);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnEmptyListForBlankCollection() {
        final Collection<Either<String, UUID>> testList = Collections.singletonList(null);
        final Collection<UUID> expectedResult = Collections.emptyList();
        final Collection<UUID> actualResult = Either.rights(testList);

        assertEquals(expectedResult, actualResult);
      }

      @Test
      void shouldReturnListOfLeftValuesForMixedCollection() {
        final String testLeftValue1 = UUID.randomUUID().toString();
        final String testLeftValue2 = UUID.randomUUID().toString();
        final UUID testRightValue1 = UUID.randomUUID();
        final UUID testRightValue2 = UUID.randomUUID();
        final Collection<Either<String, UUID>> testList = Arrays.asList(
          Either.left(testLeftValue1),
          Either.left(testLeftValue2),
          Either.right(testRightValue1),
          Either.right(testRightValue2)
        );
        final Collection<UUID> expectedResult = Arrays.asList(
          testRightValue1,
          testRightValue2
        );
        final Collection<UUID> actualResult = Either.rights(testList);

        assertEquals(expectedResult, actualResult);
      }
    }
  }

  @Nested
  class DescribeLeft {
    private final String _testLeftValue = UUID.randomUUID().toString();
    private final Either<String, UUID> _testEither = Either.left(_testLeftValue);

    @Nested
    class DescribeEquals {
      @Test
      void shouldReturnFalseForNull() {
        final Either<String, UUID> testOther = null;

        // noinspection ConstantConditions, SimplifiableJUnitAssertion
        assertFalse(_testEither.equals(testOther));
      }

      @Test
      void shouldReturnFalseForDifferingType() {
        final Object testOther = new Object();

        // noinspection SimplifiableJUnitAssertion
        assertFalse(_testEither.equals(testOther));
      }

      @Test
      void shouldReturnFalseForDifferingValues() {
        final Either<String, UUID> testOther = Either.left(UUID.randomUUID().toString());

        // noinspection SimplifiableJUnitAssertion
        assertFalse(_testEither.equals(testOther));
      }

      @Test
      void shouldReturnTrueForSameInstance() {
        // noinspection EqualsWithItself, SimplifiableJUnitAssertion
        assertTrue(_testEither.equals(_testEither));
      }

      @Test
      void shouldReturnTrueForSameValue() {
        final Either<String, UUID> testOther = Either.left(_testLeftValue);

        // noinspection SimplifiableJUnitAssertion
        assertTrue(_testEither.equals(testOther));
      }
    }

    @Nested
    class DescribeHashCode {
      @Test
      void shouldReturnDifferingHashCodeForDifferingValues() {
        final Either<String, UUID> testOther = Either.left(UUID.randomUUID().toString());

        assertNotEquals(_testEither.hashCode(), testOther.hashCode());
      }

      @Test
      void shouldReturnSameHashCodeForSameValues() {
        final Either<String, UUID> testOther = Either.left(_testLeftValue);

        assertEquals(_testEither.hashCode(), testOther.hashCode());
      }
    }

    @Nested
    class DescribeIsLeft {
      @Test
      void shouldReturnTrue() {
        assertTrue(_testEither.isLeft());
      }
    }

    @Nested
    class DescribeIsRight {
      @Test
      void shouldReturnFalse() {
        assertFalse(_testEither.isRight());
      }
    }

    @Nested
    class DescribeToString {
      @Test
      void shouldReturnFormattedString() {
        final String expectedResult = String.format("Left(%s)", _testLeftValue);
        final String actualResult = _testEither.toString();

        assertEquals(expectedResult, actualResult);
      }
    }
  }

  @Nested
  class DescribeRight {
    private final UUID _testRightValue = UUID.randomUUID();
    private final Either<String, UUID> _testEither = Either.right(_testRightValue);

    @Nested
    class DescribeEquals {
      @Test
      void shouldReturnFalseForNull() {
        final Either<String, UUID> testOther = null;

        // noinspection ConstantConditions, SimplifiableJUnitAssertion
        assertFalse(_testEither.equals(testOther));
      }

      @Test
      void shouldReturnFalseForDifferingType() {
        final Object testOther = new Object();

        // noinspection SimplifiableJUnitAssertion
        assertFalse(_testEither.equals(testOther));
      }

      @Test
      void shouldReturnFalseForDifferingValues() {
        final Either<String, UUID> testOther = Either.right(UUID.randomUUID());

        // noinspection SimplifiableJUnitAssertion
        assertFalse(_testEither.equals(testOther));
      }

      @Test
      void shouldReturnTrueForSameInstance() {
        // noinspection EqualsWithItself, SimplifiableJUnitAssertion
        assertTrue(_testEither.equals(_testEither));
      }

      @Test
      void shouldReturnTrueForSameValue() {
        final Either<String, UUID> testOther = Either.right(_testRightValue);

        // noinspection SimplifiableJUnitAssertion
        assertTrue(_testEither.equals(testOther));
      }
    }

    @Nested
    class DescribeHashCode {
      @Test
      void shouldReturnDifferingHashCodeForDifferingValues() {
        final Either<String, UUID> testEither2 = Either.right(UUID.randomUUID());

        assertNotEquals(_testEither.hashCode(), testEither2.hashCode());
      }

      @Test
      void shouldReturnSameHashCodeForSameValues() {
        final Either<String, UUID> testEither2 = Either.right(_testRightValue);

        assertEquals(_testEither.hashCode(), testEither2.hashCode());
      }
    }

    @Nested
    class DescribeIsLeft {
      @Test
      void shouldReturnFalse() {
        assertFalse(_testEither.isLeft());
      }
    }

    @Nested
    class DescribeIsRight {
      @Test
      void shouldReturnTrue() {
        assertTrue(_testEither.isRight());
      }
    }

    @Nested
    class DescribeToString {
      @Test
      void shouldReturnFormattedString() {
        final String expectedResult = String.format("Right(%s)", _testRightValue.toString());
        final String actualResult = _testEither.toString();

        assertEquals(expectedResult, actualResult);
      }
    }
  }
}
