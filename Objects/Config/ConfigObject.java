package Objects.Config;

import java.io.*;
import java.util.*;

import Logic.SorterConstants;

/**
 * Config object for Folder Sorter. Using this instead of a normal hashmap so
 * it will be immutable. Once the object is made, the hashmap is inaccessible.
 * This object has it's read functionality built in, instead of having a
 * ConfigReader. This is because the logic in both are so small it makes more
 * sense to have it be in one class. If the functionality increases, they will
 * be split back up again.
 * 
 * @author Emmett Grebe
 * @version 4-6-2026
 */
public class ConfigObject {
    private Map<String, String> folderNames;

    /**
     * Default constructor. Reads from the file.
     */
    public ConfigObject() {
        try (BufferedReader br = new BufferedReader(new FileReader(SorterConstants.userConfigPath.toString()))) {
            Map<String, String> newFolderNames = new HashMap<>();

            while (br.ready()) {
                String currLine = br.readLine();
                // Skips newlines. So the config file can be sort looking.
                // Also skips pound signs for comments in the config file.
                if ((currLine.length() > 0) && !(currLine.charAt(0) == '#')) {
                    String[] splitLine = currLine.split(" = ");
                    // Extension of file.
                    String fileType = splitLine[0];
                    // The folder the file will go in.
                    String folderName = splitLine[1];
                    newFolderNames.put(fileType, folderName);
                }
            }

            this.folderNames = newFolderNames;

        } catch (IOException ioe) {
        }
    }

    /**
     * Explicit constructor. Uses a hashmap.
     * 
     * @param folderNames The foldertypes hashmap to be used in the object.
     */
    public ConfigObject(Map<String, String> folderNames) {
        this.folderNames = folderNames;
    }

    /**
     * Gets a single folder name based on a file type.
     * 
     * @param fileType The filetype to be used. Ex: txt, pdf, exe
     * @return The String of the folder for the given filetype to be put in.
     */
    public String getFolderName(String fileType) {
        return folderNames.get(fileType);
    }

    /**
     * Gets all the folder names.
     * 
     * @return A collection of folder names.
     */
    public Collection<String> getAllFolderNames() {
        return folderNames.values();
    }
}
