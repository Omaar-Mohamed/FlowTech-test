# Rick and Morty Characters Display Application

## Overview

This is a Rick and Morty character display application that showcases characters from the popular TV show using a clean architecture with the MVVM (Model-View-ViewModel) pattern. The application utilizes Firebase for authentication to manage user access and sessions.

## Features

- **Character Display**: Lists characters from the Rick and Morty universe.
- **Firebase Authentication**: Allows users to sign in, sign out, and register using Firebase.
- **Pagination**: Supports pagination to load characters page by page.
- **MVVM Architecture**: Separates concerns using Model-View-ViewModel architecture for better maintainability.

## Technologies

- **Android SDK**: For building the Android application.
- **Firebase Authentication**: For handling user authentication.
- **Kotlin**: For writing Android application code.
- **ViewModel**: Part of Android Architecture Components for managing UI-related data.
- **StateFlow**: For reactive data handling.
- **RecyclerView**: For displaying a scrollable list of characters.
- **Clean Architecture**: Ensures a separation of concerns and modularity.

## Architecture

### Clean Architecture

1. **Presentation Layer**
   - **ViewModels**: Manage UI-related data and business logic, interact with UseCases.
   - **Fragments/Activities**: Display data to the user and handle user interactions.

2. **Domain Layer**
   - **UseCases**: Contains business logic and interacts with the repository layer.

3. **Data Layer**
   - **Repositories**: Handle data operations, interact with remote and local data sources.
   - **Remote Data Sources**: Fetch data from remote servers (e.g., Rick and Morty API).

## Setup

### Prerequisites

- Android Studio
- An active Firebase project

### Firebase Setup

1. Go to the [Firebase Console](https://console.firebase.google.com/).
2. Create a new Firebase project or use an existing one.
3. Enable Firebase Authentication by following the [Firebase Authentication Setup Guide](https://firebase.google.com/docs/auth/android/start).
4. Download the `google-services.json` file and place it in the `app/` directory of your project.
5. Add Firebase dependencies to your `build.gradle` files.
