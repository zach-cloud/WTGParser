## TriggerData.txt

To begin parsing this format you will need the latest version of TriggerData.txt (UI\TriggerData.txt) which can be extracted from the MPQs. We need this file because we must setup a mapping between functions and their number of arguments. It is assumed that we have this mapping prior to parsing WTG files.

This is a standard INI file (I expect that you can parse those, if not, Google it), we are interested in the following groups:

```
[TriggerActions]
...
[TriggerEvents]
...
[TriggerConditions]
...
[TriggerCalls]
...
```

Iterate over each key-value item (where the key does not start with '_') in these groups and count the number of arguments but filter out empty strings, numbers and the string 'nothing'.

For the group called 'TriggerCalls' one of the types is a return value, so the resulting count must be deducted by one.

Here is sample code for the setup of my mapping:
```
$areas = [
    'TriggerActions',
    'TriggerEvents',
    'TriggerConditions',
    'TriggerCalls',
];
$lookup = [];
foreach ($areas as $area)
{
    foreach ($iniParser->data->groups[$area] as $key => $argsString)
    {
        if ($key[0] == '_')
        {
            continue;
        }
        $args = explode(',', $argsString);
        $args = array_filter($args, function($arg) {
            return $arg != '0' && $arg != '1' && $arg != 'nothing' && trim($arg) != '';
        });
        $r = count($args);
        if ($area == 'TriggerCalls')
        {
            $r--;
        }
        $lookup[$key] = $r;
    }
}
```

## war3map.wtg

In the coming structures, the type 'string' is referenced. A string is a sequence of bytes terminated by a null (0x00).

The file is defined by the 'Root' structure as seen below. Everything else is nested within that. Some values display as 'Unknown'. If you can figure out what they are for, I'd love to update this specification to reach perfection some day.

Structure: Root
```
char[4]: File id (WTG!)
int32: File format version: 4 for Reign of Chaos, 7 for Frozen Throne (very important later on)
int32: Number "x" of trigger categories
struct TriggerCategory[x]: Repeat the TriggerCategory structure x times
int32: Unknown (always 0)
int32: Number "y" of variables
struct Variable[y]: Repeat the Variable structure y times
int32: Number "z" of triggers
struct Trigger[z]: Repeat the Trigger structure z times
```


Structure: TriggerCategory
```
int32: Id of the category
string: Name
int32: Is comment (1 = yes, 0 = no) (Version 7 only)
```


Structure: Variable
```
string: Name
string: Type
int32: Unknown (always 1)
int32: Is array (1 = yes, 0 = no)
int32: Array size (Version 7 only)
int32: Is initialized (1 = yes, 0 = no)
string: Initial value
```


Structure: Trigger
```
string: Name
string: Description
int32: Is comment (Version 7 only, 0 = no, any other = yes)
int32: Is enabled (1 = yes, 0 = no)
int32: Is custom (1 = yes, 0 = no)
int32: Is initially off (1 = yes, 0 = no)
int32: Run on initialization (1 = yes, 0 = no)
int32: Trigger category id
int32: Total number "x" of events/conditions/actions
struct ECA[x]: Repeat the ECA structure x times
```


Structure: ECA
```
int32: Type (0 = event, 1 = condition, 2 = action)
int32: Group for if-then-else (0 = condition, 1 = then action, 2 = else action) (Only exists if it is a child-ECA)
string: Name "x"
int32: Is enabled (1 = yes, 0 = no)
struct Parameter[y]: Repeat the Parameter structure y times (where y is found in our lookup table for the key x)
int32: Number "z" of child events/conditions/actions (Version 7 only)
struct ECA[z]: Repeat the ECA structure z times (these are child ECAs) (Version 7 only)
```


The parameter structure will be divided into separate versions for 4 and 7 because of slight nuances in whether various values exist or not.

Structure: Parameter (Version 4)
```
int32: Type "x" (0 = PRESET, 1 = VARIABLE, 2 = FUNCTION, 3 = STRING, -1 = INVALID)
string: Value
int32: Has sub parameters "y" (1 = yes, 0 = no)
struct SubParameters: (Only exists if y = yes)
int32: Unknown (Always 0, Only exists if "x" = FUNCTION)
int32: Is array "z" (1 = yes, 0 = no) (Only exists if "x" != 2)
struct Parameter: Array index (Only exists if z = yes)
```


Structure: Parameter (Version 7)
```
int32: Type "x" (0 = PRESET, 1 = VARIABLE, 2 = FUNCTION, 3 = STRING, -1 = INVALID)
string: Value
int32: Has sub parameters "y" (1 = yes, 0 = no)
struct SubParameters: (Only exists if y = yes)
int32: Unknown (Always 0, Only exists if "y" = yes)
int32: Is array "z" (1 = yes, 0 = no)
struct Parameter: Array index (Only exists if z = yes)
```


Structure: SubParameters
```
int32: Type
string: Name "x"
int32: Begin parameters "y" (0 = no, any other = yes)
struct Parameter[z]: Repeat the Parameter structure z times (where z is found in our lookup table for the key x)
```
