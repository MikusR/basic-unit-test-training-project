package lv.bootcamp.shelter.task4;

import lv.bootcamp.shelter.model.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Task 4: Collection and sorting tests
 *
 * Practice:
 * - AssertJ list assertions (extracting, containsExactly)
 * - Testing sort order
 * - Testing null/empty input handling
 *
 * Instructions:
 * Write tests for AnimalSorter. Use AssertJ's extracting() and containsExactly()
 * to verify the order of results.
 */
@DisplayName("AnimalSorter")
class AnimalSorterTest {

    private AnimalSorter sorter;

    private Animal buddy;
    private Animal luna;
    private Animal max;
    private Animal bella;

    @BeforeEach
    void setUp() {
        sorter = new AnimalSorter();
        buddy = new Animal("Buddy", "Dog", 3, true, LocalDate.of(2026, 1, 15));
        luna = new Animal("Luna", "Cat", 2, true, LocalDate.of(2026, 1, 10));
        max = new Animal("Max", "Dog", 5, false, LocalDate.of(2026, 1, 20));
        bella = new Animal("Bella", "Cat", 1, true, LocalDate.of(2026, 1, 5));
    }

    // --- sortByAge ---

    @Test
    @DisplayName("sortByAge: returns animals ordered youngest to oldest")
    void shouldSortByAgeAscending() {
        List<Animal> animals = List.of(buddy, luna, max, bella);
        List<Animal> sorted = sorter.sortByAge(animals);
        assertThat(sorted)
                .extracting(Animal::getName)
                .containsExactly("Bella", "Luna", "Buddy", "Max");

    }

    @Test
    @DisplayName("sortByAge: returns empty list for null input")
    void shouldReturnEmptyForNullInput() {
       assertThat(sorter.sortByAge(null)).isEmpty();
    }

    @Test
    @DisplayName("sortByAge: returns empty list for empty input")
    void shouldReturnEmptyForEmptyInput() {
       assertThat(sorter.sortByAge(List.of())).isEmpty();
    }

    @Test
    @DisplayName("sortByAge: does not modify the original list")
    void shouldNotModifyOriginalList() {
        List<Animal> animals = List.of(buddy, luna, max, bella);
        sorter.sortByAge(animals);
        assertThat(animals)
                .containsExactly(buddy, luna, max, bella);
    }

    // --- sortByName ---

    @Test
    @DisplayName("sortByName: returns animals in alphabetical order")
    void shouldSortByNameAlphabetically() {
        List<Animal> animals = List.of(buddy, luna, max, bella);
        List<Animal> sorted = sorter.sortByName(animals);
        assertThat(sorted)
                .extracting(Animal::getName)
                .containsExactly("Bella", "Buddy", "Luna", "Max");

    }

    @Test
    @DisplayName("sortByName: is case-insensitive")
    void shouldSortNamesCaseInsensitively() {
        Animal alpha = new Animal("alpha", "Dog", 3, true, LocalDate.of(2026, 1, 15));
        Animal beta = new Animal("beta", "Cat", 2, true, LocalDate.of(2026, 1, 10));
        Animal zeta = new Animal("Zeta", "Dog", 5, false, LocalDate.of(2026, 1, 20));
        Animal tau = new Animal("Tau", "Cat", 1, true, LocalDate.of(2026, 1, 5));

        List<Animal> animals = List.of(tau, zeta, beta, alpha);
        List<Animal> sorted = sorter.sortByName(animals);

        assertThat(sorted)
                .extracting(Animal::getName)
                .containsExactly("alpha", "beta", "Tau", "Zeta");
    }

    // --- sortByIntakeDate ---

    @Test
    @DisplayName("sortByIntakeDate: returns animals from earliest to latest")
    void shouldSortByIntakeDateAscending() {
        List<Animal> animals = List.of(buddy, luna, max, bella);
        List<Animal> sorted = sorter.sortByIntakeDate(animals);
        assertThat(sorted)
                .containsExactly(bella, luna, buddy, max);
    }

    // --- sortBySpeciesThenAgeDescending ---

    @Test
    @DisplayName("sortBySpeciesThenAgeDescending: groups by species then orders by age desc")
    void shouldSortBySpeciesThenAgeDesc() {
        List<Animal> animals = List.of(buddy, luna, max, bella);
        List<Animal> sorted = sorter.sortBySpeciesThenAgeDescending(animals);
        assertThat(sorted)
                .extracting(Animal::getName)
                .containsExactly("Luna", "Bella", "Max", "Buddy");
    }
}
