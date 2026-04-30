package Data.TestDevices;

import Data.Device;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("thermometer")
public class Thermometer extends Device {
    public Thermometer(String deviceId, String name, String brand) {
        super(deviceId, name, brand, "THERMOSTAT");
        this.setTemperature(22.5); // Default room temp in Celsius
    }

    public void updateReading(double newTemp) {
        this.setTemperature(newTemp);
    }
}
