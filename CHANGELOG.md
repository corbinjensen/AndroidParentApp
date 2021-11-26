# CHANGELOG

### Nov 25, 2021 - Dependency injection 💉

- Remove singleton patterns in the storage modules, prevent memory leaks
- Use the `Hilt` for dependency injection

#### Why?
- Cleaner architecture
- Loose relationships between classes
- Better organization of the class hierarchy

#### Architecture flows
- Room database (and DAOs), the `SharedPreferences` of the `TimeoutTimer`, and the internal storage will instantiate when the app is open
- These storages will be injected to some services
- The above modules/services will be then injected to the **view models** for then fetching data and manipulate data in the storage
- The **views** consumes **view models**. Most of their functions of **views** are to *observe data* from view models and *perform UI logic*

More details will be addressed during in-person meeting

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
