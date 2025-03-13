# <img src="https://github.com/AdamDawi/Uni-schedule/assets/49430055/55569c32-09d1-410e-97f7-3ced5be4a178" width="60" height="60" align="center" /> Uni Schedule

UniSchedule is a comprehensive app designed specifically for students of the Faculty of Electrical Engineering and Computer Science at the Lublin University of Technology. It seamlessly integrates with the university's scheduling system to provide a personalized and interactive experience for managing your class schedule.

## ⭐️Features
### Main Screen 
- **Schedule Integration:** Connect with the university's schedule system by providing your unique schedule link.
  
- **Horizontal Pager View:** Display your class schedule in a [horizontal pager](#animations), making it easy to swipe through your weekly classes.

- **Custom Layout:** Classes are visually arranged in a [custom layout](#animations) according to their time slots and duration, providing a clear and organized view of your schedule.

- **Live Time Indicator:** A red line dynamically shows the current time on your schedule.

- **Class Details Popup:** Tap on any class to view detailed information in a popup window.

### Schedule Management
- **Refresh Schedule:** Reload your schedule from the server to ensure you have the latest updates.

- **Data Caching:** Your schedule is cached locally using Room Database, allowing offline access to your schedule.

- **Link Management:** Save and update your schedule link using DataStore, ensuring your data is persistent across app sessions.

### Widget
- **Glance Widget:** A widget that shows the current or next class, updating every 15 minutes to keep you informed.

## ⚙️Technologies
### 📱App:
- **Jetpack Compose:** Leverages Jetpack Compose's declarative UI toolkit to create a modern and responsive interface.🎨

- **Clean MVVM Architecture:** Implements a clean architecture pattern with repositories and use cases, ensuring maintainability and scalability.🔧

- **Room database:** Uses SQLite ORM (Object-Relational Mapping) library to cache your schedule for offline access.

- **Retrofit2:** Fetches schedule data from the server.

- **Dagger Hilt:** Handles dependency injection throughout the app, including network clients, repositories, and use cases.

- **Glance:** Powers the app widget for showing current or next classes.

- **Material Designs:** Used to create the app's intuitive and visually appealing interface (Scaffold, Horizontal Pager, Tabs, Alert Dialog)

### 🖥Server:
- **Node.js:** The backend server that processes requests and serves schedule data in JSON format.

## Installation of mobile app

1. Clone the repository:
```bash
git clone https://github.com/AdamDawi/Uni-schedule
```
2. Open the project in Android Studio.
3. Be sure the versions in gradle are same as on github

## Here are some overview pictures:
![22](https://github.com/AdamDawi/Uni-schedule/assets/49430055/568ac3aa-a58c-4d20-8691-09ba49d13898)
![33](https://github.com/AdamDawi/Uni-schedule/assets/49430055/858a1f2d-ac74-41ba-ae13-5a56f2c5865b)
![44](https://github.com/AdamDawi/Uni-schedule/assets/49430055/65a83933-6404-4535-b3d6-70fccf5b6c54)
![11](https://github.com/AdamDawi/Uni-schedule/assets/49430055/5ffad230-724b-4f98-bacd-0b9c485d9e4b)

## Animations:
![schedule_gif](https://github.com/AdamDawi/Uni-schedule/assets/49430055/d1b10eb5-ce62-427f-a21f-c84f53c2ea1e)

## Widget:
![Image](https://github.com/user-attachments/assets/493b6979-1865-42bc-88d5-62565c466087)

## Requirements
Minimum version: Android 8 (API level 26) or later📱

Target version: Android 14 (API level 34) or later📱

## Authors

Adam Dawidziuk🧑‍💻

Sebastian Dragan🧑‍💻
