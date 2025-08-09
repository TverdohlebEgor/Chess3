package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoardTileView extends JPanel {
    private BufferedImage image;

    public BoardTileView(Color bgColor) {
        this.setBackground(bgColor);
    }

    public void setPiece(String imagePath){
        try {
            if (imagePath != null) {
                this.image = ImageIO.read(new File(imagePath));
            }
        } catch (IOException ex) {
            System.err.println("Error loading image from: " + imagePath);
            this.image = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            Image scaledImage = image.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);

            g.drawImage(scaledImage, 0, 0, this);
        }
    }
}
