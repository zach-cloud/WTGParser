package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.interfaces.IReadable;
import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.triggerdata.TriggerDataMapping;
import com.github.zachcloud.wtg.WtgConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The sub-parameter object for a parameter
 */
public class Subparameters implements IReadable {

    private int format;
    private int type;
    private String name; // "x"
    private int beginParameters; // (0 = no, any other = yes)
    private List<Parameter> parameters; // Repeat the Parameter structure z times (where z is found in our lookup table for the key x)

    /**
     * Makes a new sub-parameter
     *
     * @param format    File format (4 or 7)
     */
    public Subparameters(int format) {
        this.format = format;
        parameters = new ArrayList<>();
    }

    /**
     * Reads from the linked Binary Reader into this structure.
     *
     * @param reader Binary reader linked to file
     */
    @Override
    public void read(BinaryReader reader) {
        type = reader.readInt();
        name = reader.readString();
        beginParameters = reader.readInt();
        if(beginParameters != WtgConstants.FLAG_NO) {
            int paramCount = TriggerDataMapping.getInstance().get(name);
            for(int i = 0; i < paramCount; i++) {
                Parameter newParam = Parameter.makeParameter(format, reader);
                parameters.add(newParam);
            }
        }
    }

    @Override
    public String toString() {
        return "Subparameters{" +
                "format=" + format +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", beginParameters=" + beginParameters +
                ", parameters=" + parameters +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subparameters that = (Subparameters) o;
        return format == that.format &&
                type == that.type &&
                beginParameters == that.beginParameters &&
                Objects.equals(name, that.name) &&
                Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(format, type, name, beginParameters, parameters);
    }
}
