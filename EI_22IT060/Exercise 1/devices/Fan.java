// devices/Fan.java
package devices;

public class Fan implements Device {
    public void turnOn() { System.out.println("Fan started spinning."); }
    public void turnOff() { System.out.println("Fan stopped."); }
}
