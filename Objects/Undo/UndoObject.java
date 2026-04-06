package Objects.Undo;

import java.nio.file.Path;
import java.util.*;

/**
 * Object for storing undo data. Stores previous locations of files.
 * 
 * @author Emmett Grebe
 * @version 4-6-2026
 */
public class UndoObject {
    private Map<String, String> previousNames;
    private Map<String, Path> previousPaths;

    /**
     * Default constructor.
     */
    public UndoObject() {
        previousNames = new HashMap<String, String>(); // New filename, old filename
        previousPaths = new HashMap<String, Path>(); // Old filename, old path
    }

    /**
     * Gets the previous path of a file.
     * 
     * @param newFileName The new file name. If the file was never renamed, then just give the name.
     * @return A path to where the file previously was before the sort.
     */
    public Path getPreviousPath(String newFileName) {
        // New file name -> Old file name -> Old file path.
        return this.previousPaths.get(previousNames.get(newFileName));
    }

    /**
     * Sets the previous path of a file.
     * If there is no new name for a file, then use the same file name for oldName
     * and newName.
     * 
     * @param oldName The old file name from before the sort.
     * @param path    A path to where the file previously was before the sort.
     * @param newName The new file name.
     */
    public void setPreviousPath(String oldName, Path path, String newName) {
        this.previousNames.put(newName, oldName);
        this.previousPaths.put(oldName, path);
    }

}
