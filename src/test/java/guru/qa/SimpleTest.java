package guru.qa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Учебный тест")

public class SimpleTest {


    @DisplayName("Что 3 больше чем 2")
    @Test
    void simpleTest() {
        Assertions.assertTrue(3>2);

    }

    @Test
    void simpleTest1() {
        Assertions.assertTrue(3>2);

    }
}
