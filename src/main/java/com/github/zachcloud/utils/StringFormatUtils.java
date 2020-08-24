package com.github.zachcloud.utils;

import com.github.zachcloud.wtg.structure.Parameter;

import java.util.ArrayList;
import java.util.List;

public class StringFormatUtils {

    /**
     * Indents the String by a certain amount of tabs
     * (actually 4 spaces)
     *
     * @param in          Input StringBuilder
     * @param indentLevel Num. of indents
     */
    public static void indent(StringBuilder in, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            in.append("    ");
        }
    }

    public static String formatWorldEditorData(String worldEditorData, List<Parameter> params) {
        StringBuilder completeData = new StringBuilder();
        List<String> parts = new ArrayList<>();
        StringBuilder part = new StringBuilder();
        boolean readingPart = false;
        for (char c : worldEditorData.toCharArray()) {
            if (c == '\"') {
                if(part.length() > 0) {
                    // finished reading a part..
                    parts.add(part.toString());
                    part.setLength(0);
                }
            } else {
                part.append(c);
            }
        }
        if(part.length() > 0) {
            // add last part
            parts.add(part.toString());
        }
        int whichParam = 0;
        for(String whichPart : parts) {
            if(whichPart.startsWith(",~") || whichPart.startsWith("~")) {
                // get the param to fill in with
                Parameter param = params.get(whichParam);
                whichParam++;
                completeData.append(param.convert(0));
            } else {
                completeData.append(whichPart);
            }
        }
        return completeData.toString();
    }

    public static String trimQuotes(String in) {
        if (in.startsWith("\"")) {
            in = in.substring(1);
        }
        if(in.endsWith("\"")) {
            in = in.substring(0, in.length()-1);
        }
        return in;
    }
}
