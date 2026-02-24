import java.util.Map;

/**
 * Config object for Folder Cleaner. Using this instead of a normal hashmap so
 * it will be immutible. Once the object is made, the hashmap is inaccessible.
 * 
 * @author Emmett Grebe
 * @version 2-24-2026
 */
public class ConfigObject {
    private Map<String, String> folderTypes;

    /**
     * Explicit constructor.
     * 
     * @param folderTypes The foldertypes hashmap to be used in the object.
     */
    public ConfigObject(Map<String, String> folderTypes) {
        this.folderTypes = folderTypes;
    }

    /**
     * 
     * @param fileType The filetype to be used. Ex: txt, pdf, exe
     * @return The String of the folder for the given filetype to be put in.
     */
    public String getFolderName(String fileType) {
        return folderTypes.get(fileType);
    }
}
