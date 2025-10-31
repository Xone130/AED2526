package tests; 

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    private static final String TEST_DIR = "src/tests/"; 

    /**
     * Collect all (input, output) file pairs automatically
     */
    static Stream<Arguments> provideTestFiles() throws IOException {
        return Files.list(Paths.get(TEST_DIR))
                .filter(p -> p.getFileName().toString().startsWith("input"))
                .sorted()
                .map(input -> {
                    String index = input.getFileName().toString().replaceAll("[^0-9]", "");
                    Path output = Paths.get(TEST_DIR, "output" + index + ".txt");
                    return Arguments.of(input, output);
                });
    }

    @ParameterizedTest(name = "Test case #{index} ({0})")
    @MethodSource("provideTestFiles")
    void testMainProgram(Path inputFile, Path outputFile) throws Exception {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        try (
            InputStream testIn = Files.newInputStream(inputFile);
            ByteArrayOutputStream testOut = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(testOut, true, StandardCharsets.UTF_8)
        ) {
            System.setIn(testIn);
            System.setOut(ps);

            Main.main(new String[]{});

            // Read outputs
            String expected = Files.readString(outputFile, StandardCharsets.UTF_8).trim();
            String actual = testOut.toString(StandardCharsets.UTF_8).trim();

            List<String> expectedLines = expected.lines().map(String::trim).toList();
            List<String> actualLines = actual.lines().map(String::trim).toList();

            assertEquals(expectedLines, actualLines,
                    () -> "X Output mismatch for " + inputFile.getFileName() +
                          "\nExpected:\n" + expected +
                          "\nActual:\n" + actual);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}
