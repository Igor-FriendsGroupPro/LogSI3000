package FriendsGroup.pro;

import java.io.FileWriter;
import java.io.IOException;

public class IOReport {

    public void writeLine(String nameFile, String stringLine, boolean newFile)  {

        try (FileWriter nFile = new FileWriter(nameFile + ".txt", !newFile)) {
            nFile.write(stringLine);
            nFile.write("\n");
            nFile.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
