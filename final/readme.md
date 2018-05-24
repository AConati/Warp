# The Final Project

**Authors of this project**: Ari Conati & Grant Lee                 

**Brief Description:**                  
We are making a survival video game where you need to collect stones that represent a note in a chord. The game will get progressively difficult as time progresses and the player survives. Some key features of the game will include the ability to throw an object and teleport to that object in order to dodge other projectiles.                 

**Design Pattern:**                         
We will implement a Model View Controller, where the each component corresponds as follows:                 

   - Controller: Keyboard input corresponds to moving the player's character as well as throwing the object
        and teleporting to it.

   - View: The view window shows the player's character, the background, and the projectiles that need to be dodged.

   - Model: The xy-coordinates of the player and the projectiles; stats regarding stones collected, time; timer that updates positions of projectiles and player character based on controller input.                 

**Core Classes:**

- Shooters
- Shooters' Projectiles
- Player
- Player's Teleport Object
- Arena
- Blocks (Random Obstacles)
- Chord Stones (Objective Pieces)

