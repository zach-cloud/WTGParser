package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.interfaces.IReadable;
import com.github.zachcloud.reader.BinaryReader;

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

    /**
     * Reads from the linked Binary Reader into this structure.
     *
     * @param reader    Binary reader linked to file
     */
    @Override
    public void read(BinaryReader reader) {
        fileId = reader.readString();
        fileFormat = reader.readInt();
        triggerCategoryCount = reader.readInt();
        for(int i = 0; i < triggerCategoryCount; i++) {
            TriggerCategory category = new TriggerCategory();
            //category.read();
            triggerCategories.add(category);
        }
        unknown = reader.readInt();
        variableCount = reader.readInt();
        for(int i = 0; i < variableCount; i++) {
            Variable variable = new Variable();
            //variable.read();
            variables.add(variable);
        }
        triggerCount = reader.readInt();
        for(int i = 0; i < triggerCount; i++) {
            Trigger trigger = new Trigger();
            //trigger.read();
            triggers.add(trigger);
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
                ",categories=" + triggerCategories + ",variables="
                + variableCount + ",triggers=" + triggerCount;
    }
}
