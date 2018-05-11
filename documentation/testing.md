# Testing documentation

## Unit and integration testing

### Application logic

Tests related to central application logic are located in GameStateTest. These tests player movement,
correct gamestate initialization and input processing and the interactions between the Level class, the Player,
Monster and Item classes, so player and NPC movement is limited to allowed game area.

RoomTest and LevelTest test the functionality of the Level and Room classes with some simple tests. 
ItemTest tests the functionality of items and their interoperability with the Player class. DatabaseTest tests
that the database stores and presents data in the correct way and order.

### Test coverage

UI code is not included in the testing coverage. Instruction coverage is 86% and branch coverage is at 79%.

![Test coverage](https://raw.githubusercontent.com/TheSamsai/otm-harjoitustyo/master/documentation/test-coverage.png)

### System testing

System testing was done manually by playing the game on two separate Linux systems.

#### Installation and configuration

The program was downloaded from Github and was run according to the instructions in the [user manual](https://github.com/TheSamsai/otm-harjoitustyo/blob/master/documentation/usermanual.md). The game was executed
from its download directory with and without a highscore database present. 

#### Features

All the features listed on the [Requirements Spec](https://github.com/TheSamsai/otm-harjoitustyo/blob/master/documentation/RequirementSpec.md) was tested and illegal movement and player actions were tried, such
as entering inaccessible areas of the game world and giving the game errorneous user input in menus.

#### Current quality issues

The user interface does not provide the user with error messages when illegal actions are tried. Empty names
in the name entry screen are handled but no feedback is given to the user.
