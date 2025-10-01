// facade/SmartHomeFacade.java
package facade;

import devices.Device;
import factory.DeviceFactory;
import observer.DeviceController;
import observer.Observer;
import singleton.Logger;
import strategy.EnergyMode;

public class SmartHomeFacade {
    private final Logger logger = Logger.getInstance();
    private final DeviceController controller = new DeviceController();

    public void addDeviceObserver(Observer device) {
        controller.attach(device);
    }

    public Device createDevice(String type) {
        Device device = DeviceFactory.createDevice(type);
        logger.log(type + " created.");
        return device;
    }

    public void turnDeviceOn(Device device) {
        device.turnOn();
        controller.notifyObservers("ON");
        logger.log(device.getClass().getSimpleName() + " turned ON.");
    }

    public void turnDeviceOff(Device device) {
        device.turnOff();
        controller.notifyObservers("OFF");
        logger.log(device.getClass().getSimpleName() + " turned OFF.");
    }

    public void applyEnergyMode(EnergyMode mode) {
        mode.applyMode();
        logger.log("Energy mode applied.");
    }
}
