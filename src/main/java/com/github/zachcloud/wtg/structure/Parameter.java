package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.exception.WtgFormatException;
import com.github.zachcloud.interfaces.IReadable;
import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.wtg.WtgConstants;

/**
 * Generic Parameter which can exist for ver 4 or ver 7
 */
public abstract class Parameter implements IReadable {

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


}
