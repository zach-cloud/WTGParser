package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.interfaces.IPrintable;
import com.github.zachcloud.interfaces.IReadable;
import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.triggerdata.TriggerDataMapping;
import com.github.zachcloud.triggerdata.TriggerStringsMapping;
import com.github.zachcloud.utils.StringFormatUtils;
import com.github.zachcloud.wtg.WtgConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ECA implements IReadable, IPrintable {

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
        if(name.equals("IfThenElse")) {
            //System.out.println("Here1");
        }
        isEnabled = reader.readInt();
        readParameters(reader);
        if(format == WtgConstants.THE_FROZEN_THRONE_FORMAT) {
            readECAs(reader);
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
        String weData = TriggerStringsMapping.getInstance().getEditorData(name);
        weData = StringFormatUtils.formatWorldEditorData(weData, parameters);
        if(ecaCount > 0) {
            // TODO: Write
            System.out.println("TODO: Write me");
        }
        builder.append(weData);
        return builder.toString();
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

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public boolean isChildECA() {
        return isChildECA;
    }

    public void setChildECA(boolean childECA) {
        isChildECA = childECA;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(int isEnabled) {
        this.isEnabled = isEnabled;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public int getEcaCount() {
        return ecaCount;
    }

    public void setEcaCount(int ecaCount) {
        this.ecaCount = ecaCount;
    }

    public List<ECA> getChildEcas() {
        return childEcas;
    }

    public void setChildEcas(List<ECA> childEcas) {
        this.childEcas = childEcas;
    }
}
