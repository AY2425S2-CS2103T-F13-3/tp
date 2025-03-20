package hirehive.address;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hirehive.address.commons.core.Version;

public class MainAppTest {
    @Test
    public void versionNumber_isCorrect() {
        Version expectedVersion = new Version(1, 3, 0, true);
        assertEquals(expectedVersion, MainApp.VERSION, "App version should be 1.3.0");
    }
}