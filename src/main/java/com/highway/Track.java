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

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Track Class
 */
public class Track extends JFrame implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Track.class);

    //Colors
    private static final Color BACKGROUND_COLOR = Color.decode("#4CAF50");
    private static final Color OTHER_CAR_ONE = Color.decode("#D50000");
    private static final Color OTHER_CAR_TWO = Color.decode("#00C853");
    private static final Color ME_CAR = Color.decode("#0091EA");

    int x;
    int y;
    int xDirection;
    int yDirection;

    Rectangle myShip = new Rectangle(x, y, 20, 50);
    int yy = 1;

    Rectangle road = new Rectangle(65, 0, 285, 1000);
    Rectangle boundary2 = new Rectangle(45, 20, 20, 500);
    Rectangle boundary3 = new Rectangle(350, 20, 20, 500);

    Rectangle c1 = new Rectangle(100, 380, 20, 30);
    Rectangle c2 = new Rectangle(300, 350, 30, 10);
    Rectangle illu11 = new Rectangle(45, 90, 20, 20);
    Rectangle illu21 = new Rectangle(350, 90, 20, 20);
    Rectangle illu12 = new Rectangle(45, 200, 20, 20);
    Rectangle illu22 = new Rectangle(350, 200, 20, 20);
    Rectangle illu13 = new Rectangle(45, 300, 20, 20);
    Rectangle illu23 = new Rectangle(350, 300, 20, 20);

    int sd = 0;
    int s1 = -30;
    int s2 = 0;
    int w = 0;

    public Track() {
        setBackground(BACKGROUND_COLOR);
        addKeyListener(new AL());
        setTitle("Speed Up");
        setSize(500, 400);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        myShip.x = 200;
        myShip.y = 350;
        yy = 1;
    }

    public void move() {
        int cv = 2;
        int cd = 2;

        if (w % 13 == 0)
            cv = 5;
        else if (w % 17 == 0)
            cv = 2;

        if (w % 11 == 0)
            cd = 5;
        else if (w % 17 == 0)
            cd = 2;
        else if (w % 100 == 0)
            cd = 15;

        myShip.x += xDirection;
        myShip.y += yDirection;

        c1.y += cd;
        c2.y += cv;
        sd += yDirection;
        s1 += cd;
        s2 += cv;
        w++;

        if (yDirection == -3) {
            illu11.y -= yDirection;
            illu12.y -= yDirection;
            illu13.y -= yDirection;
            illu21.y -= yDirection;
            illu22.y -= yDirection;
            illu23.y -= yDirection;
        }
        if (myShip.x <= 50)
            myShip.x = 50;
        if (myShip.x >= 350)
            myShip.x = 350;
        if (myShip.y <= 150)
            myShip.y = 150;
        if (myShip.y > 380)
            myShip.y = 380;

        if (c1.x <= 50)
            c1.x = 50;
        if (c1.x >= 350)
            c1.x = 350;
        if (c1.y <= 140)
            c1.y = 140;
        if (c1.y > 380)
            c1.y = 380;

        if (c2.x <= 50)
            c2.x = 50;
        if (c2.x >= 350)
            c2.x = 350;
        if (c2.y <= 155)
            c2.y = 155;
        if (c2.y > 380)
            c2.y = 380;

        if (illu11.y >= 350)
            illu11.y = 50;
        if (illu12.y >= 350)
            illu12.y = 50;
        if (illu13.y >= 350)
            illu13.y = 50;
        if (illu21.y >= 350)
            illu21.y = 50;
        if (illu22.y >= 350)
            illu22.y = 50;
        if (illu23.y >= 350)
            illu23.y = 50;
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

    public class AL extends KeyAdapter {

        private void setXDirection(int xdir) {
            xDirection = xdir;
        }

        private void setYDirection(int ydir) {
            yDirection = ydir;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT)
                setXDirection(-3);
            if (keyCode == KeyEvent.VK_RIGHT)
                setXDirection(+3);
            if (keyCode == KeyEvent.VK_UP)
                setYDirection(-3);
            if (keyCode == KeyEvent.VK_DOWN)
                setYDirection(+3);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT)
                setXDirection(0);
            if (keyCode == KeyEvent.VK_RIGHT)
                setXDirection(0);
            if (keyCode == KeyEvent.VK_UP)
                setYDirection(0);
            if (keyCode == KeyEvent.VK_DOWN)
                setYDirection(0);
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
        g.setColor(Color.YELLOW);
        g.fillRect(boundary2.x, boundary2.y, boundary2.width, boundary2.height);
        g.fillRect(boundary3.x, boundary3.y, boundary3.width, boundary3.height);

        g.setColor(Color.GRAY);
        g.fillRect(road.x, road.y, road.width, road.height);

        g.setColor(Color.WHITE);
        g.fillRect(illu11.x, illu11.y, illu11.width, illu11.height);
        g.fillRect(illu11.x + 170, illu11.y, 5, 25);
        g.fillRect(illu12.x, illu12.y, illu12.width, illu12.height);
        g.fillRect(illu12.x + 170, illu12.y, 5, 25);
        g.fillRect(illu13.x, illu13.y, illu13.width, illu13.height);
        g.fillRect(illu13.x + 170, illu13.y, 5, 25);
        g.fillRect(illu21.x, illu21.y, illu21.width, illu21.height);
        g.fillRect(illu22.x, illu22.y, illu22.width, illu22.height);
        g.fillRect(illu23.x, illu23.y, illu23.width, illu23.height);

        g.setColor(ME_CAR);
        g.fillRect(myShip.x, myShip.y, 20, 50);

        g.setColor(Color.BLACK);
        g.fillRect(myShip.x + 20, myShip.y + 5, 5, 5);
        g.fillRect(myShip.x - 5, myShip.y + 5, 5, 5);
        g.fillRect(myShip.x + 20, myShip.y + 40, 5, 5);
        g.fillRect(myShip.x - 5, myShip.y + 40, 5, 5);

        g.setColor(OTHER_CAR_ONE);
        g.fillRect(c1.x, myShip.y - s1 - sd, 20, 50);

        g.setColor(Color.BLACK);
        g.fillRect(c1.x + 20, myShip.y - s1 - sd + 5, 5, 5);
        g.fillRect(c1.x - 5, myShip.y - s1 - sd + 5, 5, 5);
        g.fillRect(c1.x + 20, myShip.y - s1 - sd + 40, 5, 5);
        g.fillRect(c1.x - 5, myShip.y - s1 - sd + 40, 5, 5);

        g.setColor(OTHER_CAR_TWO);
        g.fillRect(c2.x, myShip.y - s2 - sd, 20, 50);

        g.setColor(Color.BLACK);
        g.fillRect(c2.x + 20, myShip.y - s2 - sd + 5, 5, 5);
        g.fillRect(c2.x - 5, myShip.y - s2 - sd + 5, 5, 5);
        g.fillRect(c2.x + 20, myShip.y - s2 - sd + 40, 5, 5);
        g.fillRect(c2.x - 5, myShip.y - s2 - sd + 40, 5, 5);

        g.drawString("Car - Dist. travelled", 380, 170);
        String r1 = "Me     " + (-sd);
        g.drawString(r1, 380, 190);

        r1 = "C1     " + (s1);
        g.drawString(r1, 380, 210);

        r1 = "C2     " + (s2);
        g.drawString(r1, 380, 230);

        r1 = "Rank";
        g.drawString(r1, 380, 250);

        int m = Math.max(s1, Math.max(s2, -sd));
        String p;

        if (s1 - m == 0)
            p = "C1";
        else if (s2 - m == 0)
            p = "C2";
        else
            p = "Me";

        r1 = "1." + p;
        g.drawString(r1, 380, 270);
        int m1 = Math.min(s1, Math.min(s2, -sd));

        if (s1 - m1 == 0)
            p = "C1";
        else if (s2 - m1 == 0)
            p = "C2";
        else
            p = "Me";

        r1 = "3." + p;
        g.drawString(r1, 380, 310);
        m = s1 + s2 - sd - m - m1;

        if (s1 - m == 0)
            p = "C1";
        else if (s2 - m == 0)
            p = "C2";
        else
            p = "Me";

        r1 = "2." + p;
        g.drawString(r1, 380, 290);
        repaint();
    }

    public static void main(String[] args) {
        String message = "Be Ready to Fly your Truck";
        int result = JOptionPane.showConfirmDialog(null, message, "Dialog", JOptionPane.OK_CANCEL_OPTION);

        // Result equals OK
        if (result == 0) {
            Track su = new Track();
            Thread t1 = new Thread(su);
            t1.start();
        } else {
            // Result not equals OK
            System.exit(0);
        }

    }

}