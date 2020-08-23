package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.interfaces.IReadable;
import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.wtg.WtgConstants;

public class TriggerCategory implements IReadable {

    private int format;
    private int id; // Id of category
    private String name; // Name
    private int isComment; // (1 = yes, 0 = no) (Version 7 only)

    public TriggerCategory(int format) {
        this.format = format;
    }

    /**
     * Reads from the linked Binary Reader into this structure.
     *
     * @param reader Binary reader linked to file
     */
    @Override
    public void read(BinaryReader reader) {
        id = reader.readInt();
        name = reader.readString();
        if(format == WtgConstants.THE_FROZEN_THRONE_FORMAT) {
            isComment = reader.readInt();
        }
    }

    @Override
    public String toString() {
        return "(name=" + name + ",id=" + id + ",isComment=" + isComment + ")" ;
    }
}
