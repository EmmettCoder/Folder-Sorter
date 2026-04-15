package Logic;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import Objects.Config.ConfigObject;
import Objects.Undo.UndoObject;

/**
 * Handles all sorting logic.
 * 
 * @author Emmett Grebe
 * @version 4-13-2026
 */
public class SorterLogic {
    private static ConfigObject co;
    private static UndoObject uo;

    /**
     * Sorts the given folder.
     * 
     * @param folderPath The path to the folder to sort.
     */
    public static boolean sortThere(String directory) {
        File[] files = new File(directory).listFiles();     // All files in the directory.
        if (files == null) return false;

        co = new ConfigObject();
        uo = new UndoObject();

        makeFolders(directory);

        // Logic.
        for (File f : files) {
            if (!f.isDirectory()) {     // Checks if the current file is a folder. If it is, skip.
                // Some variables to avoid magic numbers and magic strings.
                int fileNameLength = f.getName().toString().length();
                int destinationFirstHalf = f.getAbsolutePath().length() - fileNameLength;
                String[] fileSplit = f.getName().split("\\.");

                String destBeginning = f.getAbsolutePath().substring(0, destinationFirstHalf);
                
                if (fileSplit.length > 1) {                                                 // If the file name has an extension. If not, skip.
                    String destFolder = co.getFolderName(fileSplit[fileSplit.length - 1]);  // Get the folder for the file's extension.

                    if (!(destFolder == null)) {                        // If there is not a folder for the extension, do nothing.
                        Path source = Paths.get(f.getAbsolutePath());   // Path of the file.
                        Path destination = Paths.get(destBeginning +    // Path of where the file will go.
                                                     File.separator + 
                                                     destFolder + 
                                                     File.separator + 
                                                     f.getName());

                        try {
                            Files.move(source, destination);
                            uo.setPreviousPath(destination, f.toPath());
                        } catch (FileAlreadyExistsException faee) {      // If the file already exists, add a 1 to the end of the filename.
                            // Get the base name (everything before the last dot)
                            String baseName = fileSplit[0];
                            String extension = fileSplit[fileSplit.length - 1];

                            // Look for the last "(" to see if there's already a number
                            int lastOpenParen = baseName.lastIndexOf("(");
                            int lastCloseParen = baseName.lastIndexOf(")");

                            // If last open and close parenthesis exist.
                            // If the last close parenthesis comes after the first parenthesis.
                            String newName = null;
                            if (lastOpenParen != -1 && lastCloseParen != -1 && lastCloseParen > lastOpenParen) {
                                try {
                                    String numStr = baseName.substring(lastOpenParen + 1, lastCloseParen).trim(); // Extract the string between ( and )
                                    int oldNum = Integer.parseInt(numStr);
                                    int newNum = oldNum + 1;

                                    
                                    String sortBaseName = baseName.substring(0, lastOpenParen).trim();   // Strip the old (n) from the base name

                                    newName = sortBaseName + " (" + newNum + ")." + extension;
                                    destination = Paths.get(destBeginning + 
                                                            File.separator + 
                                                            destFolder + 
                                                            File.separator + 
                                                            newName);
                                } catch (NumberFormatException e) {     // If the stuff in () wasn't actually a number, treat it as a new file
                                    newName = baseName + " (1)." + extension;
                                    destination = Paths.get(destBeginning + 
                                                            File.separator + 
                                                            destFolder + 
                                                            File.separator + 
                                                            newName);
                                }
                            } else {   // No parentheses found, start with (1)
                                
                                destination = Paths
                                        .get(destBeginning + File.separator + destFolder + File.separator + baseName + " (1)." + extension);
                            }
   
                            try {  // Attempt the move with the new destination
                                Files.move(source, destination);
                                uo.setPreviousPath(destination, f.toPath());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException ioe) {

                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * Sorts the given folder and sorts the files within based on name.
     * 
     * @param folderPath The path to the folder to sort.
     * @param similarityThreshold The threshold of how similar a file is.
     */
    public static boolean sortThereSimilar(String directory, int similarityThreshold) {
        File[] files = new File(directory).listFiles();     // All files in the directory.
        if (files == null) return false;  

        co = new ConfigObject();
        uo = new UndoObject();

        makeFolders(directory);

        // Put alike files together.
        Map<String, ArrayList<File>> likeness = new HashMap<>();
        for (File f : files) {
            if (!f.isDirectory()) {
                String threshPortion = f.getName().substring(0, similarityThreshold);
                if (likeness.keySet().contains(threshPortion)) {
                    // If the map already has the word, add it to the value.
                    likeness.get(threshPortion).add(f);
                } else {
                    // If the map does not already have the word, add it as a key and a value.
                    ArrayList<File> alikeFiles = new ArrayList<>();
                    alikeFiles.add(f);
                    likeness.put(threshPortion, alikeFiles);
                }
            }
        }

        // Remove single files. Making a new map and deleting the old one. Cannot edit while iterating.
        Map<String, ArrayList<File>> likenessNoDupes = new HashMap<>();
        for (String threshString : likeness.keySet()) {
            if (likeness.get(threshString).size() > 1) likenessNoDupes.put(threshString, likeness.get(threshString));
        }


        // Logic.
        for (File f : files) {
            if (!f.isDirectory()) {     // Checks if the current file is a folder. If it is, skip.
                // Some variables to avoid magic numbers and magic strings.
                int fileNameLength = f.getName().toString().length();
                int destinationFirstHalf = f.getAbsolutePath().length() - fileNameLength;
                String[] fileSplit = f.getName().split("\\.");

                String destBeginning = f.getAbsolutePath().substring(0, destinationFirstHalf);
                
                if (fileSplit.length > 1) {                                                 // If the file name has an extension. If not, skip.
                    String destFolder = co.getFolderName(fileSplit[fileSplit.length - 1]);  // Get the folder for the file's extension.

                    if (!(destFolder == null)) {                        // If there is not a folder for the extension, do nothing.
                        Path source = Paths.get(f.getAbsolutePath());   // Path of the file.
                        Path destination = Paths.get(destBeginning +    // Path of where the file will go.
                                                     File.separator + 
                                                     destFolder + 
                                                     File.separator + 
                                                     f.getName());
                        String threshPortion = f.getName().substring(0, similarityThreshold);
                        if (likenessNoDupes.keySet().contains(threshPortion)) {
                            source = Paths.get(f.getAbsolutePath());   // Path of the file.
                            destination = Paths.get(destBeginning +    // Path of where the file will go.
                                                     File.separator + 
                                                     destFolder + 
                                                     File.separator + 
                                                     threshPortion +
                                                     File.separator + 
                                                     f.getName());
                            try {
                                // Creates the 'threshPortion' folder if it doesn't exist
                                Files.createDirectories(destination.getParent());
                                uo.addFolderMade(destination.getParent()); 
                            } catch (IOException e) {
                                System.err.println("Could not create similarity folder: " + e.getMessage());
                            }
                        }

                        try {
                            Files.move(source, destination);
                            uo.setPreviousPath(destination, f.toPath());
                        } catch (FileAlreadyExistsException faee) {      // If the file already exists, add a 1 to the end of the filename.
                            // Get the base name (everything before the last dot)
                            String baseName = fileSplit[0];
                            String extension = fileSplit[fileSplit.length - 1];

                            // Look for the last "(" to see if there's already a number
                            int lastOpenParen = baseName.lastIndexOf("(");
                            int lastCloseParen = baseName.lastIndexOf(")");

                            // If last open and close parenthesis exist.
                            // If the last close parenthesis comes after the first parenthesis.
                            String newName = null;
                            if (lastOpenParen != -1 && lastCloseParen != -1 && lastCloseParen > lastOpenParen) {
                                try {
                                    String numStr = baseName.substring(lastOpenParen + 1, lastCloseParen).trim(); // Extract the string between ( and )
                                    int oldNum = Integer.parseInt(numStr);
                                    int newNum = oldNum + 1;

                                    
                                    String sortBaseName = baseName.substring(0, lastOpenParen).trim();   // Strip the old (n) from the base name

                                    newName = sortBaseName + " (" + newNum + ")." + extension;
                                    destination = Paths.get(destBeginning + 
                                                            File.separator + 
                                                            destFolder + 
                                                            File.separator + 
                                                            newName);
                                } catch (NumberFormatException e) {     // If the stuff in () wasn't actually a number, treat it as a new file
                                    newName = baseName + " (1)." + extension;
                                    destination = Paths.get(destBeginning + 
                                                            File.separator + 
                                                            destFolder + 
                                                            File.separator + 
                                                            newName);
                                }
                            } else {   // No parentheses found, start with (1)
                                
                                destination = Paths
                                        .get(destBeginning + File.separator + destFolder + File.separator + baseName + " (1)." + extension);
                            }
   
                            try {  // Attempt the move with the new destination
                                Files.move(source, destination);
                                uo.setPreviousPath(destination, f.toPath());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException ioe) {

                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * Undoes previous sort.
     * @return True if successful, false if not.
     */
    public static boolean undoSort() {
        if (uo == null) return false;
        return uo.undoAll();
    }

    /**
     * Makes the folders given from the ConfigObject. If the folder already exists,
     * it skips the folder.
     * 
     * @param directory The directory to make the new folders in.
     * @return True if successful, false if not successful.
     */
    private static boolean makeFolders(String directory) {
        Collection<String> foldersList = co.getAllFolderNames();                      // Get all the folder names and their correlated extension.
        Path basePath = Paths.get(directory);

       
        for (String currFolder : foldersList) {                                       // Iterate over each folder.
            Path relativePath = Paths.get(currFolder);                                // Using relativePath and currWalkPath helps the program know which was made
            Path currWalkPath = basePath;                                             // by it and which are to be left alone if undo is pressed.
                                                                                      // relativePath is the whole path to the new folder.
                                                                                      // currWalkPath is the same path, but starts at the top parent.
            for (int i = 0; i < relativePath.getNameCount(); i++) {                   // getNameCount() gets the num of each individual subdirectory.
                currWalkPath = currWalkPath.resolve(relativePath.getName(i));
                try {                               
                    if (Files.notExists(currWalkPath)) {                                  // If the folder was not already there, add it to the new folders list.
                        uo.addFolderMade(currWalkPath);
                        Files.createDirectory(currWalkPath);                              // Make the folder in the directory given.
                    }
                } catch (IOException ioe) {
                    return false;
                }
            }
        }
        return true;
    }
}
