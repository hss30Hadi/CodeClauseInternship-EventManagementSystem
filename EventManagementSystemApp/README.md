# Event Management System

## Overview
The Event Management System is a Java-based application that allows users to create, manage, and attend events. The application includes features for user registration, login, event creation, attendee management (Add, Remove, and View Attendees), and event scheduling (Add, Remove, View event schedule). It uses Java and Java Swing for the user interface and MySQL for the database.

## Features
- **User Registration and Login:** Users can create an account and log in to the system.
- **Event Creation:** Users can create events with specific details such as event code, name, date, and time.
- **Attendee Management:** Users (Event Creators) can add attendees and view the list of attendees for their events and can remove attendees from the list if needed.
- **Schedule Planning:** Users can add event to their schedule (If they are in the event's attendee list), can view and remove events from their event schedules.

## Installation

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- MySQL Server
- mysql-connector-j-9.0.0.jar (Should be added to External Libraries of project)

### Setup

1. **Database Setup:**
    - Start your MySQL server.
    - Create a new database named `eventmanagementsystemdatabase`.
    - Add the SQL commands provided in project folder to create the needed tables
   
2. **Build the Project:**
   Add mysql-connector-j-9.0.0.jar, provided in project folder, to project's external library.

4. **Compile and Run the Application:**
```bash
  MainFrame.java
   ```
5. **Resources**
   - Ensure that image files(attendees.png, error.png, event.png, icons8-register-69.png, login.png, logout.png, notes.png, schedule.png, user.png) are located in `src/assets` directory
## Usage

### Register a New User
1. Open the application.
2. Click on the "Register" button.
3. Fill in the registration form with your name, email, and password.
4. Click "Submit" to create your account.

### Log In
1. Open the application.
2. Enter your registered email and password.
3. Click "Login" to access your account.

### Create an Event
1. After logging in, navigate to the event creation section.
2. Enter the event details, including the event code, name, description, date, and time.
3. Click "Create Event" to save the event.

### Attendees Management
1. Navigate to the "Attendees Management" section.
2. If you have created an event you can do the following:
    1. Enter the event code for which you want to view the attendees.
    2. Click "View" to see the event's attendees list.
    3. To remove an attendee, click the "Remove" button next to their name.
    4. Enter the event code for which you want to add attendee to its list.
    5. Enter name and email of this attendee
    6. Click "Add" to add the attendee to event's attendee list

### Schedule Planning
1. Navigate to the "Schedule Planning" section.
2. View your upcoming events by clicking on "View Schedule" and manage them as needed.
3. You can add event to your schedule if you're added to its attendees list this is done by:
   1. Enter date of event
   2. Enter start and end time of event
   3. Enter event code
   4. Click "Plan Schedule" to add event to your schedule




---

