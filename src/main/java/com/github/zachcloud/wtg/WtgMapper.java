package com.github.zachcloud.wtg;

import com.github.zachcloud.interfaces.IWtgMapper;
import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.wtg.structure.WtgRoot;

import java.io.File;

/**
 * A mapper that can read or write WTG files
 */
public class WtgMapper implements IWtgMapper {

    /**
     * Parses the specified file into a WTG Object
     *
     * @param file  File to read WTG data from.
     * @return      WTG Root
     */
    public WtgRoot read(File file) {
        BinaryReader reader = new BinaryReader(file);
        return read(reader);
    }

    /**
     * Parses the specified byte array into a WTG Object
     *
     * @param data  Bytes to read WTG data from
     * @return      WTG Root
     */
    public WtgRoot read(byte[] data) {
        BinaryReader reader = new BinaryReader(data);
        return read(reader);
    }

    /**
     * Parses the specified reader into a WTG Object
     *
     * @param reader    Binary reader with bytes for a WTG Object
     * @return          WTG Root
     */
    public WtgRoot read(BinaryReader reader) {
        WtgRoot root = new WtgRoot();
        root.read(reader);
        return root;
    }

    /**
     * Parses the file with the given filename into a WTG Object
     *
     * @param fileName  File name of wtg object
     * @return          WTG Root
     */
    public WtgRoot read(String fileName) {
        return read(new File(fileName));
    }
}
