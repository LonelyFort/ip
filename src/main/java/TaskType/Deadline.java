package TaskType;

public class Deadline extends Task {
     private String end;

     public Deadline(String end, String detail) {
         super(detail);
         this.end = end;
     }

     @Override
    public String toString() {
         return String.format("[D]%s (by: %s)", super.toString(), end);
     }
}
