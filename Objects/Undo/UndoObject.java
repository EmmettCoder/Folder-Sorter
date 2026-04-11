package Objects.Undo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Object for storing undo data. Stores previous locations of files.
 * 
 * @author Emmett Grebe
 * @version 4-11-2026
 */
public class UndoObject {
    private Map<String, String> previousNames;
    private Map<Path, Path> previousPaths;

    /**
     * Constructor.
     */
    public UndoObject() {
        previousNames = new HashMap<String, String>(); // New filename, old filename
        previousPaths = new HashMap<Path, Path>(); // New path, old path
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
     * Get the previous file names.
     * @return A map of the previous file names with their new names. (new name : old name)
     */
    public Map<String, String> getPreviousNames() {
        return this.previousNames;
    }

    /**
     * Get the previous file paths.
     * @return A map of the previous file paths with their old names. (old name : old path)
     */
    public Map<Path, Path> getPreviousPaths() {
        return this.previousPaths;
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
    public void setPreviousPath(Path newPath, Path oldPath) {
        this.previousPaths.put(newPath, oldPath);
    }

    public boolean putBack(Path newPath, Path oldPath) {
        try {
            Files.move(newPath, oldPath);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
