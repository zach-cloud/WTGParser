package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.interfaces.IReadable;
import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.wtg.WtgConstants;

import java.util.Objects;

/**
 * A world editor variable
 */
public class Variable implements IReadable {

    private int format;

    private String name;
    private String type;
    private int unknown; // Always 1
    private int isArray; // (1 = yes, 0 = no)
    private int arraySize; // Version 7 only)
    private int isInitialized; // (1 = yes, 0 = no)
    private String initialValue;

    /**
     * Makes a new variable
     *
     * @param format    Format of file (4 or 7)
     */
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


    /**
     * Checks the value of the unknown int.
     * It's not a problem if it's not what we expect, we just
     * log it out of interest.
     */
    private void checkUnknown() {
        if(unknown != 1) {
            System.out.println("Novelty: unknown value was not 1 (unknown = " + unknown + ")");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return format == variable.format &&
                unknown == variable.unknown &&
                isArray == variable.isArray &&
                arraySize == variable.arraySize &&
                isInitialized == variable.isInitialized &&
                Objects.equals(name, variable.name) &&
                Objects.equals(type, variable.type) &&
                Objects.equals(initialValue, variable.initialValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(format, name, type, unknown, isArray, arraySize, isInitialized, initialValue);
    }

    @Override
    public String toString() {
        return "Variable{" +
                "format=" + format +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", unknown=" + unknown +
                ", isArray=" + isArray +
                ", arraySize=" + arraySize +
                ", isInitialized=" + isInitialized +
                ", initialValue='" + initialValue + '\'' +
                '}';
    }
}
