package com.github.zachcloud.triggerdata;

import com.github.zachcloud.exception.InternalException;
import com.github.zachcloud.utils.StringFormatUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.github.zachcloud.triggerdata.DataConstants.*;

public class WEStringsMapping {

    private Map<String,String> mapping;

    private static WEStringsMapping instance;

    public static WEStringsMapping getInstance() {
        if(instance == null) {
            instance = new WEStringsMapping();
        }
        return instance;
    }

    /**
     * Sets up a default we strings mapping
     * Uses internal WorldEditStrings.txt
     */
    public WEStringsMapping() {
        setupMapping(new File(getClass().getClassLoader().getResource("com.github.zachcloud.triggerdata/WorldEditStrings.txt").getFile()));
    }

    /**
     * Sets up a we strings mapping
     * using the user-provided file
     *
     * @param origin    WorldEditStrings.txt file
     */
    public WEStringsMapping(File origin) {
        setupMapping(origin);
    }

    private void setupMapping(File origin) {
        mapping = new HashMap<>();
        try {
            String currentSection = "";
            Scanner dataScanner = new Scanner(origin);
            while(dataScanner.hasNextLine()) {
                String line = dataScanner.nextLine();
                if(!line.startsWith(COMMENT_FLAG) &&
                        !line.startsWith(METADATA_FLAG) &&
                        !line.startsWith(SECTION_START_FLAG)) {
                    saveMapping(line);
                }
            }
        } catch (FileNotFoundException ex) {
            throw new InternalException(ex);
        }
    }

    private void saveMapping(String line) {
        String[] parts = line.split("=", 2);
        if(parts.length == 2) {
            parts[0] = StringFormatUtils.trimQuotes(parts[0]).trim();
            parts[1] = StringFormatUtils.trimQuotes(parts[1]).trim();
            mapping.put(parts[0], parts[1]);
        }
    }

    public boolean contains(String key) {
        return mapping.containsKey(key);
    }

    public String get(String key) {
        if(!contains(key)) {
            throw new InternalException("WESTRING not found: " + key);
        }
        return mapping.get(key);
    }
}
