Astronaut Daily Schedule Organizer
📌 Overview

This project is a console-based application that helps astronauts organize their daily tasks.
It demonstrates the implementation of core OOP concepts, design patterns, and exception handling while managing daily schedules efficiently.

✨ Features

✅ Add, remove, view, and edit daily tasks
✅ Validate tasks to avoid time conflicts
✅ View tasks sorted by start time
✅ Mark tasks as completed
✅ Filter tasks by priority
✅ Handle invalid operations gracefully
✅ Built-in logging and observer notifications

🛠️ Tech Stack

Language: Java 17+

Type: Console Application

IDE: IntelliJ IDEA / VS Code / Eclipse

📂 Project Structure
AstronautSchedule/
└─ src/
   └─ com/astronaut/scheduler/
      ├─ model/                # Task, TaskPriority
      ├─ factory/              # TaskFactory
      ├─ scheduler/            # ScheduleManager (Singleton)
      ├─ observer/             # Observer pattern classes
      ├─ exceptions/           # Custom exception classes
      ├─ app/                  # AppLogger
      └─ Main.java             # Entry point

▶️ How to Run
# Compile
javac -d out src/com/astronaut/scheduler/**/*.java

# Run
java -cp out com.astronaut.scheduler.Main

📊 Sample Input/Output

Input:

1
Morning Exercise
07:00
08:00
High


Output:

✅ Task added successfully. No conflicts detected.


Input:

1
Training Session
09:30
10:30
High


Output:

⚠️ Error: Task conflicts with existing task "Team Meeting".


Input:

3


Output:

📅 Scheduled Tasks:
07:00 - 08:00 : Morning Exercise [High]
09:00 - 10:00 : Team Meeting [Medium]

🧠 Design Patterns Used

Pattern	Purpose	Example
Singleton	Ensure one schedule manager	ScheduleManager
Factory	Create tasks dynamically	TaskFactory
Observer	Notify on conflicts/updates	ConsoleNotifier

📚 Future Enhancements

Add recurring tasks

Export schedule to file

Add reminder notifications

