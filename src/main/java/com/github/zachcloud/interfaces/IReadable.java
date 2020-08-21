package com.github.zachcloud.interfaces;

import com.github.zachcloud.reader.BinaryReader;

/**
 * Represents an object that can be read from a binary file
 */
public interface IReadable {

    /**
     * Reads from the linked Binary Reader into this structure.
     *
     * @param reader    Binary reader linked to file
     */
    void read(BinaryReader reader);

    /**
     * Returns the status of this object.
     * Don't use this to convert back into WTG format.
     * See the IWritable for this (will make it later)
     *
     * @return  Status of object
     */
    String toString();
}
