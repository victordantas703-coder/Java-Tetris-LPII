package ui;

import java.awt.Component;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awl.RenderingHint;

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
    private Board board;

    private JFrame janela;
    private JPanel painel;
    private Shape currentShape;
    private int score, levels, lines;

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

                // Grade e blocos já travados
                g2.setStroke(new BasicStroke(1));
                g2.setColor(new Color(40, 40, 40));
                for (int col = 0; col <= Board.BOARD_WIDTH; col++) {
                    int x = BOARD_X + col * Block.BLOCK_SIZE;
                    g2.drawLine(x, BOARD_Y, x, BOARD_Y + BOARD_HEIGHT);
                }
                for (int row = 0; row <= Board.BOARD_HEIGHT; row++) {
                    int y = BOARD_Y + row * Block.BLOCK_SIZE;
                    g2.drawLine(BOARD_X, y, BOARD_X + BOARD_WIDTH, y);
                }
                if (board != null) {
                    for (int row = 0; row < Board.BOARD_HEIGHT; row++) {
                        for (int col = 0; col < Board.BOARD_WIDTH; col++) {
                            Color color = board.getCell(row, col);
                            if (color != null) {
                                drawBlock(g2, col * Block.BLOCK_SIZE, row * Block.BLOCK_SIZE, color);
                            }
                        }
                    }
                }
                g2.setStroke(new BasicStroke(3));   // volta ao traço grosso das caixas
                g2.setColor(Color.WHITE);
                
                // Area da proxima peca
                g2.drawRect(350, 20, 180, 180);
                g2.setRenderingHint( RenderingHint.KEY_TEXT_ANTIALIASING , RenderingHint.VALUE_TEXT_ANTIALIASING_ON);

                g2.setColor(Color.White);
                g2.setFont(new Font("Monospaced", Font.BOLD, 18));

                drawStat(g2, "SCORE", String.format("%06d", score), 350, 240);
                drawStat(g2, "LEVEL", String.valueOf(level), 350, 310);
                drawStat(g2, "LINES", String.valueOf(lines), 350, 380);

                // Peça atual: a posição dos blocos é relativa ao canto da área do jogo
                if (currentShape != null) {
                    for (Block block : currentShape.blocks) {
                        drawBlock(g2, block.getX(), block.getY(), block.blockColor);
                    }
                }

            }
        };

        painel.setPreferredSize(new Dimension(550, 640)); // Area da parte preta
        painel.setBackground(Color.BLACK);
    }

    private void drawStat(Graphics2D g2, String label, String value, int x, int y) {
        g2.setColor(Color.GRAY);
        g2.drawString(label, x, y);          // rótulo cinza
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.BOLD, 28));
        g2.drawString(value, x, y + 30);     // valor grande embaixo
        g2.setFont(new Font("Monospaced", Font.BOLD, 18)); // volta à fonte do rótulo
    }

    // x e y são relativos ao canto da área do jogo
    private void drawBlock(Graphics2D g2, int x, int y, Color color) {
        g2.setStroke(new BasicStroke(1));
        g2.setColor(color);
        g2.fillRect(BOARD_X + x, BOARD_Y + y, Block.BLOCK_SIZE, Block.BLOCK_SIZE);
        g2.setColor(Color.BLACK);
        g2.drawRect(BOARD_X + x, BOARD_Y + y, Block.BLOCK_SIZE, Block.BLOCK_SIZE);
    }

    // Define qual peça será desenhada
    public void setCurrentShape(Shape shape) {
        this.currentShape = shape;
    }

    public void setStats(int score, int level, int lines){
        this.score = score;
        this.level = level;
        this.lines = lines;

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
        this.board = board;
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



