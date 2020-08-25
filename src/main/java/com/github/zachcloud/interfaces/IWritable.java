package com.github.zachcloud.interfaces;

public interface IWritable {

    /**
     * Converts this object into its byte representation.
     *
     * @return  Byte representation of object.
     */
    byte[] toBytes();

    /**
     * Gets the size (in bytes) of this component
     *
     * @return  Num. of bytes for component
     */
    int byteSize();
}
