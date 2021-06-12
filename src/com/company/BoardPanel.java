package com.company;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;


public class BoardPanel extends JPanel{
    private final HashMap<BoardTile, Color> colorMapping = new HashMap<>() {{
        put(BoardTile.EMPTY, Color.BLACK);
        put(BoardTile.SNAKE, Color.CYAN);
        put(BoardTile.SNAKE_HEAD, Color.ORANGE);
        put(BoardTile.ENEMY_SNAKE, Color.MAGENTA);
        put(BoardTile.ENEMY_SNAKE_HEAD, Color.PINK);
        put(BoardTile.OBSTACLE, Color.WHITE);
        put(BoardTile.FRUIT, Color.GREEN);
        put(BoardTile.FROG, Color.YELLOW);
    }};
    boolean drawGrid;
    private Board currentBoard;

    public BoardPanel(boolean drawGrid) {
        this.drawGrid = drawGrid;
        setVisible(true);
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public void setCurrentBoard(Board board) {
        this.currentBoard = board;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        BoardTile[][] tiles = this.currentBoard.toTiles();
        g2d.setColor(colorMapping.get(BoardTile.EMPTY));
        Rectangle2D.Double background = new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight());
        g2d.fill(background);
        g2d.draw(background);
        double tileWidth = this.getWidth()/(double) currentBoard.getWidth();
        double tileHeight = (double) this.getHeight()/currentBoard.getHeight();
        for (int i = 0; i < currentBoard.getWidth(); i++) {
            for (int j = 0; j < currentBoard.getHeight(); j++) {
                RectangularShape tile;
                if (tiles[i][j] == BoardTile.FRUIT) {
                    tile = new RoundRectangle2D.Double(i*tileWidth+0.1*tileWidth, j*tileHeight+0.05*tileHeight,
                            0.8*tileWidth, 0.8*tileHeight, tileWidth, tileHeight);
                }
                else {
                    tile = new Rectangle2D.Double(i*tileWidth, j*tileHeight, tileWidth, tileHeight);
                }
                g2d.setColor(colorMapping.get(tiles[i][j]));
                g2d.fill(tile);
                g2d.draw(tile);
            }
        }
        if (drawGrid)
        {
            g2d.setColor(colorMapping.get(BoardTile.EMPTY));
            for (int i = 0; i < currentBoard.getWidth(); i++) {
                g2d.drawLine(0, (int)(i*tileHeight), this.getWidth(), (int)(i*tileHeight));
            }
            for (int j = 0; j < currentBoard.getHeight(); j++) {
                g2d.drawLine((int)(j*tileWidth), 0, (int)(j*tileWidth), this.getHeight());
            }
        }
        g2d.dispose();
    }
}