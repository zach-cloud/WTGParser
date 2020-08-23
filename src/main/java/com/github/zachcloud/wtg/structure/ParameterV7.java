package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.wtg.WtgConstants;

public class ParameterV7 extends Parameter {

    private static final int FORMAT = 7;

    private int type; // "x" (0 = PRESET, 1 = VARIABLE, 2 = FUNCTION, 3 = STRING, -1 = INVALID)
    private String value;
    private int hasSubParameters; // "y" (1 = yes, 0 = no)
    private Subparameters subparameters; // (Only exists if y = yes)
    private int unknown; // (Always 0, Only exists if "y" = yes)
    private int isArray; // "z" (1 = yes, 0 = no)
    private Parameter arrayIndex; // (Only exists if z = yes)

    /**
     * Reads from the linked Binary Reader into this structure.
     *
     * @param reader Binary reader linked to file
     */
    @Override
    public void read(BinaryReader reader) {
        type = reader.readInt();
        value = reader.readString();
        hasSubParameters = reader.readInt();
        if(hasSubParameters == WtgConstants.FLAG_YES) {
            subparameters = new Subparameters(FORMAT);
            subparameters.read(reader);
            unknown = reader.readInt();
            checkUnknown();
        }
        isArray = reader.readInt();
        if(isArray == WtgConstants.FLAG_YES) {
            arrayIndex = new ParameterV7();
            arrayIndex.read(reader);
        }

    }

    private void checkUnknown() {
        if(unknown != 0) {
            System.out.println("Novelty: Unknown is not 0 in Parameter V7");
        }
    }
}
