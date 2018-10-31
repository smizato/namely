package usbac.namely;

import java.io.File;

public final class FileFunctions {
    
    /**
     * Returns the number of matches with the indicated string in the file name
     * @param file the file
     * @param string the string that will be looked for
     * @return the number of matches that are in the file name
     */
    public static int numberOfMatches(File file, String string) {
        return file.getName().length() - file.getName().replace(string, "").length();
    }
    
    
    /**
     * Returns <code>true</code> if the file name has an extension, <code>false</code> otherwise
     * @param file the file
     * @return <code>true</code> if the file name has an extension, <code>false</code> otherwise
     */
    public static boolean hasExtension(File file) {
        return file.getName().length() != getExtension(file.getName()).length();
    }
    
    
    /**
     * Returns the extension of the indicated file
     * @param fileName the name of the file
     * @return the extension
     */
    public static String getExtension(String fileName) {
        if (!fileName.contains(".")) 
            return fileName;
        return fileName.substring(fileName.lastIndexOf("."), fileName.length());
    }
    
    
    /**
     * Returns the name of the file without the extension
     * @param file the file
     * @return the name of the file without the extension
     */
    public static String getNameNoExtension(File file) {
        String name = file.getName();
        String extension = FileFunctions.getExtension(name);
        if (name.length() > extension.length())
            return name.substring(0, name.length()-extension.length());
        return name;
    }
    
    
    /**
     * Invert the text of the file name
     * @param file the file
     * @return the file with the name inverted
     */
    public static File inverse(File file) {
        String extension = getExtension(file.getName());
        String invertedName = new StringBuffer(getNameNoExtension(file))
                                              .reverse()
                                              .toString();
        //If the file doesn't have an extension, clear it
        if (!hasExtension(file)) 
            extension = "";
        return new File(file.getParent(), invertedName + extension);
    }
    
    
    /**
     * Invert the position of two parts of the file name which are splitted by a separator. <p>
     * Example: A - B > B - A
     * @param file the file
     * @param separator the separator which is between the two parts
     * @param spacing whether apply or not a white space between the text and the separator
     * @return the file with the order changed
     */
    public static File changeOrder(File file, char separator, boolean spacing) {
        //Check if the Separator is in the file's name, if it isn't return the file without changes.
        int totalSeparators = numberOfMatches(file, String.valueOf(separator));
        if (totalSeparators != 1 || separator == '.')
            return new File(file.getParent(), file.getName());
        
        String extension = getExtension(file.getName());
        
        //Adjust the file name length and delete the extension if the file doesn't have an extension
        int length;
        if (hasExtension(file))
            length = getLengthNoExtension(file);        
        else {
            length = file.getName().length();
            extension = "";
        }
        
        int separatorIndex = file.getName().indexOf(separator);
        //Get the substrings and delete extra whitespaces at the beginning or ending of them
        String partOne = file.getName()
                             .substring(0, separatorIndex)
                             .trim();
        String partTwo = file.getName()
                             .substring(separatorIndex+1, length)
                             .trim();
        String space = spacing? " ":"";
        
        String newName = partTwo + space + String.valueOf(separator) + space + partOne + extension;
        return new File(file.getParent(), newName);
    }
    
    
    /**
     * Replace every occurrence of <code>original</code> with <code>replacement</code> in the file name
     * @param file the file
     * @param original the original text
     * @param replacement the new text
     * @return the file with the indicated text replaced
     */
    public static File replace(File file, String original, String replacement) {
        String extension = getExtension(file.getName());
        String newName = file.getName()
                             .replace(original, replacement);
        if (FileFunctions.hasExtension(file))
            newName = newName.substring(0, newName.length() - extension.length());
        else
            extension = "";
        return new File(file.getParent(), newName + extension);
    }
    
    
    /**
     * Change the letter case of the file name
     * @param file the file
     * @param option the indicated option (1.All Lowercase 2.All Uppercase 3.Inverse Cases)
     * @return the file with the letter case modified
     */
    public static File cases(File file, int option) {
        String extension = getExtension(file.getName()),
               newName = file.getName();
        int length;
        if (hasExtension(file)) {
            length = getLengthNoExtension(file);        
        } else {
            length = file.getName().length();
            extension = "";   
        }
        
        switch (option) {
            //All Lowercase
            case 0: newName = file.getName().toLowerCase().substring(0, length);
                break;
            //All Uppercase
            case 1: newName = file.getName().toUpperCase().substring(0, length);
                break;
            //Inverse cases
            case 2:
                char chars[] = file.getName().toCharArray();
                for (int i = 0; i < length; i++) {
                    chars[i] = Character.isUpperCase(chars[i]) ? 
                        Character.toLowerCase(chars[i]):Character.toUpperCase(chars[i]);
                }
                newName = String.valueOf(chars).substring(0, length);
                break;
        }
        return new File(file.getParent(), newName + extension);    
    }
    
    
    /**
     * Returns the length of the file name without the extension
     * @param file the file
     * @return the length of the file name without the extension
     */
    public static int getLengthNoExtension(File file) {
        return file.getName().length() - getExtension(file.getName()).length();
    }
    
    
    /**
     * Returns the size of the file
     * @param file the file
     * @return the size of the file expressed in kB
     */
    public static String getSizeInKb(File file) {
        return String.format("%.2f", file.length()/1024f);
    }
}