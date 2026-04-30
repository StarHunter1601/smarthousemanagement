package Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "homes")
public class HomeProfile {

    @Id
    private String id;
    private String homeName;
    private String address;
    @DBRef
    private List<User> authorizedUsers = new ArrayList<>();
    @DBRef
    private List<Device> connectedDevices = new ArrayList<>();

    public HomeProfile() {}

    public HomeProfile(String homeName, String address) {
        this.homeName = homeName;
        this.address = address;
    }
    public void addUser(User user) {
        if (!this.authorizedUsers.contains(user)) {
            this.authorizedUsers.add(user);
        }
    }

    public void removeUser(User user) {
        this.authorizedUsers.remove(user);
    }

    public void addDevice(Device device) {
        if (!this.connectedDevices.contains(device)) {
            this.connectedDevices.add(device);
        }
    }

    public void removeDevice(Device device) {
        this.connectedDevices.remove(device);
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getHomeName() { return homeName; }
    public void setHomeName(String homeName) { this.homeName = homeName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public List<User> getAuthorizedUsers() { return authorizedUsers; }
    public void setAuthorizedUsers(List<User> authorizedUsers) { this.authorizedUsers = authorizedUsers; }
    public List<Device> getConnectedDevices() { return connectedDevices; }
    public void setConnectedDevices(List<Device> connectedDevices) { this.connectedDevices = connectedDevices; }
}
