// devices/Thermostat.java
package devices;

public class Thermostat implements Device {
    public void turnOn() { System.out.println("Thermostat regulating temperature."); }
    public void turnOff() { System.out.println("Thermostat turned OFF."); }
}
