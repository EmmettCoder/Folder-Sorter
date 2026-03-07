import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A space to put all the constants for the classes.
 * 
 * @author Emmett Grebe
 * @version 3-7-2026
 */
public class CleanerConstants {
    final static Path documentsPath = Paths.get(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Folder-Sorter");
    final static Path configPath = Paths.get(documentsPath.toString() + File.separator + "Config.txt");  
}
