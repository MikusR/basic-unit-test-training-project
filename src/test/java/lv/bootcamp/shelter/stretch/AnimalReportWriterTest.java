package lv.bootcamp.shelter.stretch;

import lv.bootcamp.shelter.model.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Stretch goal: Testing file output
 * <p>
 * Practice:
 * - Writing to temp files and reading them back
 * - String content assertions
 * - Cleanup with Files.deleteIfExists
 * <p>
 * Instructions:
 * These tests verify that AnimalReportWriter produces correct output.
 * This task is optional — attempt it after completing tasks 1–6.
 */
@DisplayName("AnimalReportWriter (stretch)")
class AnimalReportWriterTest {

    private final AnimalReportWriter writer = new AnimalReportWriter();

    @Test
    @DisplayName("writes report file that contains total count")
    void shouldWriteTotalCount(@TempDir Path tempDir) throws IOException {
        Animal buddy = new Animal("Buddy", "Dog", 3, true, LocalDate.of(2026, 1, 15));
        Animal luna = new Animal("Luna", "Cat", 2, true, LocalDate.of(2026, 1, 10));
        Animal max = new Animal("Max", "Dog", 5, false, LocalDate.of(2026, 1, 20));
        List<Animal> animals = List.of(buddy, luna, max);

        Path output = tempDir.resolve("report-test.txt");
        writer.writeReport(animals, output);

        String content = Files.readString(output, StandardCharsets.UTF_8);
        assertThat(content).contains("Total animals: 3");
    }

    @Test
    @DisplayName("writes per-species breakdown in alphabetical order")
    void shouldWriteSpeciesBreakdown(@TempDir Path tempDir) throws IOException {
        Animal buddy = new Animal("Buddy", "Dog", 3, true, LocalDate.of(2026, 1, 15));
        Animal luna = new Animal("Luna", "Cat", 2, true, LocalDate.of(2026, 1, 10));
        Animal max = new Animal("Max", "Dog", 5, false, LocalDate.of(2026, 1, 20));
        List<Animal> animals = List.of(buddy, luna, max);

        Path output = tempDir.resolve("report-test.txt");
        writer.writeReport(animals, output);
        List<String> content = Files.readAllLines(output, StandardCharsets.UTF_8);
        assertThat(content).containsSequence("Cat: 1 total, 1 vaccinated", "Dog: 2 total, 1 vaccinated");
    }

    @Test
    @DisplayName("writes oldest animal per species")
    void shouldWriteOldestPerSpecies(@TempDir Path tempDir) throws IOException {
        Animal buddy = new Animal("Buddy", "Dog", 3, true, LocalDate.of(2026, 1, 15));
        Animal luna = new Animal("Luna", "Cat", 2, true, LocalDate.of(2026, 1, 10));
        Animal max = new Animal("Max", "Dog", 5, false, LocalDate.of(2026, 1, 20));
        List<Animal> animals = List.of(buddy, luna, max);

        Path output = tempDir.resolve("report-test.txt");
        writer.writeReport(animals, output);
        List<String> content = Files.readAllLines(output, StandardCharsets.UTF_8);
        assertThat(content).containsSequence("Dog: Max (age 5)");
    }
}
