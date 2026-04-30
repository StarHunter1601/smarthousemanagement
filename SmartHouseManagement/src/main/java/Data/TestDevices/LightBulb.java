package Data.TestDevices;

import Data.Device;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("lightBulb")
public class LightBulb extends Device {
    public LightBulb(String deviceId, String name, String brand) {
        super(deviceId, name, brand, "LIGHT");
        this.setPowerStatus(false);
    }

    public void toggleLights() {
        this.setPowerStatus(!this.isPowerStatus());
    }
}

