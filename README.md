# Trash2Cash

Trash2Cash is an Android application designed to promote recycling and reward users for contributing to a cleaner environment. Users can submit trash they wish to recycle, earn XP and reward points for their contributions, and unlock special rewards based on their level. Administrators can manage submissions, reward users, and track the leaderboard to celebrate top contributors.

## Features
### User

- Submit Trash for Recycling: Users can submit details about the trash they intend to recycle.
- Earn XP and Reward Points: Upon submission approval by the admin, users receive XP and reward points.
- Rewards: Users can unlock rewards as they level up and spend reward points to claim these rewards.
- Profile and Stats: Users can view their profile, current level, rewards, and stats regarding the quantity and type of trash they have recycled.

### Admin

- Manage Submissions: Admins can view all submissions, approve or reject them, and allocate XP and reward points to users based on the submission.
- Create & Manage Rewards: Admins can create, edit, and delete rewards, setting required levels and points needed for redemption.
- Leaderboard Management: Admins can view and manage the leaderboard, highlighting top contributors.

## Requirements
-     Android Studio
-     Java: JDK 8 or higher
-     XAMPP: For PHP backend and MySQL database
-     PHP: Version 7.0 or higher

## Installation
### Backend Setup
```
Download and Install XAMPP: Install XAMPP to set up a local server with Apache and MySQL.
Configure PHP API: Copy the database directory in the XAMPP htdocs directory. Ensure that the correct permissions are given.
```

### Android App Setup
```
Clone or Download the Repository: Clone the app source code or download the repository.
Open in Android Studio: Launch Android Studio and open the project.
Configure API Endpoint: Set the base URL for API calls in the OkHttpHandler.java file and on the network_security_config.xml. Replace the "Your Ip" text with your local IP or server address to ensure Android can access it.
Build the Project: Sync dependencies and build the project in Android Studio.
```

### Admin credentials
#### The default admin credentials are:
- Email: admin
- Password: admin

## Dependencies
### Android Libraries

- Picasso: Used for image loading and caching. Helps display images submitted by users efficiently.
- OkHttp: Used for network operations, enabling the app to make HTTP requests to the backend API.

## Contributing

We welcome contributions to Trash2Cash! If you have ideas for improvements or new features, please submit a pull request or open an issue.
```
Create your own fork of the project.
Make a new branch for your feature or bug fix.
Run tests to ensure the app works as expected.
Submit a Pull Request. Provide a clear description of your changes and any relevant context.
```

## License

Trash2Cash is distributed under the MIT License. See LICENSE for more information.
