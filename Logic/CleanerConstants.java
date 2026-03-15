package Logic;
import java.io.File;
import java.nio.file.*;

/**
 * A space to put all the constants for the classes.
 * 
 * @author Emmett Grebe
 * @version 3-7-2026
 */
public class CleanerConstants {
    public final static Path userDocumentsPath = Paths.get(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Folder-Sorter");
    public final static Path userConfigPath = Paths.get(userDocumentsPath.toString() + File.separator + "Config.txt");  

    public final static String defaultConfigPath = "/Assets/Config.txt";
    public final static String aboutTextPath = "/Assets/About.html";
    public final static String guideTextPath = "/Assets/Guide.html";
    public final static String iconPath = "/Assets/Icon.png";    
}
