package storage;

import tasktype.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Database {

    //Temporary storage
    private List<Task> listData;

    private String storagePath;

    public Database(String storagePath) {
        this.storagePath = storagePath;

    }

    public void readFromFile() throws IOException {
        try {
            BufferedWriter fileInput = new BufferedWriter(new FileWriter(this.storagePath, false));
        } catch (IOException e) {

        }
    }
}
