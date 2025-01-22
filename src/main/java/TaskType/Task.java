package TaskType;

public class Task {
    private String detail;
    private boolean isCompleted;

    public Task(String detail) {
        this.detail = detail;
        this.isCompleted = false;
    }
    public String completedSymbol() {
        return isCompleted ? "X" : " ";
    }

    public void check() {
        this.isCompleted = true;
    }

    public void uncheck() {
        this.isCompleted = false;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public String toString() {
        return "[" + completedSymbol() + "] " + this.detail;
    }
}
