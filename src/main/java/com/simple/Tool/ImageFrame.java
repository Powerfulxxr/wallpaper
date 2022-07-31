package com.simple.Tool;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class ImageFrame extends JFrame {

    private LinkedList<BufferedImage> images;

    private int switchSeconds;
    private int index = -1;

    public ImageFrame(LinkedList<BufferedImage> images,int switchSeconds) throws HeadlessException {
        this.images = images;
        this.switchSeconds = switchSeconds;
    }

    private void initImageFrame() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(size.width, size.height);
        setTitle("BG_FRAME");
        setUndecorated(true);// 无边框
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

 

    @Override
    public void paint(Graphics g) {
        if (index < 0) {
            index = 0;
        }
        BufferedImage image = images.get(index);
        ImageIcon imageIcon = new ImageIcon(image);
        imageIcon.setImage(image.getScaledInstance(this.getWidth(), this.getHeight(),
                Image.SCALE_AREA_AVERAGING));
        g.drawImage(imageIcon.getImage(), 0, 0, null);
        g.dispose();
    }

    public void run() {
        initImageFrame();
        render();
    }

    private void render() {
        while (true) {
            try {
                Thread.sleep(switchSeconds * 1000);
                index = (index + 1) % images.size();
                repaint();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
