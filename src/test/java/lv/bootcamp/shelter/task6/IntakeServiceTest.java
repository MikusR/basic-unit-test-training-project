package lv.bootcamp.shelter.task6;

import lv.bootcamp.shelter.model.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Task 7: Mocking a dependency
 *
 * Practice:
 * - @Mock and @InjectMocks
 * - when(...).thenReturn(...)
 * - verify(...) and verify(..., never())
 * - Testing with mocked dependencies
 *
 * Instructions:
 * Write tests for IntakeService. The AnimalRepository is mocked — you control what it returns.
 * Focus on verifying that IntakeService calls the repository correctly.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("IntakeService")
class IntakeServiceTest {

    @Mock
    private AnimalRepository repository;

    @InjectMocks
    private IntakeService service;

    private final Animal buddy = new Animal("Buddy", "Dog", 3, true, LocalDate.of(2026, 1, 15));

    // ==================== intake() ====================

    @Nested
    @DisplayName("intake")
    class Intake {

        @Test
        @DisplayName("saves valid animal and returns it")
        void shouldSaveValidAnimal() {
            when(repository.save(buddy)).thenReturn(buddy);

            Animal animal = service.intake(buddy);
            assertThat(animal.getName()).isEqualTo("Buddy");
            verify(repository,times(1)).save(buddy);
        }

        @Test
        @DisplayName("throws for null animal without calling repository")
        void shouldThrowForNullAnimal() {
            assertThatThrownBy(()->service.intake(null)).isInstanceOf(NullPointerException.class);
            verify(repository,times(0)).save(any());
        }

        @Test
        @DisplayName("throws for invalid animal without calling repository")
        void shouldThrowForInvalidAnimal() {
            Animal invalid = new Animal("", "Dog", 3, true, LocalDate.now());

            assertThatThrownBy(()->service.intake(invalid)).isInstanceOf(IllegalArgumentException.class);
            verify(repository,times(0)).save(any());
        }
    }

    // ==================== findByName() ====================

    @Nested
    @DisplayName("findByName")
    class FindByName {

        @Test
        @DisplayName("returns animal when repository finds it")
        void shouldReturnAnimalWhenFound() {
            when(repository.findByName("Buddy")).thenReturn(Optional.of(buddy));

            Animal animal = service.findByName("Buddy");
            assertThat(animal).isNotNull();
            assertThat(animal.getName()).isEqualTo("Buddy");

        }

        @Test
        @DisplayName("returns null when repository does not find it")
        void shouldReturnNullWhenNotFound() {
            when(repository.findByName("Ghost")).thenReturn( Optional.empty());

            Animal animal = service.findByName("Ghost");
            assertThat(animal).isNull();
        }

        @Test
        @DisplayName("throws for blank name without calling repository")
        void shouldThrowForBlankName() {
            assertThatThrownBy(() -> service.findByName("")).isInstanceOf(IllegalArgumentException.class);
            verify(repository,times(0)).findByName(any());
        }
    }

    // ==================== findBySpecies() ====================

    @Nested
    @DisplayName("findBySpecies")
    class FindBySpecies {

        @Test
        @DisplayName("returns list from repository for valid species")
        void shouldReturnAnimalsForValidSpecies() {
            when(repository.findBySpecies("Dog")).thenReturn(List.of(buddy));

            List<Animal> results = service.findBySpecies("Dog");
            assertThat(results.size()).isEqualTo(1);
            assertThat(results).contains(buddy);
        }

        @Test
        @DisplayName("returns empty list for blank species without calling repository")
        void shouldReturnEmptyForBlankSpecies() {
            List<Animal> results = service.findBySpecies("");
            assertThat(results).isEmpty();
            verify(repository,times(0)).findBySpecies(any());
        }

        @Test
        @DisplayName("returns empty list for null species without calling repository")
        void shouldReturnEmptyForNullSpecies() {
            List<Animal> results = service.findBySpecies(null);
            assertThat(results).isEmpty();
            verify(repository,times(0)).findBySpecies(any());
        }
    }

    // ==================== count() ====================

    @Nested
    @DisplayName("count")
    class Count {

        @Test
        @DisplayName("returns the size of all animals from repository")
        void shouldReturnCountFromRepository() {
            Animal baddy = new Animal("Baddy", "Cat", 3, true, LocalDate.of(2026, 1, 15));
            Animal biddy = new Animal("Biddy", "Snek", 3, true, LocalDate.of(2026, 1, 15));
            when(repository.findAll()).thenReturn(List.of(buddy, baddy, biddy));

            assertThat(service.count()) .isEqualTo(3);
        }

        @Test
        @DisplayName("returns 0 when repository is empty")
        void shouldReturnZeroWhenEmpty() {
            when(repository.findAll()).thenReturn(List.of());

            assertThat(service.count()) .isEqualTo(0);
        }
    }
}
