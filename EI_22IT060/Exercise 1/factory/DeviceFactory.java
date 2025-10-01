// factory/DeviceFactory.java
package factory;

import devices.*;

public class DeviceFactory {
    public static Device createDevice(String type) {
        return switch (type.toLowerCase()) {
            case "light" -> new Light();
            case "fan" -> new Fan();
            case "thermostat" -> new Thermostat();
            default -> null;
        };
    }
}
