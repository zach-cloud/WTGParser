package com.github.zachcloud.triggerdata;

import com.github.zachcloud.exception.InternalException;
import com.github.zachcloud.triggerdata.model.TriggerString;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Creates a mapping between the eca name and
 * the description, from TriggerStrings.txt
 */
public class TriggerStringsMapping {

    private Map<String, TriggerString> mapping;

    private static TriggerStringsMapping instance;

    public static TriggerStringsMapping getInstance() {
        if(instance == null) {
            instance = new TriggerStringsMapping();
        }
        return instance;
    }

    /**
     * Sets up a default trigger data mapping
     * Uses internal TriggerData.txt
     */
    public TriggerStringsMapping() {
        setupMapping(new File(getClass().getClassLoader().getResource("com.github.zachcloud.triggerdata/TriggerStrings.txt").getFile()));
    }

    /**
     * Sets up a trigger data mapping
     * using the user-provided file
     *
     * @param origin    TriggerData.txt file
     */
    public TriggerStringsMapping(File origin) {
        setupMapping(origin);
    }

    private void setupMapping(File origin) {
        mapping = new HashMap<>();
        try {
            Scanner fileScanner = new Scanner(origin);
            while(fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                // Ignore comments, blank lines, and section starts
                if(!line.startsWith("//") && !line.isEmpty() && !line.startsWith("[")) {
                    saveEntry(line);
                }
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            throw new InternalException(ex);
        }

    }

    private void saveEntry(String entry) {
        String[] parts = entry.split("=", 2);
        boolean isHint = false;
        if(parts[0].endsWith("Hint")) {
            // Trim the hint part
            parts[0] = parts[0].substring(0, parts[0].length()-("Hint".length()));
            isHint = true;
        }
        String key = parts[0];
        String value = "";
        if(parts.length == 2) {
            value = parts[1];
        }

        TriggerString existingString = mapping.get(key);
        if(existingString == null) {
            existingString = new TriggerString();
        }

        if(!existingString.isHasSetDescription()) {
            existingString.setDescription(value);
            existingString.setHasSetDescription(true);
        } else if(!existingString.isHasSetEditorData()) {
            existingString.setEditorData(value);
            existingString.setHasSetEditorData(true);
        } else if(!existingString.isHasSetHint() && isHint) {
            existingString.setHint(value);
            existingString.setHasSetHint(true);
            // Else, ignore it.
        }

        mapping.put(key, existingString);

    }

    public String getDescription(String key) {
        if(!contains(key)) {
            throw new InternalException("Trigger strings entry does not exist: " + key);
        }
        String description = mapping.get(key).getDescription();
        if (description.startsWith("\"")) {
            description = description.substring(1);
        }
        if(description.endsWith("\"")) {
            description = description.substring(0, description.length()-1);
        }
        return description;
    }

    public String getHint(String key) {
        if(!contains(key)) {
            throw new InternalException("Trigger strings entry does not exist: " + key);
        }
        return mapping.get(key).getHint();
    }

    public String getEditorData(String key) {
        if(!contains(key)) {
            throw new InternalException("Trigger strings entry does not exist: " + key);
        }
        return mapping.get(key).getEditorData();
    }

    public boolean contains(String key) {
        return mapping.containsKey(key);
    }
}
