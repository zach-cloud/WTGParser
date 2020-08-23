package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.exception.WtgFormatException;
import com.github.zachcloud.interfaces.IReadable;
import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.wtg.WtgConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * The root of the WTG Structure.
 */
public class WtgRoot implements IReadable {

    private String fileId; // File ID: "WTG!"
    private int fileFormat; // 4 for RoC, 7 for TFT
    private int triggerCategoryCount; // Number of trigger categories
    private List<TriggerCategory> triggerCategories; // Repeat TriggerCategory x times
    private int unknown; // Always zero
    private int variableCount; // Number of variables
    private List<Variable> variables; // Repeat the Variable structure y times
    private int triggerCount; // Number of triggers
    private List<Trigger> triggers; // Repeat the Trigger structure z times

    public WtgRoot() {
        triggerCategories = new ArrayList<>();
        variables = new ArrayList<>();
        triggers = new ArrayList<>();
    }

    /**
     * Reads from the linked Binary Reader into this structure.
     *
     * @param reader    Binary reader linked to file
     */
    @Override
    public void read(BinaryReader reader) {
        fileId = reader.readString(4);
        checkFileId();
        fileFormat = reader.readInt();
        checkFileFormat();
        triggerCategoryCount = reader.readInt();
        readTriggerCategories(reader);
        unknown = reader.readInt();
        checkUnknown();
        variableCount = reader.readInt();
        readVariables(reader);
        triggerCount = reader.readInt();
        readTriggers(reader);
    }

    private void checkUnknown() {
        if(unknown != 0) {
            System.out.println("Novelty: unknown value was not 0 (unknown = " + unknown + ") for root");
        }
    }

    private void readTriggers(BinaryReader reader) {
        for(int i = 0; i < triggerCount; i++) {
            Trigger trigger = new Trigger(fileFormat);
            trigger.read(reader);
            triggers.add(trigger);
        }
    }

    private void readVariables(BinaryReader reader) {
        for(int i = 0; i < variableCount; i++) {
            Variable variable = new Variable(fileFormat);
            variable.read(reader);
            variables.add(variable);
        }
    }

    private void readTriggerCategories(BinaryReader reader) {
        for(int i = 0; i < triggerCategoryCount; i++) {
            TriggerCategory category = new TriggerCategory(fileFormat);
            category.read(reader);
            triggerCategories.add(category);
        }
    }

    private void checkFileFormat() {
        if(fileFormat != WtgConstants.REIGN_OF_CHAOS_FORMAT &&
                fileFormat != WtgConstants.THE_FROZEN_THRONE_FORMAT) {
            throw new WtgFormatException("Not a RoC or TFT file (format = " + fileFormat + ")");
        }
    }

    private void checkFileId() {
        if(!fileId.equals("WTG!")) {
            throw new WtgFormatException("Not a WTG File (fileId = " + fileId + ")");
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
        return "fId=" + fileId + ",format=" + fileFormat +
                ",categories=" + triggerCategoryCount + ",variables="
                + variableCount + ",triggers=" + triggerCount;
    }
}
