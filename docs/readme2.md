# Iteration 2 Note

- Users can choose to flip a coin without assigned children. However, the result won't be saved to the history.
- Users can create or update tasks without assigned children.
- When a child record is deleted. All coin flips referencing to the child also get deleted, and all the tasks referencing to that child will be updated to `UNASSIGNED` status.
