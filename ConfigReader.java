import java.io.*;
import java.util.*;

/**
 * Reads the Config.txt and makes a ConfigObject.
 * 
 * @author Emmett Grebe
 * @version 2-24-2026
 */
public class ConfigReader {
    /**
     * Reads Config.txt and turns it into a ConfigObject.
     * 
     * @return A ConfigObject based off of the Config.txt file.
     */
    public static ConfigObject read() {
        try (BufferedReader br = new BufferedReader(new FileReader("Config.txt"))) {
            Map<String, String> folderNames = new HashMap<>();

            while (br.ready()) {
                String currLine = br.readLine();
                // Skips newlines. So the config file can be clean looking.
                // Also skips pound signs for comments in the config file.
                if ((currLine.length() > 0) || currLine.charAt(0) == '#') {
                    String[] splitLine = currLine.split(" = ");
                    String fileType = splitLine[0];
                    String folderName = splitLine[1];
                    folderNames.put(fileType, folderName);
                }
            }

            return new ConfigObject(folderNames);

        } catch (IOException ioe) {
            return null;
        }
    }
}
