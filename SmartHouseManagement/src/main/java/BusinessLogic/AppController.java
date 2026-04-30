package BusinessLogic;

import Data.Device;
import Data.User;
import Database.DeviceRepository;
import Database.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AppController {

    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    public AppController(UserRepository userRepository, DeviceRepository deviceRepository) {
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
    }

    // --- LOGIN ENDPOINT ---
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok("Login Successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    // --- DASHBOARD ENDPOINTS ---
    @GetMapping("/devices")
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @PostMapping("/devices/{id}/toggle")
    public Device toggleDevicePower(@PathVariable String id) {
        Device device = deviceRepository.findById(id).orElseThrow();
        device.setPowerStatus(!device.isPowerStatus());
        return deviceRepository.save(device);
    }
}
