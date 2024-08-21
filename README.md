Rick and Morty Characters Display Application
Overview
This is a Rick and Morty character display application that showcases characters from the popular TV show using a clean architecture with MVVM (Model-View-ViewModel) pattern. The application utilizes Firebase for authentication to manage user access and sessions.

Features
Character Display: Lists characters from the Rick and Morty universe.
Firebase Authentication: Allows users to sign in, sign out, and register using Firebase.
Pagination: Supports pagination to load characters page by page.
MVVM Architecture: Separates concerns using Model-View-ViewModel architecture for better maintainability.
Technologies
Android SDK: For building the Android application.
Firebase Authentication: For handling user authentication.
Kotlin: For writing Android application code.
ViewModel: Part of Android Architecture Components for managing UI-related data.
StateFlow: For reactive data handling.
RecyclerView: For displaying a scrollable list of characters.
Clean Architecture: Ensures a separation of concerns and modularity.
Architecture
Clean Architecture
Presentation Layer

ViewModels: Manage UI-related data and business logic, interact with UseCases.
Fragments/Activities: Display data to the user and handle user interactions.
Domain Layer

UseCases: Contains business logic and interacts with the repository layer.
Data Layer

Repositories: Handle data operations, interact with remote and local data sources.
Remote Data Sources: Fetch data from remote servers (e.g., Rick and Morty API).
Setup
Prerequisites
Android Studio
An active Firebase project
Firebase Setup
Go to the Firebase Console.
Create a new Firebase project or use an existing one.
Enable Firebase Authentication by following the Firebase Authentication Setup Guide.
Download the google-services.json file and place it in the app/ directory of your project.
Add Firebase dependencies to your build.gradle files.
