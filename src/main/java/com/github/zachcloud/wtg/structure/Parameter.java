package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.exception.WtgFormatException;
import com.github.zachcloud.interfaces.IPrintable;
import com.github.zachcloud.interfaces.IReadable;
import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.triggerdata.TriggerDataMapping;
import com.github.zachcloud.triggerdata.TriggerStringsMapping;
import com.github.zachcloud.triggerdata.WEStringsMapping;
import com.github.zachcloud.utils.StringFormatUtils;
import com.github.zachcloud.wtg.WtgConstants;

/**
 * Generic Parameter which can exist for ver 4 or ver 7
 */
public abstract class Parameter implements IReadable, IPrintable {

    protected int type;
    protected String value;
    protected int hasSubParameters;
    protected Subparameters subparameters;
    protected int unknown;
    protected int isArray;
    protected Parameter arrayIndex;

    /**
     * Makes a new Parameter for the specified format
     *
     * @param format    File format (4 or 7)
     * @param reader    Binary reader
     * @return          A new parameter
     */
    public static Parameter makeParameter(int format, BinaryReader reader) {
        Parameter newParam;
        if(format == WtgConstants.THE_FROZEN_THRONE_FORMAT) {
            newParam = new ParameterV7();
        } else if(format == WtgConstants.REIGN_OF_CHAOS_FORMAT) {
            newParam = new ParameterV4();
        } else {
            // Should never happen. Just to be safe...
            throw new WtgFormatException("Unknown format: " + format);
        }
        newParam.read(reader);
        return newParam;
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
        String weData = value;
        if(type != WtgConstants.TYPE_PRESET) {
            if (TriggerDataMapping.getInstance().containsWeString(value)) {
                weData = TriggerDataMapping.getInstance().getWeString(value);
                if (WEStringsMapping.getInstance().contains(weData)) {
                    weData = WEStringsMapping.getInstance().get(weData);

                }
            }
            if (TriggerStringsMapping.getInstance().contains(value)
                    || value.equals("")) {
                if(!value.equals("")) {
                    weData = TriggerStringsMapping.getInstance().getEditorData(value);
                    if (hasSubParameters == WtgConstants.FLAG_YES) {
                        weData = StringFormatUtils.formatWorldEditorData(weData, subparameters.getParameters());
                    }
                } else {
                    // some sort of bug with if/then/else here...
                    // investigate
                    StringBuilder buildWeData = new StringBuilder();
                    for(Parameter param : subparameters.getParameters()) {
                        buildWeData.append(param.convert(0)).append(" ");
                    }
                    if(buildWeData.length() > 0) {
                        buildWeData.setLength(buildWeData.length() - 1);
                    }
                    weData = buildWeData.toString();
                }
            }
        }
        builder.append(weData);
        return builder.toString();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getHasSubParameters() {
        return hasSubParameters;
    }

    public void setHasSubParameters(int hasSubParameters) {
        this.hasSubParameters = hasSubParameters;
    }

    public Subparameters getSubparameters() {
        return subparameters;
    }

    public void setSubparameters(Subparameters subparameters) {
        this.subparameters = subparameters;
    }

    public int getUnknown() {
        return unknown;
    }

    public void setUnknown(int unknown) {
        this.unknown = unknown;
    }

    public int getIsArray() {
        return isArray;
    }

    public void setIsArray(int isArray) {
        this.isArray = isArray;
    }

    public Parameter getArrayIndex() {
        return arrayIndex;
    }

    public void setArrayIndex(Parameter arrayIndex) {
        this.arrayIndex = arrayIndex;
    }
}
