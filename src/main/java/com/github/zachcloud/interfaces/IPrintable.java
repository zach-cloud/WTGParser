package com.github.zachcloud.interfaces;

/**
 * Represents the behavior of converting this class into a
 * String reprentation which can be pretty-printed to the user
 */
public interface IPrintable {

    /**
     * Converts into a pretty string representation of object
     *
     * @param indentLevel How much this String should be indented by
     * @return String representation of object
     */
    String convert(int indentLevel);
}
