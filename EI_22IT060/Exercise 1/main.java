// Main.java
import adapter.HeaterAdapter;
import adapter.LegacyHeater;
import devices.Device;
import facade.SmartHomeFacade;
import observer.Observer;
import strategy.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SmartHomeFacade facade = new SmartHomeFacade();
        Scanner sc = new Scanner(System.in);

        Observer statusDisplay = state -> System.out.println("Status Display: Devices are " + state);
        facade.addDeviceObserver(statusDisplay);

        while (true) {
            System.out.println("\n=== SMART HOME MENU ===");
            System.out.println("1. Create and Turn ON Device");
            System.out.println("2. Turn OFF Device");
            System.out.println("3. Switch Energy Mode");
            System.out.println("4. Use Legacy Heater (Adapter)");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter device name (Light/Fan/Thermostat): ");
                    String type = sc.nextLine();
                    Device device = facade.createDevice(type);
                    if (device != null) facade.turnDeviceOn(device);
                    else System.out.println("Invalid device type.");
                }
                case 2 -> {
                    System.out.print("Enter device name to turn OFF: ");
                    String type = sc.nextLine();
                    Device device = facade.createDevice(type);
                    if (device != null) facade.turnDeviceOff(device);
                }
                case 3 -> {
                    System.out.print("Choose mode (eco/normal): ");
                    String mode = sc.nextLine();
                    EnergyMode energyMode = mode.equalsIgnoreCase("eco") ? new EcoMode() : new NormalMode();
                    facade.applyEnergyMode(energyMode);
                }
                case 4 -> {
                    Device heater = new HeaterAdapter(new LegacyHeater());
                    facade.turnDeviceOn(heater);
                }
                case 5 -> {
                    System.out.println("Exiting Smart Home System...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
