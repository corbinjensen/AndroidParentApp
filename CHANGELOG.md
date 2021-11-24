# CHANGELOG

### Nov 23, 2021 - Major update the schemas for the database and more

- Add `whose_turn` table to store the history of completed turns of the tasks
- Update the `TaskDao` to query all the children with the time of completing a specific task
- All the data classes with embedded fields is now located in the `model.composite` package
- Rename some intermediate data classes to show the entity relationships in the database: e.g. `CoinResultAndChild` → `CoinResultWithChild`
