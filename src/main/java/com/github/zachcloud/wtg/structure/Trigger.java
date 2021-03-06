package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.interfaces.IPrintable;
import com.github.zachcloud.interfaces.IReadable;
import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.utils.StringFormatUtils;
import com.github.zachcloud.wtg.WtgConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trigger implements IReadable, IPrintable {

    private int format;

    private String name;
    private String description;
    private int isComment; // (Version 7 only, 0 = no, any other = yes)
    private int isEnabled; // (1 = yes, 0 = no)
    private int isCustom; // (1 = yes, 0 = no)
    private int isInitiallyOff; // (1 = yes, 0 = no)
    private int runOnInit; // (1 = yes, 0 = no)
    private int triggerCategoryId;
    private int totalEcas;
    private List<ECA> ecas;

    /**
     * Makes a new trigger
     *
     * @param format    Format of file (4 or 7)
     */
    public Trigger(int format) {
        this.format = format;
        ecas = new ArrayList<>();
    }

    /**
     * Reads from the linked Binary Reader into this structure.
     *
     * @param reader Binary reader linked to file
     */
    @Override
    public void read(BinaryReader reader) {
        name = reader.readString();
        description = reader.readString();
        if(format == WtgConstants.THE_FROZEN_THRONE_FORMAT) {
            isComment = reader.readInt();
        }
        isEnabled = reader.readInt();
        isCustom = reader.readInt();
        isInitiallyOff = reader.readInt();
        runOnInit = reader.readInt();
        triggerCategoryId = reader.readInt();
        totalEcas = reader.readInt();
        for(int i = 0; i < totalEcas; i++) {
            ECA eca = new ECA(false, format);
            eca.read(reader);
            ecas.add(eca);
        }
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
        builder.append("Trigger");
        if(getIsComment() == WtgConstants.FLAG_YES) {
            builder.append(" (Comment)");
        }
        builder.append(": ").append(getName()).append("\n");

        if(getIsComment() != WtgConstants.FLAG_YES) {
            indentLevel++;
            StringFormatUtils.indent(builder, indentLevel);
            builder.append("Events: ").append("\n");
            for(ECA event : getEvents()) {
                builder.append(event.convert(indentLevel+1)).append("\n");
            }
            StringFormatUtils.indent(builder, indentLevel);
            builder.append("Conditions: ").append("\n");
            for(ECA condition : getConditions()) {
                builder.append(condition.convert(indentLevel+1)).append("\n");
            }
            StringFormatUtils.indent(builder, indentLevel);
            builder.append("Actions: ").append("\n");
            for(ECA action : getActions()) {
                builder.append(action.convert(indentLevel+1)).append("\n");
            }
        }
        return builder.toString();
    }

    private List<ECA> selectEcasMatching(int type) {
        List<ECA> selectedEcas = new ArrayList<>();
        for(ECA eca : ecas) {
            if(eca.getType() == type) {
                selectedEcas.add(eca);
            }
        }
        return selectedEcas;
    }

    public List<ECA> getEvents() {
        return selectEcasMatching(WtgConstants.ECA_EVENT);
    }

    public List<ECA> getConditions() {
        return selectEcasMatching(WtgConstants.ECA_CONDITION);
    }

    public List<ECA> getActions() {
        return selectEcasMatching(WtgConstants.ECA_ACTION);
    }

    @Override
    public String toString() {
        return "Trigger{" +
                "format=" + format +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isComment=" + isComment +
                ", isEnabled=" + isEnabled +
                ", isCustom=" + isCustom +
                ", isInitiallyOff=" + isInitiallyOff +
                ", runOnInit=" + runOnInit +
                ", triggerCategoryId=" + triggerCategoryId +
                ", totalEcas=" + totalEcas +
                ", ecas=" + ecas +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trigger trigger = (Trigger) o;
        return format == trigger.format &&
                isComment == trigger.isComment &&
                isEnabled == trigger.isEnabled &&
                isCustom == trigger.isCustom &&
                isInitiallyOff == trigger.isInitiallyOff &&
                runOnInit == trigger.runOnInit &&
                triggerCategoryId == trigger.triggerCategoryId &&
                totalEcas == trigger.totalEcas &&
                Objects.equals(name, trigger.name) &&
                Objects.equals(description, trigger.description) &&
                Objects.equals(ecas, trigger.ecas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(format, name, description, isComment, isEnabled, isCustom, isInitiallyOff, runOnInit, triggerCategoryId, totalEcas, ecas);
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsComment() {
        return isComment;
    }

    public void setIsComment(int isComment) {
        this.isComment = isComment;
    }

    public int getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(int isEnabled) {
        this.isEnabled = isEnabled;
    }

    public int getIsCustom() {
        return isCustom;
    }

    public void setIsCustom(int isCustom) {
        this.isCustom = isCustom;
    }

    public int getIsInitiallyOff() {
        return isInitiallyOff;
    }

    public void setIsInitiallyOff(int isInitiallyOff) {
        this.isInitiallyOff = isInitiallyOff;
    }

    public int getRunOnInit() {
        return runOnInit;
    }

    public void setRunOnInit(int runOnInit) {
        this.runOnInit = runOnInit;
    }

    public int getTriggerCategoryId() {
        return triggerCategoryId;
    }

    public void setTriggerCategoryId(int triggerCategoryId) {
        this.triggerCategoryId = triggerCategoryId;
    }

    public int getTotalEcas() {
        return totalEcas;
    }

    public void setTotalEcas(int totalEcas) {
        this.totalEcas = totalEcas;
    }

    public List<ECA> getEcas() {
        return ecas;
    }

    public void setEcas(List<ECA> ecas) {
        this.ecas = ecas;
    }
}
