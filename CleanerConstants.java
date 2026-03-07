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
    final static Path userDocumentsPath = Paths.get(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Folder-Sorter");
    final static Path userConfigPath = Paths.get(userDocumentsPath.toString() + File.separator + "Config.txt");  

    final static String defaultConfigPath = "/Assets/Config.txt";
    final static String aboutTextPath = "/Assets/About.html";
    final static String guideTextPath = "/Assets/Guide.html";
    final static String iconPath = "/Assets/Icon.png";
}
