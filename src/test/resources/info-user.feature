Feature: /info/users tests

  Scenario Outline: Send bad parameters to info/users method
    When Users info sending parameters is <start>, <end>
    Then Response status is 400
     Examples:
      | start       |  end       |
      | -1          |  1         |
      |  1          | -1         |
      | -1          | -1         |
      |  0          |  2147483648|
      | -2147483649 |  0         |
      | 0           |  0         |
      | 5           |  1         |

 Scenario Outline: Get info about single user
   When Try get user info only about <user>
   Then Receive information about only this <user>
   Examples:
     | user        |
     | 1           |
     | 2           |
     | 3           |
     | 4           |
     | 5           |
     | 6           |
     | 7           |
     | 8           |
     | 9           |
     | 10          |

  Scenario Outline: Get info about single user returns info about only single user
   When Try get user info only about <user>
   Then Receive information about only one user
   Examples:
     | user        |
     | 1           |
     | 2           |
     | 3           |
     | 4           |
     | 5           |
     | 6           |
     | 7           |
     | 8           |
     | 9           |
     | 10          |

  Scenario Outline: Get info is only about users in sending range
   When Users info sending range is <start>, <end>
   Then Receive information only about users in sending range <start>, <end>
   Examples:
     | start       |  end       |
     | 0           | 10         |
     | 0           | 9          |
     | 0           | 8          |
     | 0           | 7          |
     | 0           | 6          |
     | 0           | 5          |
     | 0           | 4          |
     | 1           | 10         |
     | 1           | 9          |
     | 1           | 8          |
     | 1           | 7          |
     | 1           | 6          |

