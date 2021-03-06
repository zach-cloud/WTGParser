package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.wtg.WtgConstants;

import java.util.Objects;

/**
 * A version 7 parameter
 */
public class ParameterV7 extends Parameter {

    private static final int FORMAT = 7;

    /**
     * Reads from the linked Binary Reader into this structure.
     *
     * @param reader Binary reader linked to file
     */
    @Override
    public void read(BinaryReader reader) {
        type = reader.readInt();
        value = reader.readString();
        hasSubParameters = reader.readInt();
        if(hasSubParameters == WtgConstants.FLAG_YES) {
            subparameters = new Subparameters(FORMAT);
            subparameters.read(reader);
            unknown = reader.readInt();
            checkUnknown();
        }
        isArray = reader.readInt();
        if(isArray == WtgConstants.FLAG_YES) {
            arrayIndex = new ParameterV7();
            arrayIndex.read(reader);
        }

    }

    /**
     * Checks the value of the unknown int.
     * It's not a problem if it's not what we expect, we just
     * log it out of interest.
     */
    private void checkUnknown() {
        if(unknown != 0) {
            System.out.println("Novelty: Unknown is not 0 in Parameter V7");
        }
    }

    @Override
    public String toString() {
        return "ParameterV7{" +
                "type=" + type +
                ", value='" + value + '\'' +
                ", hasSubParameters=" + hasSubParameters +
                ", subparameters=" + subparameters +
                ", unknown=" + unknown +
                ", isArray=" + isArray +
                ", arrayIndex=" + arrayIndex +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParameterV7 that = (ParameterV7) o;
        return type == that.type &&
                hasSubParameters == that.hasSubParameters &&
                unknown == that.unknown &&
                isArray == that.isArray &&
                Objects.equals(value, that.value) &&
                Objects.equals(subparameters, that.subparameters) &&
                Objects.equals(arrayIndex, that.arrayIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value, hasSubParameters, subparameters, unknown, isArray, arrayIndex);
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getHasSubParameters() {
        return hasSubParameters;
    }

    public void setHasSubParameters(int hasSubParameters) {
        this.hasSubParameters = hasSubParameters;
    }

    public Subparameters getSubparameters() {
        return subparameters;
    }

    public void setSubparameters(Subparameters subparameters) {
        this.subparameters = subparameters;
    }

    public int getUnknown() {
        return unknown;
    }

    public void setUnknown(int unknown) {
        this.unknown = unknown;
    }

    public int getIsArray() {
        return isArray;
    }

    public void setIsArray(int isArray) {
        this.isArray = isArray;
    }

    public Parameter getArrayIndex() {
        return arrayIndex;
    }

    public void setArrayIndex(Parameter arrayIndex) {
        this.arrayIndex = arrayIndex;
    }
}
