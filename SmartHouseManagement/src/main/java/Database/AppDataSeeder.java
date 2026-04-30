package Database;

import Data.TestDevices.*;
import Data.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppDataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    public AppDataSeeder(UserRepository userRepository, DeviceRepository deviceRepository) {
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public void run(String... args) {
        // Clear old data for the simulation
        userRepository.deleteAll();
        deviceRepository.deleteAll();

        // 1. Create the single user
        User admin = new User("admin", "admin@smarthouse.com", "password123", "ADMIN");
        userRepository.save(admin);

        // 2. Create the predetermined devices
        LightBulb livingRoomLight = new LightBulb("L-01", "Main Chandelier", "Philips");
        Thermometer hallThermostat = new Thermometer("T-01", "Hallway Climate", "Nest");
        Oven kitchenOven = new Oven("O-01", "Smart Oven", "Samsung");

        // 3. Save them to MongoDB
        deviceRepository.save(livingRoomLight);
        deviceRepository.save(hallThermostat);
        deviceRepository.save(kitchenOven);

        System.out.println("Base model data seeded successfully!");
    }
}
