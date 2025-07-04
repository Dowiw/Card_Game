# Card_Game
## Description
A project about a war card game.

The purpose of this project is to develop a Card Game program with a
Graphical User Interface.

The program will be written in Java and have a GUI with saving capabilities so the user can continue the game from a saved state.

Done in fulfillment of the Back-end Development Course for the 2025 Summer Semester.

This project was built for the Eclipse IDE.

## Dependencies

- Java SE 8 or higher (JDK)
- Maven (for building and managing the project)
> note: project is built on JDK 21

## How to contribute in Eclipse:

1. **Clone or Download the Project**
   - If using Git:
     - Go to `File` → `Import...` → `Git` → `Projects from Git` → `Clone URI`.
     - Enter the repository URL, your GitHub username, and your personal access token (if required).
   - Or download the ZIP from GitHub and extract it to your workspace folder.

2. **Import the Project**
   - Go to `File` → `Import...` → `Existing Maven Projects`.
   - Select the project root folder (where `pom.xml` is located).

3. **Build the Project**
   - Right-click the project in the Project Explorer.
   - Select `Run As` → `Maven install` or `Maven build`.

4. **Run the Application**
   - In the Project Explorer, find the `Main.java` file (usually in the `common_main` package).
   - Right-click `Main.java` → `Run As` → `Java Application`.

5. **Make Changes and Test**
   - Edit code, add features, or fix bugs as needed.
   - Test your changes by running the application.

6. **Commit and Push (if using Git)**
   - Right-click the project → `Team` → `Commit...`
   - Add a commit message and push to the repository.

> **Note:** If you are contributing via GitHub, you will need to log in to your GitHub account in Eclipse to push changes. If you downloaded the ZIP, you can still edit and run the code, but you will not have access to version control features unless you import the project into a Git repository.

## Game Description & Requirements

**1.	Introduction**

War (also known as Battle in the United Kingdom) is a simple card game,
typically played by two players using a standard playing card deck, and
often played by children.

**2.	How to Play**

- The objective of the game is to win all of the cards.
- The deck is divided evenly and randomly among the players, giving
each a down stack. In unison, each player reveals the top card of
their deck -this is a "battle"- and the player with the higher card takes
both of the cards played and moves them to their stack. Aces are
high, and suits are ignored.
- If the two cards played are of equal value, then there is a "war". Both
players place the next 2 cards from their pile face down and then
another card face-up. The owner of the higher face-up card wins the
war and adds all the cards on the table to the bottom of their deck. If
the face-up cards are again equal then the battle repeats with another
set of face-down/up cards. This repeats until one player's face-up
card is higher than their opponent's.
- If a player runs out of cards during a war, that player immediately
loses. In others, the player may play the last card in their deck as
their face-up card for the remainder of the war or replay the game
from the beginning.
- The game will continue until one player has collected all of the cards.

**3.	Solution Steps**

Please follow the following steps during the solution:
- Create Packages, You need at least 2 main packages,
- You are free to use your solution, the solution must be OOP, and you
need to create at least:
- card class,
- user class (players, name and id),
- save class (save game progress),
- ...

In each class you have to:
- Constructors.
- Getter and setters.
- Print info.
- Needed methods.

You must save the game progress.

**4.	Solution Conditions**

The solution must has the following conditions (Not following the following will have penalties):
- Based on OOP, no static methods are allowed, except for
Lambda Function if needed. (20 points penalty)
- All variables must be private. (4 point penalty)
- Uses inheritance, and abstract in the solution (4 points
penalty).
- Uses Array or list to save the data (4 points penalty).
- Uses file saving to save the game progress. (4 points penalty).
- Uses the random number to shuffle and distribute the card
deck. (4 Points penalty).
- Use an Array or list to save the user's information. (2 points
penalty).
- Using a Graphical User interface controlled by a mouse or
Keyboard. (20 Points penalty).
- The GUI must had a menu bar which had at least the following
(New) for the new Game. (Save) for saving the Game. (Open)
to open the saved game. (5 points penalty).
- Code must have comments. (2 Points penalty).
- Code must not be copied. or AI generated (50 Points
penalty).
- UTF-8 character coding (1 point penalty).
- Project must show your names, in the about section Menu
Bar. (2-point penalty).
- All used images must be included in the Images folder, in your
Project.
- You can add sound effects if you would like to game.
- You can download card images from the Internet or create
your design.
- There is no pre-defined design or how the game will be at the
end, the winning group game will be given 10 extra points.

> made in Java,
> KB, Fatih, Konstiantyn, Shukrulloh, Ezra, Ferds
