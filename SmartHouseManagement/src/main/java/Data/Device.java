package Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "devices")
public class Device {

    @Id
    private String id;
    private String deviceId;
    private String name;
    private String brand;
    private String deviceType;

    private boolean isOnline;

    // Real-Time State Attributes
    private boolean powerStatus;
    private double temperature;
    private boolean isLocked;
    private String operatingMode;

    public Device() {}

    public Device(String deviceId, String name, String brand, String deviceType) {
        this.deviceId = deviceId;
        this.name = name;
        this.brand = brand;
        this.deviceType = deviceType;
        this.isOnline = true; // Default assumption when added
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
    public boolean isOnline() { return isOnline; }
    public void setOnline(boolean online) { isOnline = online; }
    public boolean isPowerStatus() { return powerStatus; }
    public void setPowerStatus(boolean powerStatus) { this.powerStatus = powerStatus; }
    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
    public boolean isLocked() { return isLocked; }
    public void setLocked(boolean locked) { this.isLocked = locked; }
    public String getOperatingMode() { return operatingMode; }
    public void setOperatingMode(String operatingMode) { this.operatingMode = operatingMode; }
}
