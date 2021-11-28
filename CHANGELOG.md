# CHANGELOG

### Nov 26, 2021 - New implementation for Timeout

- Reimplement timer by using `LiveData` and view model to fulfill *single responsibility principles*
- Remove the `Timer` model and embed Android Timer inside the view model
- Remove `TimeoutSetting` and replace it with `TimeoutStorage` to support the new model
- Construct a new view model for breath screen (addressing #65)

#### Why?

- The old implement was very disorganized and hard to make the changes for new features.
- The old code somehow violate the *single responsibility principles*. The view should **not** access to the class for timeout preferences.

### Nov 24, 2021 - View models, MVVM and more optimization

- Implement view models to separate UI and database layer for children, coin flips, and tasks
    - View models **fetch** data from the database
    - Views only **observe** and **show** the data from the view models
- Update some methods in DAOs to return `LiveData` for dynamic data
- Add generic `BaseDao` interface to support adding, updating, or deleting data

#### Future plan  

- Implement a view model for the timer to replace the existing one
- Use **dependency injection** to mitigate the use of singletons

### Nov 23, 2021 - Major update the schemas for the database and more

- Add `whose_turn` table to store the history of completed turns of the tasks
- Update the `TaskDao` to query all the children with the time of completing a specific task
- All the data classes with embedded fields is now located in the `model.composite` package
- Rename some intermediate data classes to show the entity relationships in the database: e.g. `CoinResultAndChild` → `CoinResultWithChild`
