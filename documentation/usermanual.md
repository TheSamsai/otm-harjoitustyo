# User manual of Dungeon Crawl

## Installation

The program requires the Java runtime and JavaFX to function.

The DungeonCrawl.jar file should be downloaded to a directory of user's choice for execution.

## Usage

The program is executed by running "java -jar DungeonCrawl.jar" in the installation directory of the program.

The program will start up in the main game view, which draws the current level of the dungeon and the game objects, like
items and monsters. An action log will appear in the bottom of the window, which lists the last couple of events that
have taken place in the game world recently. The right side of the window contains information related to the player,
like their current health and what equipment they are currently carrying.

![Screenshot](https://raw.githubusercontent.com/TheSamsai/otm-harjoitustyo/master/documentation/screenshot.png)

### Objective
The objective of the game is to reach the 10th level of the dungeon and loot the treasures that have been stored there while
avoiding death at the hands of the treasures' guardians.

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

### Ending
The game can end in two ways: either you get to the treasures at the bottom of the dungeon or you die trying.
When either of those situations happens, you will be asked for a name to enter to the scoreboard and your score
will be calculated based on the levels you traversed, whether you managed to find the treasure or not and how many times
your character leveled up during your journey. 

Once you've given your name and pressed Enter your score and name are stored in the highscore database and the top 10 
runs of the highscoreboard are shown. The scoreboard is a completely local file and will only show runs that have been 
played on your computer. Pressing Enter again will exit the game.

If you don't want to store your score to the database, leave the name field empty. This will cause the database to not
store your score.
