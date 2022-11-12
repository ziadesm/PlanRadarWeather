# PlanRadarWeather

## Issues (But what next)
- bg_screen in mipmap folder had low resolution and not showing well.

## Follow TODOs
- For testing only you can follow these TODOs and do what it says :D

## Clean architecture with 3 layers
- Data (for database and API code)
- Domain (for business logic and models)
- Presentation (for UI logic, with MVVM)

## Technologies Used

- MVVM
- Jetpack Libraries
- Navigation Components
- Kotlin Coroutines
- Kotlin Flow
- ROOM
- Retrofit
- Dagger Hilt

## Room Relation

- City History 1 <--- Have --->    1 Forecast Weather
- Single City  1 <--- Have --->    n Forecast Weather
- Single City  1 <--- Contain ---> n City History 

✅ Unit Tests
✅ Integration Tests
✅ End to End Test

## Unit Test code Coverage