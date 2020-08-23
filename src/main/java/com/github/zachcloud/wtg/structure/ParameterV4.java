package com.github.zachcloud.wtg.structure;

import com.github.zachcloud.reader.BinaryReader;
import com.github.zachcloud.wtg.WtgConstants;

public class ParameterV4 extends Parameter {

    private static final int FORMAT = 4;

    private int type; // "x" (0 = PRESET, 1 = VARIABLE, 2 = FUNCTION, 3 = STRING, -1 = INVALID)
    private String value;
    private int hasSubParameters; // "y" (1 = yes, 0 = no)
    private Subparameters subparameters; // (Only exists if y = yes)
    private int unknown; // (Always 0, Only exists if "x" = FUNCTION)
    private int isArray; // (1 = yes, 0 = no) (Only exists if "x" != 2)
    private Parameter arrayIndex; // Array index (Only exists if z = yes)

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
        }
        if(type == WtgConstants.TYPE_FUNCTION) {
            unknown = reader.readInt();
            checkUnknown();
        }
        if(type != WtgConstants.TYPE_FUNCTION) {
            isArray = reader.readInt();
            if (isArray == WtgConstants.FLAG_YES) {
                arrayIndex = new ParameterV4();
                arrayIndex.read(reader);
            }
        }
    }

    private void checkUnknown() {
        if(unknown != 0) {
            System.out.println("Novelty: Unknown is not 0 in Parameter V4");
        }
    }
}
