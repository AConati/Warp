/**
 * Model.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The class that represents the model/the underlying game logic.
 */

package warp;

import javafx.geometry.Point2D;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Model {
    public final int SMART_SHOOTER_FIRE_RATE = 60; //the rate at which the projectiles are fired from smart turret
    public final int SHOOTER_FIRE_RATE = 30; //the rate at which teh projectiles are fired from normal turret
    public final int SHOOTER_PROJECTILE_LIFE = 25; //how many steps a projectile will take before disappearing
    public final int SHOOTER_BULLET_VELOCITY = 15; //the speed at which the projectiles move
    public final int TRANSLOCATOR_DECELERATION = 3; //how quickly the translocator stops

    private Player player;
    private List<Shooter> shooters = new ArrayList<Shooter>();
    private MagicStone magicStone;

    public enum DifficultyModifier {
        ADD_SHOOTER, ADD_SMART_SHOOTER;
    }

    private int score;
    private List<HighScore> scores;
    private DifficultyModifier nextDifficultyMod = DifficultyModifier.ADD_SHOOTER;

    public Model(double xBoundary, double yBoundary) {
        this.initialize(xBoundary, yBoundary);
        this.score = 0;
    }

    private void initialize(double xBoundary, double yBoundary) {
        Point2D playerPosition = new Point2D(10,10);
        player = new Player("hero", 5, playerPosition);
        player.setVelocity(0,0);

        Point2D shooterPosition = new Point2D(100,100);
        Shooter shooter = new Shooter(SHOOTER_PROJECTILE_LIFE, shooterPosition, true);
        shooter.setFireRate(SMART_SHOOTER_FIRE_RATE);
        shooters.add(shooter);

        magicStone = new MagicStone();
        magicStone.setPosition(Math.random()*(xBoundary - this.getMagicStone().getWidth()), Math.random()*(yBoundary - this.getMagicStone().getHeight()));
        magicStone.setVelocity(0,0);
    }

    public class HighScore implements Comparable<HighScore>{
        private String name;
        private int score;

        public HighScore(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return this.name;
        }

        public int getScore(){
            return this.score;
        }

        @Override
        public String toString() {
            return this.name + ": " + this.score;
        }

        public int compareTo(HighScore other) {
            return this.score - other.score;
        }

        public boolean equals(HighScore other) {
            return this.compareTo(other) == 0;
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public List<Shooter> getShooters() {
        return this.shooters;
    }

    public MagicStone getMagicStone() {
        return this.magicStone;
    }

    public boolean isGameOver() {
        return this.player.getLifeTotal() <= 0;
    }

    public int getScore() {
        return this.score;
    }


    public boolean loadHighScores(String filePath) {
        File file = new File(filePath);
        Scanner scanner = null;
        this.scores = new ArrayList<HighScore>();
        try {
            scanner = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.err.println("Could not find high scores file");
            return false;
        }
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] lineArray = line.split(",");
            String name = "";
            int score = 0;
            try {
                name = lineArray[0];
                score = Integer.parseInt(lineArray[1]);
            } catch (Exception e) {
                System.err.println("Improperly formatted high scores file");
                return false;
            }
            scores.add(new HighScore(name,score));
        }
        Collections.sort(scores);
        Collections.reverse(scores);
        if(scores.size() > 5)
            scores = scores.subList(0,5);
        return true;
    }

    public boolean isNewHighScore(int score) {
        if(this.scores.size() < 5)
            return true;
        for(HighScore highScore : this.scores){
            if (score > highScore.getScore()) {
                return true;
            }
        }
        return false;
    }

    public boolean writeHighScore(String filePath, String name, int score) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
        } catch(IOException e) {
            System.err.println("High score could not be written");
            return false;
        }

        this.scores.add(new HighScore(name, score));
        Collections.sort(this.scores);
        Collections.reverse(this.scores);

        int iterations = this.scores.size() >= 5 ? 5 : this.scores.size();
        for(int i = 0; i < iterations; i++) {
            String highScoreName = this.scores.get(i).getName();
            String scoreAsString = String.valueOf(this.scores.get(i).getScore());
            try {
                writer.write(highScoreName + ",");
                writer.write(scoreAsString);
                writer.newLine();
            } catch (IOException e) {
                System.err.println("High score could not be written.");
                return false;
            }
        }

        try {
            writer.close();
        } catch(IOException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }


    public List<HighScore> getHighScores() {
        return this.scores;
    }
    /**
     * Sets the chord stone's location to a new location and increments the player's score by 1.
     * @param xBoundary the maximum x boundary the stone can spawn within
     * @param yBoundary the maximum y boundary the stone can spawn within
     */
    public void spawnChordStone(double xBoundary, double yBoundary) {
        double newXPosition = Math.random()*xBoundary;
        double newYPosition = Math.random()*yBoundary;
        this.magicStone.setPosition(newXPosition, newYPosition);
        this.magicStone.makeSound();
        this.score++;
    }

    /**
     * The method for Spawning each type of shooter
     * A smart shooter targets the position of the player but has a low rate of fire
     * A normal shooter targets a random position but has a higher rate of fire
     */
    private Shooter spawnSmartShooter(double xBoundary, double yBoundary) {
        double xPosition = Math.random()*xBoundary - this.getShooters().get(0).getWidth();
        double yPosition = Math.random()*yBoundary - this.getShooters().get(0).getHeight();
        Shooter shooter = new Shooter(SHOOTER_PROJECTILE_LIFE, new Point2D(xPosition, yPosition), true);
        shooter.setFireRate(SMART_SHOOTER_FIRE_RATE);
        shooters.add(shooter);
        return shooter;
    }

    private Shooter spawnShooter(double xBoundary, double yBoundary) {
        double xPosition = Math.random()*xBoundary - this.getShooters().get(0).getWidth();
        double yPosition = Math.random()*yBoundary - this.getShooters().get(0).getHeight();
        Shooter shooter = new Shooter(SHOOTER_PROJECTILE_LIFE, new Point2D(xPosition, yPosition), false);
        shooter.setFireRate(SHOOTER_FIRE_RATE);
        double targetX = Math.random()*xBoundary;
        double targetY = Math.random()* yBoundary;
        shooter.setTarget(new Point2D(targetX, targetY));
        shooters.add(shooter);
        return shooter;
    }

    /**
     * The method used to determine which type of shooter gets spawned
     */
    public Shooter increaseDifficulty(double xBoundary, double yBoundary) {
        switch(nextDifficultyMod) {
            case ADD_SHOOTER:
                nextDifficultyMod = DifficultyModifier.ADD_SMART_SHOOTER;
                return spawnShooter(xBoundary, yBoundary);
            case ADD_SMART_SHOOTER:
                nextDifficultyMod = DifficultyModifier.ADD_SHOOTER;
                return spawnSmartShooter(xBoundary, yBoundary);
            default:
                return null;
        }
    }

}
