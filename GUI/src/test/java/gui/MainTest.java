package gui;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    // Test the Main class
    @org.junit.jupiter.api.Test
    void testMain() {
        // Check if the main method runs without exceptions
        assertDoesNotThrow(() -> {
            Main.main(new String[]{});
        }, "Main method should run without exceptions");
    }

}