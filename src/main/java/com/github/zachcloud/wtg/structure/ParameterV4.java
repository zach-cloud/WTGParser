package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.wtg.WtgConstants;

import java.util.Objects;

/**
 * A version 4 parameter.
 */
public class ParameterV4 extends Parameter {

    private static final int FORMAT = 4;

    private int type; // "x" (0 = PRESET, 1 = VARIABLE, 2 = FUNCTION, 3 = STRING, -1 = INVALID)
    private String value;
    private int hasSubParameters; // "y" (1 = yes, 0 = no)
    private Subparameters subparameters; // (Only exists if y = yes)
    private int unknown; // (Always 0, Only exists if "x" = FUNCTION)
    private int isArray; // (1 = yes, 0 = no) (Only exists if "x" != 2)
    private Parameter arrayIndex; // Array index (Only exists if z = yes)

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
        }
        if(type == WtgConstants.TYPE_FUNCTION) {
            unknown = reader.readInt();
            checkUnknown();
        }
        if(type != WtgConstants.TYPE_FUNCTION) {
            isArray = reader.readInt();
            if (isArray == WtgConstants.FLAG_YES) {
                arrayIndex = new ParameterV4();
                arrayIndex.read(reader);
            }
        }
    }

    /**
     * Checks the value of the unknown int.
     * It's not a problem if it's not what we expect, we just
     * log it out of interest.
     */
    private void checkUnknown() {
        if(unknown != 0) {
            System.out.println("Novelty: Unknown is not 0 in Parameter V4 (unknown=" + unknown + ")");
        }
    }

    @Override
    public String toString() {
        return "ParameterV4{" +
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
        ParameterV4 that = (ParameterV4) o;
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
