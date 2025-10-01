// observer/DeviceController.java
package observer;

import java.util.ArrayList;
import java.util.List;

public class DeviceController implements Subject {
    private final List<Observer> observers = new ArrayList<>();

    public void attach(Observer o) { observers.add(o); }
    public void detach(Observer o) { observers.remove(o); }

    public void notifyObservers(String state) {
        for (Observer o : observers) {
            o.update(state);
        }
    }
}
