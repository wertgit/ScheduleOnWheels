# ScheduleOnWheels Android App

A work shifts' scheduling app that uses the clean architecture approach and is written in Kotlin.

## About This Project
The focus of this project is to create an app that generates schedules for people to take turns supporting a company's business.
<br/>The app chooses two random people to both complete a half-day of support, either Day or Night.
<br/>We assume a company has 10 people and that the generated schedule should span a period of two weeks starting the first working day. 

<br/>The generated schedule takes into account that each person should complete 2 shifts in the given two weeks period and a person cannot have more than one shift on any consecutive days.
The generated schedule comprises of a list of random weekdays.

<br/>A Weekday comprises of:
* A date from Monday to Friday
* People, two and each assigned only one shift at most in a day.
* Shifts, a day and a night shift. 


## App Features
* Shows a list of fetched people.
* Shows a list of generated schedules.

## Architecture
The architecture is built around Android Architecture Components( LiveData, ViewModel, Repository ) using MVVM pattern with the help of Retrofit for consuming rest api data.
<br/>Layer separation with modules (app and data) to make it easier to maintain, read, extend and test.


## RESTful API
The app mock API was created using Apiary. The Apiary is a powerful API design stack that is built for developers.
<br/>You can find more information on their website.
https://app.apiary.io/

## Libraries and Techniques Used
* Android JetPack using Android Architecture Components (Live Data, View Model).
* Retrofit for networking. 
* Rxjava for data access.
* Timber for debugging.
* Shared ViewModel for communication between Activity and fragments.
* Data Binding for binding UI elements to accelerate development and eliminate boilerplate code.
* Recyclerview for displaying lists.
* Koin for performing Dependency Injection using Singleton and Factory Patterns.
* Base Classes declared for Fragments and ViewModels and ViewHolders because we don't want to reinvent the wheel by writing the same boilerplate code again and again. :)



