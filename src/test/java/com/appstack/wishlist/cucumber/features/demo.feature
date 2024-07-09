
Feature: the hello endpoint can be retrieved
  Scenario: client makes call to GET greeting endpoint
    Given client wants to write username
    When client calls greeting endpoint
    Then client receives hello text and username