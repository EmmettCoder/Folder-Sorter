package Logic;

import java.io.File;

/**
 * Helper for getting parts of the filename.
 * 
 * @author Emmett Grebe
 * @version 4-18-2026
 */
public abstract class FileNameHelper {
    /**
     * Gets the filename without the extension at the end.
     * For example, "image.png" would return "image".
     * This also accounts for files with no extension.
     * 
     * @param f The file to be used.
     * @return A string of the filename without the extension.
     */
    public static String getNameWithoutExtension(File f) {
        String fullName = f.getName();
        int extensionIndex = fullName.lastIndexOf(".");

        if (extensionIndex == -1) {
            // If file has no extension.
            return fullName;
        } else {
            // If file does have an extension.
            return fullName.substring(0, extensionIndex - 1);
        }
    }

    /**
     * Gets just the extension at the end of a filename.
     * For example, "image.png" would return ".png".
     * This also accounts for files with no extension.
     * 
     * @param f The file to be used.
     * @return The extension of the file.
     */
    public static String getExtension(File f) {
        String fullName = f.getName();
        int extensionIndex = fullName.lastIndexOf(".");
        
        if (extensionIndex == -1) {
            // If file has no extension.
            return fullName;
        } else {
            // If file does have an extension.
            return fullName.substring(extensionIndex + 1);
        }
    }
}
