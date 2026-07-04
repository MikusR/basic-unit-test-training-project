package lv.bootcamp.shelter.task23;

import lv.bootcamp.shelter.model.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tasks 2 & 3: Parameterized tests and exception tests
 *
 * Practice:
 * - @ParameterizedTest with @CsvSource
 * - @ValueSource and @NullAndEmptySource
 * - assertThrows with message checks
 * - AssertJ assertThatThrownBy
 *
 * Instructions:
 * Write tests for AnimalValidator. Each TODO describes one test to write.
 */
@DisplayName("AnimalValidator")
class AnimalValidatorTest {

    private AnimalValidator validator;

    @BeforeEach
    void setUp() {
        validator = new AnimalValidator();
    }

    // ==================== Task 2: Parameterized tests ====================

    @Nested
    @DisplayName("validateName")
    class ValidateName {

        @ParameterizedTest
        @ValueSource(strings = {"Buddy", "Luna", "Mr. Whiskers", "X"})
        @DisplayName("accepts valid names")
        void shouldAcceptValidNames(String name) {
            assertDoesNotThrow(() -> validator.validateName(name));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t", "\n"})
        @DisplayName("rejects blank or null names")
        void shouldRejectBlankNames(String name) {
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> validator.validateName(name));
            assertTrue(e.getMessage().contains("must not be blank"));
        }

        @Test
        @DisplayName("rejects name longer than 100 characters")
        void shouldRejectOverlyLongName() {
            String longName = "Alfonso María Isabel Francisco Eugenio Gabriel Pedro Sebastián Pelayo Fernando Francisco de Paula Pío Miguel Rafael Juan José Joaquín Ana Zacarías Elisabeth Simeón Tereso Pedro Pablo Tadeo Santiago Simón Lucas Juan Mateo Andrés Bartolomé Ambrosio Gerónimo Agustín Bernardo Cándido Gerardo Luis-Gonzaga Filomeno Camilo Cayetano Andrés-Avelino Bruno Joaquín-Picolimini Felipe Luis-Rey-de-Francia Ricardo Esteban-Protomártir Genaro Nicolás Estanislao-de-Koska Lorenzo Vicente Crisóstomo Cristano Darío Ignacio Francisco-Javier Francisco-de-Borja Higona Clemente Esteban-de-Hungría Ladislado Enrique Ildefonso Hermenegildo Carlos-Borromeo Eduardo Francisco-Régis Vicente-Ferrer Pascual Miguel-de-los-Santos Adriano Venancio Valentín Benito José-Oriol Domingo Florencio Alfacio Benére Domingo-de-Silos Ramón Isidro Manuel Antonio Todos-los-Santos de Borbón y Borbón";
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> validator.validateName(longName));
            assertTrue(e.getMessage().contains("100 characters"));

        }
    }

    @Nested
    @DisplayName("validateAge")
    class ValidateAge {

        @ParameterizedTest
        @CsvSource({
                "0",
                "1",
                "10",
                "50"
        })
        @DisplayName("accepts valid ages")
        void shouldAcceptValidAges(int age) {
            assertDoesNotThrow(() -> validator.validateAge(age));
        }

        @ParameterizedTest
        @CsvSource({
                "-1, must not be negative",
                "-100, must not be negative",
                "51, seems unrealistic",
                "999, seems unrealistic"
        })
        @DisplayName("rejects invalid ages with correct message")
        void shouldRejectInvalidAges(int age, String expectedMessagePart) {
            IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->validator.validateAge(age));
            assertTrue(e.getMessage().contains(expectedMessagePart));
        }
    }

    // ==================== Task 3: Exception tests ====================

    @Nested
    @DisplayName("validate (full animal)")
    class ValidateFullAnimal {

        @Test
        @DisplayName("throws NullPointerException for null animal")
        void shouldThrowForNullAnimal() {
            NullPointerException e=assertThrows(NullPointerException.class,()->validator.validate(null));
            assertTrue(e.getMessage().contains("must not be null"));

        }

        @Test
        @DisplayName("throws for animal with blank name")
        void shouldThrowForBlankName() {
            Animal animal = new Animal("","species",4,false, LocalDate.of(2026, 1, 1));
            assertThrows(IllegalArgumentException.class,()->validator.validate(animal));

        }

        @Test
        @DisplayName("throws for animal with blank species")
        void shouldThrowForBlankSpecies() {
            Animal animal = new Animal("animal","",4,false, LocalDate.of(2026, 1, 1));
            assertThrows(IllegalArgumentException.class,()->validator.validate(animal));
        }

        @Test
        @DisplayName("throws for animal with negative age")
        void shouldThrowForNegativeAge() {
            Animal animal = new Animal("animal","species",-4,false, LocalDate.of(2026, 1, 1));
            assertThrows(IllegalArgumentException.class,()->validator.validate(animal));
        }

        @Test
        @DisplayName("does not throw for fully valid animal")
        void shouldPassForValidAnimal() {
            Animal animal = new Animal("Buddy","Dog",3,true, LocalDate.now());
            assertDoesNotThrow(()->validator.validate(animal));
        }
    }
}
