# Architecture documentation

## Class hierarchy

The program consists of two top-level packages, dungeoncrawl.ui and dungeoncrawl.game. The UI package contains the user interface
code for the program and the "game" package contains all the classes related to the various game objects and game state. The UI
package only contains the code for the application startup and UI construction and updating in the MainApp.java file.

The "game" package is further divided into game.entities and game.level sub-packages. The "game" package itself contains just the
classes that are responsible for keeping track of the current game state, relaying the necessary information back to the
user interface and updating the current gamestate based on user input.

The game.entities package contains the classes that represent various objects in the game world that can be interacted with
and the necessary abstractions to make these interactions straightforward. This package also has two sub-packages,
entities.item and entities.monster, which hold the sub-classes for the different items and monsters that appear in the
game world.

The game.level package is responsible for holding the data needed for the generation and querying of level data. It has
classes representing the whole level, individual tiles and rooms that form the game level.

The following UML diagram roughly shows the relationships of the core classes of the program.

![UML](https://raw.githubusercontent.com/TheSamsai/otm-harjoitustyo/master/documentation/uml-diagram.png)


## User interface

The UI of the program consists of a main game view, which queries the current GameState for information regarding the
level layout, the current location and status of the player and other game objects, and then draws a graphical representation
of the current state on a JavaFX canvas. An action log is displayed at the bottom of the screen and player-specific information
is available textually on the right side of the user interface.

The UI-Application separation is achieved by having the user interface ask the necessary state related information from the
GameState instance. All modification of the current state is handled by passing user input to the GameState instance.

Some UI elements, such as inventory menus are presented to the player by drawing them directly on the canvas when the certain
flags have been set in gamestate.

## Application logic

The majority of the application logic is based on the interactions of the UI and the GameState instance. The GameState acts
as a bridge between the current state of the game and the UI and also as a container of the various game objects. The UI
gets is necessary information through a number of getters, such as getLevel(), which allows the UI to draw the various tiles
of the game world. To modify the current game state, the UI passes player input to the GameState instance's processInput()
method, which then decides what actions need to be taken based on the user input and current state.

The basic program startup sequence and GameState creation is described in the following sequence diagram:

![Startup sequence](https://raw.githubusercontent.com/TheSamsai/otm-harjoitustyo/master/documentation/startup-sequence.png)

The GameState instance, in addition to processing the user's input, also works as a container for the game world and
the objects that reside within it. As such it contains functions that move game objects around or create new ones. It also
presents the current game state to various non-player characters, so that their AI can make choices regarding movement, for
example.

Typically when the player takes action, the Player object's state is modified in some way, followed by the NPCs taking their
actions in order, modifying their own state. Once the state modifications are complete, the execution is continued in the
user interface code and the updated game state is presented the to the user. 

An example of a turn of player's movement and subsequent AI movement is described in the following sequence diagram:

![Input processing sequence](https://raw.githubusercontent.com/TheSamsai/otm-harjoitustyo/master/documentation/input-process.png)

## Database

The program uses a simple sqlite database to store highscores. The database consists of a single database table
called Highscore which stores a name (string) and a score (int). The name is a name given by the player and
the score is calculated when the game is ended based on the player's progress. The database is stored in a
scores.db file and is created if it does not exist already.

