Feature: /check method tests

  Background:
    Given All rooms are empty

  Scenario: Person tries to enter in a wrong room
    When Person tries to enter in a wrong room
    Then Response status is 403

  Scenario: Person tries to enter and to exit from a valid room
    When Person tries to enter and to exit in a valid room
    Then Response status is 200

  Scenario: Enter in a room
    When Person #1 enters in a room #1
    Then There is a person #1 in the room #1

  Scenario: Enter in a room twice
    When Person tries to enter in a same room twice
    Then Response status is 500

  Scenario: Person tries to enter in a room while in another room
    Given Person #2 enters in a room #1
    When Person #2 enters in a room #2
    Then Response status is 500

  Scenario: Person tries to exit from a room while in another room
    Given Person #10 enters in a room #5
    When Person #10 exits from a room #2
    Then Response status is 500

  Scenario: Person tries to exit a room while outside any room
    When Person #1 exits from a room #1
    Then Response status is 500

  Scenario Outline: Invalid parameters in check request
    When Sending parameters is "<enterance>", <keyId>, <roomId>
    Then Response status is 500
    Examples:
      | enterance | keyId | roomId |
      | ENTRANCE  |   0   |    1   |
      | ENTRANCE  |  11   |    1   |
      | ENTRANCE  |   1   |    0   |
      | ENTRANCE  |   1   |    6   |
      | ENTRANCE  |   1   |    6   |
      | ENTRANCE  |   1   |    6   |
      | EXIT      |   0   |    1   |
      | EXIT      |  11   |    1   |
      | EXIT      |   1   |    0   |
      | EXIT      |   1   |    6   |
      | EXIT      |   1   |    6   |
      | EXIT      |   1   |    6   |

  Scenario Outline: Bad parameters requests
    When Sending parameters is "<enterance>", <keyId>, <roomId>
    Then Response status is 400
    Examples:
      | enterance | keyId        | roomId         |
      |           |   1          |    6           |
      | QUIT      |   1          |    6           |
      | ENTRANCE  |   2147483648 |    1           |
      | ENTRANCE  |  -2147483649 |    1           |
      | ENTRANCE  |   1          |    2147483648  |
      | ENTRANCE  |   1          |   -2147483649  |
