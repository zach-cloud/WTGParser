package com.github.zachcloud.interfaces;

import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.wtg.structure.WtgRoot;

import java.io.File;

/**
 * A mapper that can read or write WTG files
 */
public interface IWtgMapper {

    /**
     * Parses the specified file into a WTG Object
     *
     * @param file  File to read WTG data from.
     * @return      WTG Root
     */
    WtgRoot read(File file);

    /**
     * Parses the specified byte array into a WTG Object
     *
     * @param data  Bytes to read WTG data from
     * @return      WTG Root
     */
    WtgRoot read(byte[] data);

    /**
     * Parses the specified reader into a WTG Object
     *
     * @param reader    Binary reader with bytes for a WTG Object
     * @return          WTG Root
     */
    WtgRoot read(BinaryReader reader);

    /**
     * Parses the file with the given filename into a WTG Object
     *
     * @param fileName  File name of wtg object
     * @return          WTG Root
     */
    WtgRoot read(String fileName);

}
