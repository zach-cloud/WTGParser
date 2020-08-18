package com.github.zachcloud.interfaces;

public interface ITriggerDataMapping {

    /**
     * Gets the number of arguments for the specified
     * trigger data entry
     *
     * @param key   Trigger data entry to get argument count
     * @return      Argument count, or throws exception if not exists
     */
    int get(String key);

    /**
     * Determines if the mapping contains this trigger data entry
     *
     * @param key   Trigger data entry
     * @return      True if exists; false if not.
     */
    boolean contains(String key);
}
