# User manual of Dungeon Crawl

## Installation

The program requires the Java runtime and JavaFX to function.

The DungeonCrawl.jar file should be downloaded to a directory of user's choice for execution.

## Usage

The program is executed by running "java -jar DungeonCrawl" in the installation directory of the program.

The program will start up in the main game view, which draws the current level of the dungeon and the game objects, like
items and monsters. An action log will appear in the bottom of the window, which lists the last couple of events that
have taken place in the game world recently. The right side of the window contains information related to the player,
like their current health and what equipment they are currently carrying.

![Screenshot](TODO)

### Movement and controls
The player can move through the dungeon using the arrow keys. Moving into monsters initiates combat and the player rolls for
damage, which is then inflicted on the target. The dungeon also contains items, which can be picked up by moving into them.
Items that are currently in the player's inventory can be viewed by pressing "I". To use an item, move either up or down
with the arrow keys and press "Enter" when the desired item has been selected. Some items, such as armor and weapons
are equipped when used and provide a bonus to attack or defense. Items such as potions are consumed when used for some kind
of a single-use effect, like increased health. To exit the Inventory view, simply press "Escape".

### Enemies
After the player has taken their turn, the enemies around the map will take their turns in order. The monsters will attempt
to move towards the player and if they move into the player they will attack, similarly to how the player can attack enemies.

The dungeon contains multiple kinds of enemies. Some of them have increased health and defense while others have stronger
attacks. 
