package es.atgti.datavinci;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PerformanceTest {

    private static final int WARMUP_ITERATIONS = 10000;
    private static final int TEST_ITERATIONS = 10000000;
    private static final int MAXIMUM_ALLOWED_TIME_PER_DNI_VALIDATION = 2000; // 2 microsecond

    @BeforeAll
    static void warmUpTheJIT() {
        System.out.println("Warming up the JIT (hotspot)...");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            validateDNI(generateRandomDNI());
        }
    }

    @Test
    void testPerformance() {
        System.out.println("Performance test");
        long start = System.nanoTime();
        for (int i = 0; i < TEST_ITERATIONS; i++) {
            validateDNI(generateRandomDNI());
        }
        long end = System.nanoTime();
        long total = end - start;
        System.out.println("Total time: " + total + " ns");
        long average = total / TEST_ITERATIONS;
        System.out.println("Average time: " + average + " ns");

        Assertions.assertTrue(MAXIMUM_ALLOWED_TIME_PER_DNI_VALIDATION > (end - start) / TEST_ITERATIONS);

    }

    private static String generateRandomDNI() {
        int number = (int) (Math.random()*99999999);
        char controlDigit = DNIPatterns.ORDERED_DNI_CONTROL_DIGITS.charAt( (int) (Math.random()*23) );
        return "" + number + controlDigit;
    }

    private static void validateDNI(String dni) {
        DNI miDNI = DNI.of(dni);
        Assertions.assertTrue(miDNI.getNumber().orElseThrow() > 0);
        Assertions.assertTrue(miDNI.getControlDigit().isPresent());
    }

}
