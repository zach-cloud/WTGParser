Feature: End-to-end WTG Reader tests

  Scenario: Test reading a known WTG File
    Given The file "RoC.wtg"
    When WTG File is read
    Then There should be 19 trigger categories
    Then There should be 54 variables
    Then There should be 151 triggers

  Scenario: Test reading a known WTG File
    Given The file "TFT.wtg"
    When WTG File is read
    Then There should be 2 trigger categories
    Then There should be 0 variables
    Then There should be 4 triggers