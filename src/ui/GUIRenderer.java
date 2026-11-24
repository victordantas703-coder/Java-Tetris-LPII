package ui;

import java.awt.Component;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gameobject.Block;
import gameobject.Shape;
import world.Board;

public class GUIRenderer implements IRenderer {

    // Canto superior esquerdo e tamanho da área principal do jogo
    private static final int BOARD_X = 20;
    private static final int BOARD_Y = 20;
    private static final int BOARD_WIDTH = 300;
    private static final int BOARD_HEIGHT = 600;
    private static final int BORDER_GAP = 2; 

    private JFrame janela;
    private JPanel painel;
    private Shape currentShape;

    public GUIRenderer() {
        painel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.WHITE);

                g2.setStroke(new BasicStroke(3));

                // Area principal do jogo, o contorno fica por fora da área onde as peças são desenhadas
                g2.drawRect(BOARD_X - BORDER_GAP, BOARD_Y - BORDER_GAP, BOARD_WIDTH + 2 * BORDER_GAP, BOARD_HEIGHT + 2 * BORDER_GAP);

                // Area da proxima peca
                g2.drawRect(350, 20, 180, 180);

                // Peça atual: a posição dos blocos é relativa ao canto da área do jogo
                if (currentShape != null) {
                    g2.setStroke(new BasicStroke(1));
                    for (Block block : currentShape.blocks) {
                        g2.setColor(block.blockColor);
                        g2.fillRect(BOARD_X + block.getX(), BOARD_Y + block.getY(), Block.BLOCK_SIZE, Block.BLOCK_SIZE);
                        g2.setColor(Color.BLACK);
                        g2.drawRect(BOARD_X + block.getX(), BOARD_Y + block.getY(), Block.BLOCK_SIZE, Block.BLOCK_SIZE);
                    }
                }

            }
        };

        painel.setPreferredSize(new Dimension(550, 640)); // Area da parte preta
        painel.setBackground(Color.BLACK);
    }

    // Define qual peça será desenhada
    public void setCurrentShape(Shape shape) {
        this.currentShape = shape;
    }

    @Override
    public void open() {
        janela = new JFrame("Tetris");

        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false);
        janela.add(painel);
        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }

    @Override
    public void render(Board board) {
        painel.repaint();
    }

    @Override
    public Component getEventSource() {
        return painel;
    }

    @Override
    public void close() {
        if (janela != null) {
            janela.dispose();
        }
    }
}


