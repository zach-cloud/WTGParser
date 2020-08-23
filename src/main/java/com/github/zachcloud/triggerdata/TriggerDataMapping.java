package com.github.zachcloud.triggerdata;

import com.github.zachcloud.exception.WtgReadingException;
import com.github.zachcloud.interfaces.ITriggerDataMapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A class to represent the mapping between the TriggerData
 * content, which relates a function with its number of arguments.
 */
public class TriggerDataMapping implements ITriggerDataMapping {

    public static final String COMMENT_CHAR = "//";
    public static final String COMMENT_FLAG = COMMENT_CHAR;
    public static final String METADATA_FLAG = "_";
    public static final String SECTION_START_FLAG = "[";
    /**
     * Internal mapping of trigger data entry to argument count
     */
    private Map<String,Integer> mapping;

    private static TriggerDataMapping instance;

    public static TriggerDataMapping getInstance() {
        if(instance == null) {
            instance = new TriggerDataMapping();
        }
        return instance;
    }

    /**
     * Sets up a default trigger data mapping
     * Uses internal TriggerData.txt
     */
    public TriggerDataMapping() {
        setupMapping(new File(getClass().getClassLoader().getResource("com.github.zachcloud.triggerdata/TriggerData.txt").getFile()));
    }

    /**
     * Sets up a trigger data mapping
     * using the user-provided file
     *
     * @param origin    TriggerData.txt file
     */
    public TriggerDataMapping(File origin) {
        setupMapping(origin);
    }

    /**
     * Generates a mapping based on the file provided.
     *
     * @param mappingFile   TriggerData.txt file
     */
    private void setupMapping(File mappingFile) {
        this.mapping = new HashMap<>();
        try {
            readTriggerDataFile(mappingFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new WtgReadingException(ex);
        }
    }

    /**
     * Reads the file into the Mapping object.
     *
     * @param mappingFile   Mapping file (TriggerData.txt)
     */
    private void readTriggerDataFile(File mappingFile) throws FileNotFoundException {
        String currentSection = "";
        Scanner dataScanner = new Scanner(mappingFile);
        while(dataScanner.hasNextLine()) {
            String line = dataScanner.nextLine();
            // Skip comments and metadata
            if(!line.startsWith(COMMENT_FLAG) && !line.startsWith(METADATA_FLAG)) {
                // Handle the section character
                if(line.startsWith(SECTION_START_FLAG)) {
                    // Save the section we're currently in
                    currentSection = line.substring(1, line.length() - 1);
                } else {
                    // Only save the sections we care about.
                    if(currentSection.equals("TriggerActions") ||
                            currentSection.equals("TriggerEvents") ||
                            currentSection.equals("TriggerConditions") ||
                            currentSection.equals("TriggerCalls")) {
                        saveMapping(currentSection, line);
                    }
                }
            }
        }
    }

    /**
     * Parses and saves mapping for this key/value.
     *
     * @param currentSection    Which section we are in
     * @param line              Entry from file
     */
    private void saveMapping(String currentSection, String line) {
        String[] parts = line.split("=");
        // Ignore any garbage lines
        if(parts.length == 2) {
            String key = parts[0];
            int argsCount = count(parts[1]);
            if(currentSection.equals("TriggerCalls")) {
                argsCount--;
            }
            mapping.put(key, argsCount);
        }
    }

    /**
     * Counts the number of arguments from the argument String
     *
     * @param input Argument string, separated by commas.
     * @return      Number of arguments
     */
    private int count(String input) {
        if(!input.contains(",")) {
            // Handle the garbage.
            return 0;
        }
        int count = 0;
        for(String arg : input.split(",")) {
            if(isValidArgument(arg)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Determines if this argument is a valid argument
     * that should be considered in argument count.
     *
     * @param arg   String to check
     * @return      True if valid; false if not.
     */
    private boolean isValidArgument(String arg) {
        return !isNumeric(arg) && !isBlank(arg);
    }

    /**
     * Determines if this String is blank.
     * If it is null, empty, or equal to "nothing", it is blank.
     *
     * @param arg   String to check
     * @return      True if blank; false if not.
     */
    private boolean isBlank(String arg) {
        return (arg == null || arg.isEmpty() || arg.equals("nothing"));
    }

    /**
     * Determines if this String is a number.
     *
     * @param arg   String to check
     * @return      True if number; false if not.
     */
    private boolean isNumeric(String arg) {
        if (arg == null) {
            return false;
        }
        try {
            Double.parseDouble(arg);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Gets the number of arguments for the specified
     * trigger data entry
     *
     * @param key Trigger data entry to get argument count
     * @return Argument count, or throws exception if not exists
     */
    @Override
    public int get(String key) {
        if(!contains(key)) {
            throw new IllegalArgumentException("Trigger data entry does not exist: " + key);
        }
        return mapping.get(key);
    }

    /**
     * Determines if the mapping contains this trigger data entry
     *
     * @param key Trigger data entry
     * @return True if exists; false if not.
     */
    @Override
    public boolean contains(String key) {
        return mapping.containsKey(key);
    }
}
