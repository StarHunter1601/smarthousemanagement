package Data.TestDevices;

import Data.Device;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("oven")
public class Oven extends Device {
    private int cookingTimeMinutes; // Specific to Oven

    public Oven(String deviceId, String name, String brand) {
        super(deviceId, name, brand, "OVEN");
        this.setPowerStatus(false);
        this.setTemperature(0);
        this.cookingTimeMinutes = 0;
    }

    public int getCookingTimeMinutes() { return cookingTimeMinutes; }
    public void setCookingTimeMinutes(int cookingTimeMinutes) { this.cookingTimeMinutes = cookingTimeMinutes; }

    public void startBaking(double targetTemp, int minutes) {
        this.setPowerStatus(true);
        this.setTemperature(targetTemp);
        this.cookingTimeMinutes = minutes;
        this.setOperatingMode("Bake");
    }
}
