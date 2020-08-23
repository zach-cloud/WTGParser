package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.interfaces.IReadable;
import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.wtg.WtgConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trigger implements IReadable {

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
}
