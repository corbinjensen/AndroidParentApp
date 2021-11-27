# CHANGELOG

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
