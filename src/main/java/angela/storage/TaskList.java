package angela.storage;

import angela.tasktype.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles and store task items stored in a list.
 */
public class TaskList {
    // list for storing task items while Angela is running.
    private List<Task> listData = new ArrayList<>();

    /**
     * Checks if the list of data is empty.
     *
     * @return true if the list of data is empty, false otherwise
     */
    public boolean isEmpty() {
        return listData.isEmpty();
    }

    /**
     * Returns the number of elements in the list of data.
     *
     * @return the number of elements in the list of data
     */
    public int size() {
        return listData.size();
    }

    /**
     * Retrieves the Task at the specified index from the list of data.
     *
     * @param index the index of the Task to retrieve
     * @return the Task at the specified index
     */
    public Task get(int index) {
        return listData.get(index);
    }

    /**
     * Removes the Task at the specified index from the list of data.
     *
     * @param index the index of the Task to remove
     */
    public void remove(int index) {
        listData.remove(index);
    }

    /**
     * Adds a new Task to the list of data.
     *
     * @param newTask the Task to be added to the list
     */
    public void add(Task newTask) {
        listData.add(newTask);
    }

    /**
     * Generates a string representation of the list of tasks with each task on a new line.
     * The format is: index, task.toString().
     *
     * @return a string representation of the list of tasks
     */
    public String printList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            String line = (i + 1) + ", " + get(i).toString();
            if (i < size() - 1) {
                line += "\n";
            }
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    /**
     * Generates a string representation of all tasks in the list, each in a specific save format.
     * Each task is separated by a newline character.
     *
     * @return a string representation of all tasks in the list, formatted for saving
     */
    public String saveAllTask() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            String taskToBeSaved = get(i).toSaveFormat();
            if (i < size() - 1) {
                taskToBeSaved += "\n";
            }
            stringBuilder.append(taskToBeSaved);
        }
        return stringBuilder.toString();
    }

    /**
     * Filters the tasks in the list by the specified keyword and returns a string representation of the filtered tasks.
     * If no tasks match the keyword, a message indicating no matches is returned.
     *
     * @param keyword the keyword to filter tasks by
     * @return a string representation of the filtered tasks, or a message if no tasks match the keyword
     */
    public String printFilteredByKeywordList(String keyword) {
        TaskList filteredTaskList = new TaskList();

        for (Task task : listData) {
            if (!task.containsKeyword(keyword)) {
                continue;
            }
            filteredTaskList.add(task);
        }
        
        if (filteredTaskList.size() == 0) {
            return "No entries in the list matches the keyword.";
        }

        return filteredTaskList.printList();
    }

    /**
     * Returns a new TaskList containing only the tasks marked as important.
     *
     * @return a TaskList with tasks that are marked as important.
     */
    public TaskList filteredByImportance() {
        TaskList filteredTaskList = new TaskList();

        for (Task task : listData) {
            if (!task.isImportant()) {
                continue;
            }
            filteredTaskList.add(task);
        }
        return filteredTaskList;
    }

    /**
     * Returns a string representation of the list of tasks filtered by importance.
     * If no tasks are marked as important, a specific message is returned.
     *
     * @return a string representing the tasks marked as important, or a message indicating no important tasks.
     */
    public String printFilterByImportanceList() {
        TaskList filteredTaskList = filteredByImportance();

        if (filteredTaskList.size() == 0) {
            return "No entries in the list are marked as important.";
        }

        return filteredTaskList.printList();
    }

    /**
     * Prints a filtered list of important tasks based on the specified keyword.
     * This method first filters the task list to include only important tasks.
     * If there are no important tasks, it returns a message stating so.
     * Otherwise, it returns a list of important tasks filtered by the given keyword.
     *
     * @param keyword The keyword to filter important tasks by.
     * @return A string representing the filtered list of important tasks,
     * or a message indicating no important tasks are present.
     */
    public String printFilteredImptList(String keyword) {
        TaskList filteredTaskList = filteredByImportance();

        if (filteredTaskList.size() == 0) {
            return "No entries in the list are marked as important.";
        }

        return filteredTaskList.printFilteredByKeywordList(keyword);
    }
}
