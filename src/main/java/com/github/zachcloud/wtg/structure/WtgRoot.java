package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.exception.WtgFormatException;
import com.github.zachcloud.interfaces.IPrintable;
import com.github.zachcloud.interfaces.IReadable;
import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.utils.StringFormatUtils;
import com.github.zachcloud.wtg.WtgConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The root of the WTG Structure.
 */
public class WtgRoot implements IReadable, IPrintable {

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
     * Makes a new WTG Root.
     */
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

    /**
     * Checks value of unknown field.
     * If it is not what we expected, it doesn't mean anything
     * is wrong. We just log it out of interest.
     */
    private void checkUnknown() {
        if(unknown != 0) {
            System.out.println("Novelty: unknown value was not 0 (unknown = " + unknown + ") for root");
        }
    }

    /**
     * Reads all triggers.
     *
     * @param reader    Binary reader
     */
    private void readTriggers(BinaryReader reader) {
        for(int i = 0; i < triggerCount; i++) {
            Trigger trigger = new Trigger(fileFormat);
            trigger.read(reader);
            triggers.add(trigger);
        }
    }

    /**
     * Reads all variables.
     *
     * @param reader    Binary reader
     */
    private void readVariables(BinaryReader reader) {
        for(int i = 0; i < variableCount; i++) {
            Variable variable = new Variable(fileFormat);
            variable.read(reader);
            variables.add(variable);
        }
    }

    /**
     * Reads all trigger categories
     *
     * @param reader    Binary reader
     */
    private void readTriggerCategories(BinaryReader reader) {
        for(int i = 0; i < triggerCategoryCount; i++) {
            TriggerCategory category = new TriggerCategory(fileFormat);
            category.read(reader);
            triggerCategories.add(category);
        }
    }

    /**
     * Checks the file format.
     * If the file format is unrecognized, we fail the reading.
     */
    private void checkFileFormat() {
        if(fileFormat != WtgConstants.REIGN_OF_CHAOS_FORMAT &&
                fileFormat != WtgConstants.THE_FROZEN_THRONE_FORMAT) {
            throw new WtgFormatException("Not a RoC or TFT file (format = " + fileFormat + ")");
        }
    }

    /**
     * Checks the File ID
     * If file id is not "WTG!", we fail the reading since it's
     * not a wtg file
     */
    private void checkFileId() {
        if(!fileId.equals("WTG!")) {
            throw new WtgFormatException("Not a WTG File (fileId = " + fileId + ")");
        }
    }

    @Override
    public String toString() {
        return "WtgRoot{" +
                "fileId='" + fileId + '\'' +
                ", fileFormat=" + fileFormat +
                ", triggerCategoryCount=" + triggerCategoryCount +
                ", triggerCategories=" + triggerCategories +
                ", unknown=" + unknown +
                ", variableCount=" + variableCount +
                ", variables=" + variables +
                ", triggerCount=" + triggerCount +
                ", triggers=" + triggers +
                '}';
    }

    /**
     * Converts into a pretty string representation of object
     *
     * @param indentLevel How much this String should be indented by
     * @return String representation of object
     */
    @Override
    public String convert(int indentLevel) {
        StringBuilder builder = new StringBuilder();
        StringFormatUtils.indent(builder, indentLevel);
        builder.append("War3map.wtg:\n");
        StringFormatUtils.indent(builder, indentLevel);
        builder.append("------------\n\n");
        for(TriggerCategory category : triggerCategories) {
            int id = category.getId();
            // Find all triggers belonging to ID so we can print them under this category
            List<Trigger> selectedTriggers = findTriggersForId(id);
            builder.append("\n");
            builder.append(category.convert(indentLevel+1));
            builder.append("\n\n");
            for(Trigger trigger : selectedTriggers) {
                builder.append(trigger.convert(indentLevel+2)).append("\n\n");
            }
        }
        return builder.toString();
    }

    private List<Trigger> findTriggersForId(int id) {
        List<Trigger> selectedTriggers = new ArrayList<>();
        for(Trigger trigger : triggers) {
            if(trigger.getTriggerCategoryId() == id) {
                selectedTriggers.add(trigger);
            }
        }
        return selectedTriggers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WtgRoot wtgRoot = (WtgRoot) o;
        return fileFormat == wtgRoot.fileFormat &&
                triggerCategoryCount == wtgRoot.triggerCategoryCount &&
                unknown == wtgRoot.unknown &&
                variableCount == wtgRoot.variableCount &&
                triggerCount == wtgRoot.triggerCount &&
                Objects.equals(fileId, wtgRoot.fileId) &&
                Objects.equals(triggerCategories, wtgRoot.triggerCategories) &&
                Objects.equals(variables, wtgRoot.variables) &&
                Objects.equals(triggers, wtgRoot.triggers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileId, fileFormat, triggerCategoryCount, triggerCategories, unknown, variableCount, variables, triggerCount, triggers);
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public int getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(int fileFormat) {
        this.fileFormat = fileFormat;
    }

    public int getTriggerCategoryCount() {
        return triggerCategoryCount;
    }

    public void setTriggerCategoryCount(int triggerCategoryCount) {
        this.triggerCategoryCount = triggerCategoryCount;
    }

    public List<TriggerCategory> getTriggerCategories() {
        return triggerCategories;
    }

    public void setTriggerCategories(List<TriggerCategory> triggerCategories) {
        this.triggerCategories = triggerCategories;
    }

    public int getUnknown() {
        return unknown;
    }

    public void setUnknown(int unknown) {
        this.unknown = unknown;
    }

    public int getVariableCount() {
        return variableCount;
    }

    public void setVariableCount(int variableCount) {
        this.variableCount = variableCount;
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public int getTriggerCount() {
        return triggerCount;
    }

    public void setTriggerCount(int triggerCount) {
        this.triggerCount = triggerCount;
    }

    public List<Trigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<Trigger> triggers) {
        this.triggers = triggers;
    }
}
