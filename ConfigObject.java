import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Config object for Folder Cleaner. Using this instead of a normal hashmap so
 * it will be immutible. Once the object is made, the hashmap is inaccessible.
 * 
 * @author Emmett Grebe
 * @version 2-24-2026
 */
public class ConfigObject {
    private Map<String, String> folderNames;

    /**
     * Explicit constructor.
     * 
     * @param folderNames The foldertypes hashmap to be used in the object.
     */
    public ConfigObject(Map<String, String> folderNames) {
        this.folderNames = folderNames;
    }

    /**
     * 
     * @param fileType The filetype to be used. Ex: txt, pdf, exe
     * @return The String of the folder for the given filetype to be put in.
     */
    public String getFolderName(String fileType) {
        return folderNames.get(fileType);
    }

    public Collection<String> getAllFolderNames() {
        return folderNames.values();
    }
}
