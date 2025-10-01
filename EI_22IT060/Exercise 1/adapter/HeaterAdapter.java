// adapter/HeaterAdapter.java
package adapter;

import devices.Device;

public class HeaterAdapter implements Device {
    private final LegacyHeater heater;

    public HeaterAdapter(LegacyHeater heater) {
        this.heater = heater;
    }

    @Override
    public void turnOn() {
        heater.startHeating();
    }

    @Override
    public void turnOff() {
        System.out.println("Legacy heater turned OFF.");
    }
}
