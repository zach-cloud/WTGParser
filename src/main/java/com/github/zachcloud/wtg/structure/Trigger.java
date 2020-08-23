package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.interfaces.IReadable;
import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.wtg.WtgConstants;

import java.util.ArrayList;
import java.util.List;

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
        return "(name=" + name + ",desc=" + description + ",ecas=" + totalEcas + ")";
    }
}
