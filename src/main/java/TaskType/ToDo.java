package TaskType;

public class ToDo extends Task {
    public ToDo(String detail) {
        super(detail);
    }
    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
