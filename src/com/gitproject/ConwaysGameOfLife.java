package com.gitproject;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ConwaysGameOfLife extends JFrame{

    int size = 100;  // number of rows and cols
    boolean[][] cellGrid;  // a simple 2d array to store the alive/dead state of cells as true/false
    JButton[][] cell;  // a button representation for each cell

    public ConwaysGameOfLife() {
        Random random = new Random();  // to randomly decide true/false value for each cell
        cellGrid = new boolean[size][size];
        cell = new JButton[size][size];
        setSize(600,600);  // you may change size and layout to whatever you like
        setLayout(new GridLayout(100,100));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cellGrid[i][j] = random.nextBoolean();  // randomly generating alive/dead cells
                JButton temp = new JButton();
                temp.setBorderPainted(false);  // removing the cell borders to make it look more natural and pretty
                if (cellGrid[i][j]) // check if cell is alive or dead
                    temp.setBackground(Color.WHITE); // initial color if alive
                else
                    temp.setBackground(Color.BLACK);  //initial color if dead
                add(temp);
                cell[i][j] = temp;
            }
        }
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Timer timer = new Timer(100, e -> {  // representing a game loop, using lambda expression
            boolean[][] temp = new boolean[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int count = countNeighbors(i,j);  // since the entire game depends of num of neighbors, it makes sense to count em and make life easier to implement all rules

                    if (cellGrid[i][j]) {

                        // Rule 1: Any live cell with fewer than two live neighbours dies, as if by underpopulation.
                        if (count < 2) {
                            temp[i][j] = false;
                        }
                        // Rule 2: Any live cell with two or three live neighbours lives on to the next generation.
                        if (count == 2 || count == 3) {
                            temp[i][j] = true;
                        }
                        // Rule 3: Any live cell with more than three live neighbours dies, as if by overpopulation.
                        if (count > 3) {
                            temp[i][j] = false;
                        }
                    }
                    else {
                        // Rule 4: Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
                        if (count == 3) {
                            temp[i][j] = true;
                        }
                    }
                }
            }
            cellGrid = temp;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (cellGrid[i][j]) {  // If cell's alive, let's party with random colors
                        cell[i][j].setBackground(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(100)));
                    }
                    else {  // If not alive, let's mourn with a black background
                        cell[i][j].setBackground(Color.BLACK);
                    }
                }
            }
        });
        timer.start();
    }

    int countNeighbors(int x, int y) {  // coordinates of our cell
        int life = 0;  // initially assume life around us is zero

        for (int i = x-1; i <= x+1; i++) {  // we only compare ourselves with the 8 cells immediately around us
            for (int j = y-1; j <= y+1; j++) {
                try {
                    if (cellGrid[i][j]) { // if a certain cell is true, means life present
                        life++;
                    }
                } catch (Exception ignored) {  //catch error related to boundaries exception, so program runs smoothly
                }
            }
        }
        if(cellGrid[x][y])  // if cell calling this method is already alive, reduce life
            life--;
        return life;
    }

    public static void main(String[] args) {
        new ConwaysGameOfLife();
    }
}
