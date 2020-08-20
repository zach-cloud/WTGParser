package com.github.zachcloud.wtg.structure;

public class WtgRoot {
    /*
    char[4]: File id (WTG!)
    int32: File format version: 4 for Reign of Chaos, 7 for Frozen Throne (very important later on)
    int32: Number "x" of trigger categories
    struct TriggerCategory[x]: Repeat the TriggerCategory structure x times
    int32: Unknown (always 0)
    int32: Number "y" of variables
    struct Variable[y]: Repeat the Variable structure y times
    int32: Number "z" of triggers
    struct Trigger[z]: Repeat the Trigger structure z times
     */
}
