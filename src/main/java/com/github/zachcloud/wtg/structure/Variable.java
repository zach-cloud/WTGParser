package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.interfaces.IReadable;
import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.wtg.WtgConstants;

public class Variable implements IReadable {

    private int format;

    private String name;
    private String type;
    private int unknown; // Always 1
    private int isArray; // (1 = yes, 0 = no)
    private int arraySize; // Version 7 only)
    private int isInitialized; // (1 = yes, 0 = no)
    private String initialValue;

    public Variable(int format) {
        this.format = format;
    }

    /**
     * Reads from the linked Binary Reader into this structure.
     *
     * @param reader Binary reader linked to file
     */
    @Override
    public void read(BinaryReader reader) {
        name = reader.readString();
        type = reader.readString();
        unknown = reader.readInt();
        checkUnknown();
        isArray = reader.readInt();
        if(format == WtgConstants.THE_FROZEN_THRONE_FORMAT) {
            arraySize = reader.readInt();
        }
        isInitialized = reader.readInt();
        initialValue = reader.readString();
    }

    private void checkUnknown() {
        if(unknown != 1) {
            System.out.println("Novelty: unknown value was not 1 (unknown = " + unknown + ")");
        }
    }

    /**
     * Returns the status of this object.
     * Don't use this to convert back into WTG format.
     * See the IWritable for this (will make it later)
     *
     * @return  Status of object
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
