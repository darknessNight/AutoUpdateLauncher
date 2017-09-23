package pl.darknessNight.AutoUpdateLauncher;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.logging.Logger;


public class MainClassTest {
    @Test
    public void main() {
        pl.darknessNight.AutoUpdateLauncher.MainClass.main(new String[1]);

        assertTrue(true);
    }

}