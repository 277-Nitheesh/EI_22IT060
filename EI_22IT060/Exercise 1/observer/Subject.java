// observer/Subject.java
package observer;

public interface Subject {
    void attach(Observer o);
    void detach(Observer o);
    void notifyObservers(String state);
}
