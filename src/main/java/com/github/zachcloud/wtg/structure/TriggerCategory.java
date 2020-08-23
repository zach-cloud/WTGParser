package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.interfaces.IReadable;
import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.wtg.WtgConstants;

import java.util.Objects;

/**
 * A trigger categorory (folder)
 */
public class TriggerCategory implements IReadable {

    private int format;
    private int id; // Id of category
    private String name; // Name
    private int isComment; // (1 = yes, 0 = no) (Version 7 only)

    /**
     * Makes a new trigger category
     *
     * @param format    Format of file (4 or 7)
     */
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
        return "TriggerCategory{" +
                "format=" + format +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", isComment=" + isComment +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TriggerCategory that = (TriggerCategory) o;
        return format == that.format &&
                id == that.id &&
                isComment == that.isComment &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(format, id, name, isComment);
    }
}
