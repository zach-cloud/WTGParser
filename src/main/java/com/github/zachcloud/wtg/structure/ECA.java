package com.github.zachcloud.wtg.structure;

public class ECA {

    /*
    int32: Type (0 = event, 1 = condition, 2 = action)
    int32: Group for if-then-else (0 = condition, 1 = then action, 2 = else action) (Only exists if it is a child-ECA)
    string: Name "x"
    int32: Is enabled (1 = yes, 0 = no)
    struct Parameter[y]: Repeat the Parameter structure y times (where y is found in our lookup table for the key x)
    int32: Number "z" of child events/conditions/actions (Version 7 only)
    struct ECA[z]: Repeat the ECA structure z times (these are child ECAs) (Version 7 only)
     */
}
