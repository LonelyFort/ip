package Angela.storage;

import Angela.tasktype.Deadline;
import Angela.tasktype.Event;
import Angela.tasktype.ToDo;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {

    @Test
    public void printListTest() {
        TaskList emptyList = new TaskList();
        TaskList threeItemList = new TaskList();
        threeItemList.add(new ToDo("test"));
        threeItemList.add(new Deadline(LocalDateTime.of(2025, 12, 31, 0, 0),"test2"));
        threeItemList.add(new Event(LocalDateTime.of(2025, 12, 31, 0, 0),LocalDateTime.of(2025, 12, 31, 0, 0) ,"test3"));

        assertEquals("", emptyList.printList());
        assertEquals("1, [T][ ] test\n" +
                "2, [D][ ] test2 (by: 2025/12/31 12:00)\n" +
                "3, [E][ ] test3 (from: 2025/12/31 12:00 to: 2025/12/31 12:00)", threeItemList.printList());
    }

    @Test
    public void saveAllTaskTest() {
        TaskList emptyList = new TaskList();
        TaskList threeItemList = new TaskList();
        threeItemList.add(new ToDo("test"));
        threeItemList.add(new Deadline(LocalDateTime.of(2025, 12, 31, 0, 0),"test2"));
        threeItemList.add(new Event(LocalDateTime.of(2025, 12, 31, 0, 0),LocalDateTime.of(2025, 12, 31, 0, 0) ,"test3"));

        assertEquals("", emptyList.saveAllTask());
        assertEquals("T| |test\n" +
                "D| |test2|2025-12-31 12:00\n" +
                "E| |test3|2025-12-31 12:00|2025-12-31 12:00", threeItemList.saveAllTask());

    }
}
