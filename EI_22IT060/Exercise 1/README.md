🏠 Smart Home Automation System – Design Patterns Demo
📌 Overview

This console-based project demonstrates the use of Behavioral, Creational, and Structural design patterns in a single cohesive application.
It simulates a Smart Home Automation System where users can create and manage devices, switch energy modes, connect legacy devices, and control everything through a unified interface.

✨ Features

📡 Observer Pattern – Notify devices when a state changes (e.g., power ON/OFF).

🔄 Strategy Pattern – Switch between different energy-saving modes.

🏭 Factory Pattern – Create various smart devices (Light, Fan, Thermostat).

🧠 Singleton Pattern – Global configuration and logging instance.

🔌 Adapter Pattern – Connect and control legacy devices.

🎮 Facade Pattern – Provide a single unified interface for the user.

🛠️ Tech Stack

Language: Java 17+

Type: Console Application

IDE: IntelliJ IDEA / VS Code / Eclipse

Build Tool: Manual javac or IDE compiler

📂 Project Structure
SmartHomeAutomation/
└─ src/
   └─ com/smarthome/
      ├─ devices/              # Light, Fan, Thermostat classes
      ├─ factory/              # DeviceFactory
      ├─ strategy/             # Energy-saving strategies
      ├─ observer/             # Observer pattern classes
      ├─ adapter/              # Adapter for legacy devices
      ├─ facade/               # SmartHomeFacade class
      ├─ singleton/            # Logger / Config Manager
      └─ Main.java             # Entry point

▶️ How to Run
# Compile
javac Main.java

# Run
java Main

📊 Sample Input/Output

Input:

1. Add Device
2. Turn On Device
3. Switch Energy Mode
4. Connect Legacy Device
5. Exit
Enter choice: 1
Enter device type (Light/Fan/Thermostat): Light
Device created successfully!


Output:

[LOG] Light device created.
[NOTIFY] Light is now ON.
[STRATEGY] Switched to Eco Mode.
[ADAPTER] Legacy AC connected successfully.

🧠 Design Patterns Used
Pattern	Purpose	Example Class
Singleton	Global logging/config	AppLogger
Factory	Create devices	DeviceFactory
Observer	Notify devices of state changes	DeviceObserver
Strategy	Change energy modes dynamically	EnergySavingStrategy
Adapter	Connect legacy devices	LegacyDeviceAdapter
Facade	Unified control interface	SmartHomeFacade
📚 Future Enhancements

Add scheduling features for devices.

Integrate voice commands.

Real-time device monitoring and status updates.
