package com.github.zachcloud.wtg.structure;

public class ParameterV7 {

    /*
    int32: Type "x" (0 = PRESET, 1 = VARIABLE, 2 = FUNCTION, 3 = STRING, -1 = INVALID)
    string: Value
    int32: Has sub parameters "y" (1 = yes, 0 = no)
    struct SubParameters: (Only exists if y = yes)
    int32: Unknown (Always 0, Only exists if "y" = yes)
    int32: Is array "z" (1 = yes, 0 = no)
    struct Parameter: Array index (Only exists if z = yes)
     */
}
