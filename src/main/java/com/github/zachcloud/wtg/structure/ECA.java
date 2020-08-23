package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.interfaces.IReadable;
import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.triggerdata.TriggerDataMapping;
import com.github.zachcloud.wtg.WtgConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ECA implements IReadable {

    private int format;
    private boolean isChildECA;

    private int type; // (0 = event, 1 = condition, 2 = action)
    private int group; // Group for if-then-else (0 = condition, 1 = then action, 2 = else action) (Only exists if it is a child-ECA)
    private String name;
    private int isEnabled;
    private List<Parameter> parameters; // Repeat the Parameter structure y times (where y is found in our lookup table for the key x)
    private int ecaCount; // Number "z" of child events/conditions/actions (Version 7 only)
    private List<ECA> childEcas; // Repeat the ECA structure z times (these are child ECAs) (Version 7 only)

    /**
     * Makes a new ECA
     *
     * @param isChildECA    True if this is a child ECA, false if not
     * @param format        Format of file (4 or 7)
     */
    public ECA(boolean isChildECA, int format) {
        this.isChildECA = isChildECA;
        this.format = format;
        parameters = new ArrayList<>();
        childEcas = new ArrayList<>();
    }

    /**
     * Reads from the linked Binary Reader into this structure.
     *
     * @param reader Binary reader linked to file
     */
    @Override
    public void read(BinaryReader reader) {
        type = reader.readInt();
        if(isChildECA) {
            group = reader.readInt();
        }
        name = reader.readString();
        isEnabled = reader.readInt();
        readParameters(reader);
        if(format == WtgConstants.THE_FROZEN_THRONE_FORMAT) {
            readECAs(reader);
        }
    }

    /**
     * Reads the parameters of this ECA
     *
     * @param reader    Binary reader
     */
    private void readParameters(BinaryReader reader) {
        int paramCount = TriggerDataMapping.getInstance().get(name);
        for(int i = 0; i < paramCount; i++) {
            Parameter newParam = Parameter.makeParameter(format, reader);
            parameters.add(newParam);
        }
    }

    /**
     * Reads all Event/Condition/Action objects
     *
     * @param reader    Binary reader
     */
    private void readECAs(BinaryReader reader) {
        ecaCount = reader.readInt();
        for(int i = 0; i < ecaCount; i++) {
            ECA child = new ECA(true, format);
            child.read(reader);
            childEcas.add(child);
        }
    }

    @Override
    public String toString() {
        return "ECA{" +
                "format=" + format +
                ", isChildECA=" + isChildECA +
                ", type=" + type +
                ", group=" + group +
                ", name='" + name + '\'' +
                ", isEnabled=" + isEnabled +
                ", parameters=" + parameters +
                ", ecaCount=" + ecaCount +
                ", childEcas=" + childEcas +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ECA eca = (ECA) o;
        return format == eca.format &&
                isChildECA == eca.isChildECA &&
                type == eca.type &&
                group == eca.group &&
                isEnabled == eca.isEnabled &&
                ecaCount == eca.ecaCount &&
                Objects.equals(name, eca.name) &&
                Objects.equals(parameters, eca.parameters) &&
                Objects.equals(childEcas, eca.childEcas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(format, isChildECA, type, group, name, isEnabled, parameters, ecaCount, childEcas);
    }
}
