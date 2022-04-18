package org.example.Model;

import java.io.FileWriter;

/**
 * Bill Class
 */
public class Bill {

    /**
     * file bill
     */
    private FileWriter fileWriter;

    /**
     * file getter
     * @return file
     */
    public FileWriter getFileWriter() {
        return fileWriter;
    }

    /**
     * file setter
     * @param fileWriter file
     */
    public void setFileWriter(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }


}
