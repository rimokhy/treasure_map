# Treasure Map

### Context
This program is a hands-on exercise made for [CarbonIT](http://carbon-it.fr/).\
Written in Kotlin, compilable with maven. Runs with java 8 or above.

### Goal
Given an input file, generates a map configured with specified entities.\
The game takes place on a map, where adventurers evolves to find treasures, or a road blocked with mountains.
An adventurer can hold any number of treasures, but he can only loot them one by one.\
Multiple adventurers can play together, but they will play alternately according to their position in the configuration file.

### Evolution of a game
Adventurers will move according to their sequence of movements, executing one action by turn.
If an adventurer steps on a treasure tile, they will pickup one of it. If they want more, they have to move back, then move forward again.
An adventurer cannot move through a mountain. When a mountain is met, the adventurer will consume his movement, but the adventurer will not move.

###### Example input file:
```
C​ - 3 - 4
M​ - 1 - 0
M​ - 2 - 1
T​ - 0 - 3 - 2
T​ - 1 - 3 - 3
A​ - Lara - 1 - 1 - S - AADADAGGA
```
A game requires at least 1 [map](#map-configuration), 1 [treasure](#treasure-configuration), and 1 [adventurer](#adventurer-configuration) to start.

#### Game configuration 
Currently implemented entities:
* [Map](#map-configuration)
* [Mountain](#mountain-configuration)
* [Treasure](#treasure-configuration)
* [Adventurer](#adventurer-configuration)

#### Map configuration
Generates a map of size **Horizontal axis**, **Vertical axis**.
```
# C - {Horizontal axis} - {Vertical Axis}
C​ - 3 - 4
```
#### Mountain configuration
Put a mountain on the map at position **Horizontal axis**, **Vertical axis**.
```
# M - {Horizontal axis} - {Vertical Axis}
M - 1 - 0
M​ - 2 - 1
```
#### Treasure configuration
Put a treasure on the map at position **Horizontal axis**, **Vertical axis** harboring **Number of treasures** treasures.
```
# T - {Horizontal axis} - {Vertical Axis} - {Number of treasure}
T​ - 0 - 3 - 2
T​ - 1 - 3 - 3
```
#### Adventurer configuration
Put an adventurer named **Adventurer name** on the map at position **Horizontal axis**, **Vertical axis**, facing **Orientation**.
The adventurer will move according to its **Sequence of movements**.
```
# A - {Adventurer name} - {Horizontal axis} - {Vertical Axis} - {Orientation} - {Sequence of movements}
A​ - Lara - 1 - 1 - S - AADADAGGA
```
##### Sequence of movements
A - Move one tile in the direction of player orientation\
G - Rotate player left\
G - Rotate player right


##### End of game
The game is over when all the adventurers have completed their sequence of movements.
At the end of the game, the program will output a file containing the number of remaining treasures, and the number of treasures in the adventurer's bag.
###### Example output file from [input file](#example-input-file):
```
C - 3 - 4
M - 1 - 0
M - 2 - 1
# T - {Horizontal axis} - {Vertical Axis} - {Number of treasures left}
T - 0 - 3 - 0
T - 1 - 3 - 2
# A - {Adventurer name} - {Horizontal axis} - {Vertical Axis} - {Orientation} - {Number of treasures}
A - Lara - 0 - 3 - South - 3
```

### Usage

##### Compile the project using maven
```shell script
$> mvn clean install
```
This will install the release (currently 0.1.0) jar under directory `dist/`

##### Or download the [latest release](https://github.com/rimokhy/treasure_map/releases/download/0.1.0/treasure-map-0.1.0.jar)

##### Runs the program with default configuration
```shell script
$> java -jar dist/treasure-map-0.1.0.jar
```
###### Options
|   Option    |     Effect     |   Default value    |
| :------------: | :-------------: | :-------------: |
|   -f [file]    |     Configuration file     |        [src/main/resources/treasure_map.txt](./src/main/resources/treasure_map.txt) |
|   -o [file]   |     Output file     |       ./out.txt |
##### Runs the program with options
```shell script
$> java -jar dist/treasure-map-0.1.0.jar -f ./my_configuration_file.txt -o ./output.txt
```

### Run test
Unit tests are made with JUnit5, and can be run using maven :
```
$> mvn clean test
```
