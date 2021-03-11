package com.highway;

/*
The MIT License (MIT)

Copyright (c) 2015 Aditya Chatterjee

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URI;

public class Track extends JFrame implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Track.class);

    //Colors
    private static final Color BACKGROUND_COLOR = Color.decode("#4CAF50");
    private static final Color COLOR_BOUNDARY = Color.decode("#D32F2F");

    //Fonts
    private static final Font FONT_TITLE = new Font("Trebuchet MS", Font.BOLD, 14);
    private static final Font FONT_NORMAL = new Font("Trebuchet MS", Font.PLAIN, 12);

    private static final int KERB_WIDTH = 20;
    private static final int KERB_HEIGHT = 40;
    private static final int LANE_DIVIDER_WIDTH = 5;
    private static final int LANE_DIVIDER_HEIGHT = 25;

    private int x;
    private int y;
    private int xDirection;
    private int yDirection;

    private Rectangle playerCar = new Rectangle(x, y, 20, 50);
    private Rectangle opponentOne = new Rectangle(100, 380, 20, 30);
    private Rectangle opponentTwo = new Rectangle(300, 350, 30, 10);

    private Rectangle road = new Rectangle(65, 0, 285, 500);
    private Rectangle leftBoundary = new Rectangle(45, 20, 20, 500);
    private Rectangle rightBoundary = new Rectangle(350, 20, 20, 500);

    private Rectangle rightKerbOne = new Rectangle(45, 90, KERB_WIDTH, KERB_HEIGHT);
    private Rectangle rightKerbTwo = new Rectangle(350, 90, KERB_WIDTH, KERB_HEIGHT);
    private Rectangle rightKerbThree = new Rectangle(45, 200, KERB_WIDTH, KERB_HEIGHT);

    private Rectangle leftKerbOne = new Rectangle(350, 200, KERB_WIDTH, KERB_HEIGHT);
    private Rectangle leftKerbTwo = new Rectangle(45, 300, KERB_WIDTH, KERB_HEIGHT);
    private Rectangle leftKerbThree = new Rectangle(350, 300, KERB_WIDTH, KERB_HEIGHT);

    private int sd = 0;
    private int s1 = -30;
    private int s2 = 0;
    private int w = 0;

    private Track() {
        setBackground(BACKGROUND_COLOR);
        addKeyListener(new AL());
        setTitle("Endless");
        setSize(510, 400);
        setIconImage(new ImageIcon(Track.class.getResource("/icon.png")).getImage());
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        playerCar.x = 200;
        playerCar.y = 350;
    }

    private void move() {
        int cv = 2;
        int cd = 2;

        if (w % 13 == 0) {
            cv = 5;
        } else if (w % 17 == 0) {
            cv = 2;
        }

        if (w % 11 == 0) {
            cd = 5;
        } else if (w % 17 == 0) {
            cd = 2;
        } else if (w % 100 == 0) {
            cd = 15;
        }

        playerCar.x += xDirection;
        playerCar.y += yDirection;

        opponentOne.y += cd;
        opponentTwo.y += cv;
        sd += yDirection;
        s1 += cd;
        s2 += cv;
        w++;

        if (yDirection == -3) {
            rightKerbOne.y -= yDirection;
            rightKerbThree.y -= yDirection;
            leftKerbTwo.y -= yDirection;
            rightKerbTwo.y -= yDirection;
            leftKerbOne.y -= yDirection;
            leftKerbThree.y -= yDirection;
        }

        playerCar = setXYToCar(playerCar, 150);
        opponentOne = setXYToCar(opponentOne, 140);
        opponentTwo = setXYToCar(opponentTwo, 155);

        if (rightKerbOne.y >= 350) {
            rightKerbOne.y = 50;
        }
        if (rightKerbThree.y >= 350) {
            rightKerbThree.y = 50;
        }
        if (leftKerbTwo.y >= 350) {
            leftKerbTwo.y = 50;
        }
        if (rightKerbTwo.y >= 350) {
            rightKerbTwo.y = 50;
        }
        if (leftKerbOne.y >= 350) {
            leftKerbOne.y = 50;
        }
        if (leftKerbThree.y >= 350) {
            leftKerbThree.y = 50;
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                move();
                Thread.sleep(20);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private class AL extends KeyAdapter {

        private void setXDirection(int xdir) {
            xDirection = xdir;
        }

        private void setYDirection(int ydir) {
            yDirection = ydir;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT) {
                setXDirection(-3);
            }
            if (keyCode == KeyEvent.VK_RIGHT) {
                setXDirection(+3);
            }
            if (keyCode == KeyEvent.VK_UP) {
                setYDirection(-3);
            }
            if (keyCode == KeyEvent.VK_DOWN) {
                setYDirection(+3);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT) {
                setXDirection(0);
            }
            if (keyCode == KeyEvent.VK_RIGHT) {
                setXDirection(0);
            }
            if (keyCode == KeyEvent.VK_UP) {
                setYDirection(0);
            }
            if (keyCode == KeyEvent.VK_DOWN) {
                setYDirection(0);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        Image image = createImage(getWidth(), getHeight());
        Graphics dbg = image.getGraphics();
        paintComponent(dbg);
        g.drawImage(image, 0, 0, this);
    }

    private void paintComponent(Graphics g) {
        drawBoundary(g);
        drawRoad(g);
        drawLaneDividers(g);
        drawAllKerbs(g);
        drawAllCars(g);

        drawTravelledDistance(g);
        drawRank(g);

        repaint();
    }

    private void drawRoad(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(road.x, road.y, road.width, road.height);
    }

    private void drawBoundary(Graphics g) {
        //Left boundary
        g.setColor(COLOR_BOUNDARY);
        g.fillRect(leftBoundary.x, leftBoundary.y, leftBoundary.width, leftBoundary.height);
        //Right boundary
        g.setColor(COLOR_BOUNDARY);
        g.fillRect(rightBoundary.x, rightBoundary.y, rightBoundary.width, rightBoundary.height);
    }

    private void drawRank(Graphics g) {
        g.setFont(FONT_TITLE);
        g.drawString("Rank", 380, 250);

        g.setFont(FONT_NORMAL);
        //First place
        int m = Math.max(s1, Math.max(s2, -sd));
        g.drawString("1° " + getNameFromDriver(m), 380, 270);
        //Second place
        int m1 = Math.min(s1, Math.min(s2, -sd));
        g.drawString("3° " + getNameFromDriver(m1), 380, 310);
        //Third place
        m = s1 + s2 - sd - m - m1;
        g.drawString("2° " + getNameFromDriver(m), 380, 290);
    }

    private void drawTravelledDistance(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(FONT_TITLE);
        g.drawString("Distance travelled", 380, 170);

        g.setFont(FONT_NORMAL);
        g.drawString("Me:         " + (-sd), 380, 190);
        g.drawString("Ayrton Penna: " + (s1), 380, 210);
        g.drawString("Rubens Chinello:  " + (s2), 380, 230);
    }

    private void drawLaneDividers(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(rightKerbOne.x + 170, rightKerbOne.y, LANE_DIVIDER_WIDTH, LANE_DIVIDER_HEIGHT);
        g.fillRect(leftKerbTwo.x + 170, leftKerbTwo.y, LANE_DIVIDER_WIDTH, LANE_DIVIDER_HEIGHT);
        g.fillRect(rightKerbThree.x + 170, rightKerbThree.y, LANE_DIVIDER_WIDTH, LANE_DIVIDER_HEIGHT);
    }

    private void drawAllCars(Graphics g) {
        drawPlayerCar(g);
        drawOpponentOne(g);
        drawOpponentTwo(g);
    }

    private void drawAllKerbs(Graphics g) {
        //Kerbs
        g.setColor(Color.WHITE);
        drawKerb(g, rightKerbOne);
        drawKerb(g, leftKerbOne);

        drawKerb(g, rightKerbTwo);
        drawKerb(g, leftKerbTwo);

        drawKerb(g, rightKerbThree);
        drawKerb(g, leftKerbThree);
    }

    private void drawKerb(Graphics g, Rectangle kerb) {
        g.fillRect(kerb.x, kerb.y, kerb.width, kerb.height);
    }

    private void drawPlayerCar(Graphics g) {
        drawCar("blue_car.png", playerCar.x, playerCar.y, g);
    }

    private void drawOpponentOne(Graphics g) {
        drawCar("green_car.png", opponentOne.x, playerCar.y - s1 - sd, g);
    }

    private void drawOpponentTwo(Graphics g) {
        drawCar("yellow_truck.png", opponentTwo.x, playerCar.y - s2 - sd, g);
    }

    private void drawCar(String carName, int x, int y, Graphics graphics) {
        try {
            URI uri = Track.class.getClassLoader().getResource(carName).toURI();

            File pathToFile = new File(uri);
            Image car = ImageIO.read(pathToFile);
            graphics.drawImage(car, x, y, null);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    private String getNameFromDriver(int distanceTravelled) {
        if (s1 - distanceTravelled == 0) {
            return "Ayrton Penna";
        } else if (s2 - distanceTravelled == 0) {
            return "Rubens Chinello";
        } else {
            return "Me";
        }
    }

    private Rectangle setXYToCar(Rectangle car, int distanceY) {
        if (car.x <= 50) {
            car.x = 50;
        }
        if (car.x >= 350) {
            car.x = 350;
        }
        if (car.y <= distanceY) {
            car.y = distanceY;
        }
        if (car.y > 380) {
            car.y = 380;
        }

        return car;
    }

    public static void main(String[] args) {
        int result = JOptionPane.showConfirmDialog(null, "Be ready to race your car", "Endless", JOptionPane.OK_CANCEL_OPTION);

        // Result equals OK
        if (result == 0) {
            Track track = new Track();
            Thread thread = new Thread(track);
            thread.start();
        } else {
            System.exit(0);
        }
    }

}
