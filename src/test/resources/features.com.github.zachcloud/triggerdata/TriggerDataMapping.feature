Feature: Test the Trigger Data Mapper

  Scenario: Test a mapping from TriggerEvents
    When Trigger Data is read
    Then Trigger Data Entry "TriggerRegisterDeathEvent" should have 1 arguments

  Scenario: Test a mapping from TriggerConditions
    When Trigger Data is read
    Then Trigger Data Entry "OperatorCompareBoolean" should have 3 arguments

  Scenario: Test a mapping from TriggerActions
    When Trigger Data is read
    Then Trigger Data Entry "WaitForSoundBJ" should have 2 arguments

  Scenario: Test a mapping from TriggerCalls
    When Trigger Data is read
    Then Trigger Data Entry "IsTerrainPathableBJ" should have 2 arguments