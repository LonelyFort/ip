package Angela.storage;

import Angela.tasktype.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> listData = new ArrayList<>();

    public boolean isEmpty() {
        return listData.isEmpty();
    }

    public int size() {
        return listData.size();
    }

    public Task get(int index) {
        return listData.get(index);
    }

    public void remove(int index) {
        listData.remove(index);
    }

    public void add(Task newTask) {
        listData.add(newTask);
    }

    public void printList() {
        for (int i = 0; i < size(); i++) {
            System.out.println((i + 1) + ". " + get(i).toString());
        }
    }

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
}
