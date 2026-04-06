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

    public UndoObject() {
        previousNames = new HashMap<String,String>();
        previousPaths = new HashMap<String,Path>();
    }



    public Path getPreviousPath(String fileName) {

    }
}
