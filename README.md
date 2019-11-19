# ScheduleOnWheels Android App

A shifts scheduling app that uses the clean architecture approach and is written in Kotlin.

## About This Project
The focus of this project is to create an app that generates schedules for people to take turns supporting a company's buisness.
<br/>The app chooses two random people to both complete a half day of support.
<br/>We assume a company has 10 people and that the generated schedule should span a period of two weeks starting first working day. 

<br/>The generated schedule takes in account that each person should complete 2 shifts in the given two weeks period and a person cannot have more than one shift on any consecutive days.

The generated schedule comprises of a list of random weekdays.
<br/>A Weekday comprises of:
<br/>* A date from Monday to Friday
<br/>* People, two and each assigned only one shift at most in a day.
<br/>* Shifts, a day and a night shift. 


## App Features
<br/>-Shows a list of fetched people.
<br/>-Shows a list of generated schedule

## Architecture
The architecture is built around Android Architecture Components( LiveData,ViewModel,Repository ) using MVVM pattern by the help of Retrofit for consuming rest api data.
<br/>Project is seperated in terms of modules, app and data
<br/>Classes are devided into the following packages: activities, adapters, data, helpers, models.


## RESTful API
The app mock API was created using Apiary. The Apiary is powerful API design stack that is built for developers.
<br/>You can find more information on their website.
https://app.apiary.io/

## Libraries and Techniques Used
<br/>-Android Architecture Components
<br/>-Live Data
<br/>-View Model
<br/>-Paging
<br/>-Retrofit
<br/>-News Api
<br/>$ ls *.shGlide
<br/>-Recyclerview

Foundation - Components for core system capabilities, Kotlin extensions and support for multidex and automated testing.
AppCompat - Degrade gracefully on older versions of Android.
Android KTX - Write more concise, idiomatic Kotlin code.
Test - An Android testing framework for unit and runtime UI tests.
Architecture - A collection of libraries that help you design robust, testable, and maintainable apps. Start with classes for managing your UI component lifecycle and handling data persistence.
Data Binding - Declaratively bind observable data to UI elements.
Lifecycles - Create a UI that automatically responds to lifecycle events.
LiveData - Build data objects that notify views when the underlying database changes.
Navigation - Handle everything needed for in-app navigation.
Room - Access your app's SQLite database with in-app objects and compile-time checks.
ViewModel - Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks for optimal execution.
WorkManager - Manage your Android background jobs.
UI - Details on why and how to use UI Components in your apps - together or separate
Animations & Transitions - Move widgets and transition between screens.
Fragment - A basic unit of composable UI.
Layout - Lay out widgets using different algorithms.
Third party
Glide for image loading
Kotlin Coroutines for managing background threads with simplified code and reducing needs for callbacks

We used Fragments to later on simplfy the Activity app using Navigation component.

We used Koin for dependency injection and we heavily relied on dagger-android to abstract away boiler-plate code.

Activities
The following activities are used:

TrendingActivity (Home screen of the app, contains an overview today's trending movies)
SearchActivity (Activity with an editbox implementend. Fires a new intent when submitting a search request)
SearchResultActivity (Contains an overview of movies that are the result of a JSON search request by movie name)
MovieDetailActivity (Shows detailed information about a selected movie)
FavoritesActivity (Favorite movies of the user)


