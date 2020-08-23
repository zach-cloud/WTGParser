Feature: Test the trigger strings mapper

  Scenario: Test the MapInitializationEvent
    When Trigger Strings are read
    Then Trigger Strings entry "MapInitializationEvent" should have description "Map Initialization"
    Then Trigger Strings entry "MapInitializationEvent" should have editor data "Map initialization"
    Then Trigger Strings entry "MapInitializationEvent" should have hint ""

  Scenario: Test the GetUnitLevel
    When Trigger Strings are read
    Then Trigger Strings entry "GetUnitLevel" should have description "Level Of Unit"
    Then Trigger Strings entry "GetUnitLevel" should have editor data "Level of ,~Unit"
    Then Trigger Strings entry "GetUnitLevel" should have hint "This may be used for heroes and regular units."