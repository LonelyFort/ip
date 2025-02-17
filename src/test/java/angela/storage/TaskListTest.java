package angela.storage;

import angela.tasktype.Deadline;
import angela.tasktype.Event;
import angela.tasktype.ToDo;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {

    @Test
    public void printListTest() {
        TaskList emptyList = new TaskList();
        TaskList threeItemList = new TaskList();
        threeItemList.add(new ToDo("test", false));
        threeItemList.add(new Deadline(LocalDateTime.of(2025, 12, 31, 0, 0), "test2", true));
        threeItemList.add(new Event(LocalDateTime.of(2025, 12, 31, 0, 0),
                LocalDateTime.of(2025, 12, 31, 0, 0), "test3", false));

        assertEquals("", emptyList.printList());
        assertEquals("1, [T][ ] test\n" +
                "2, [D*][ ] test2 (by: 2025/12/31 12:00)\n" +
                "3, [E][ ] test3 (from: 2025/12/31 12:00 to: 2025/12/31 12:00)", threeItemList.printList());
    }

    @Test
    public void saveAllTaskTest() {
        TaskList emptyList = new TaskList();
        TaskList threeItemList = new TaskList();
        threeItemList.add(new ToDo("test", false));
        threeItemList.add(new Deadline(LocalDateTime.of(2025, 12, 31, 0, 0),"test2", true));
        threeItemList.add(new Event(LocalDateTime.of(2025, 12, 31, 0, 0),
                LocalDateTime.of(2025, 12, 31, 0, 0), "test3", false));

        assertEquals("", emptyList.saveAllTask());
        assertEquals("T| |test\n" +
                "D*| |test2|2025-12-31 12:00\n" +
                "E| |test3|2025-12-31 12:00|2025-12-31 12:00", threeItemList.saveAllTask());

    }

    @Test
    public void printFilteredByKeywordListTest() {
        TaskList emptyList = new TaskList();
        TaskList threeItemList = new TaskList();
        threeItemList.add(new ToDo("test", false));
        threeItemList.add(new Deadline(LocalDateTime.of(2025, 12, 31, 0, 0),"test2", true));
        threeItemList.add(new Event(LocalDateTime.of(2025, 12, 31, 0, 0),
                LocalDateTime.of(2025, 12, 31, 0, 0), "test3", false));

        assertEquals("No entries in the list matches the keyword.", emptyList.printFilteredByKeywordList("test"));
        assertEquals("1, [D*][ ] test2 (by: 2025/12/31 12:00)", threeItemList.printFilteredByKeywordList("test2"));
    }

    @Test
    public void printFilterByImportanceListTest() {
        TaskList emptyList = new TaskList();
        TaskList threeItemList = new TaskList();
        threeItemList.add(new ToDo("test", false));
        threeItemList.add(new Deadline(LocalDateTime.of(2025, 12, 31, 0, 0),"test2", true));
        threeItemList.add(new Event(LocalDateTime.of(2025, 12, 31, 0, 0),
                LocalDateTime.of(2025, 12, 31, 0, 0), "test3", false));

        assertEquals("No entries in the list are marked as important.", emptyList.printFilterByImportanceList());
        assertEquals("1, [D*][ ] test2 (by: 2025/12/31 12:00)", threeItemList.printFilterByImportanceList());
    }

    @Test
    public void printFilteredImptListTest() {
        TaskList emptyList = new TaskList();
        TaskList threeItemList = new TaskList();
        threeItemList.add(new ToDo("test", false));
        threeItemList.add(new Deadline(LocalDateTime.of(2025, 12, 31, 0, 0),"test2", true));
        threeItemList.add(new Event(LocalDateTime.of(2025, 12, 31, 0, 0),
                LocalDateTime.of(2025, 12, 31, 0, 0), "test3", false));

        assertEquals("No entries in the list are marked as important.", emptyList.printFilteredImptList("test"));
        assertEquals("No entries in the list matches the keyword.", threeItemList.printFilteredImptList("test3"));
        assertEquals("1, [D*][ ] test2 (by: 2025/12/31 12:00)", threeItemList.printFilteredImptList("test2"));
    }
}
