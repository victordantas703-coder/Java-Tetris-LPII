package ui;

import java.awt.Component;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

import javax.swing.JFrame;
import javax.swing.JPanel;

import world.Board;

public class GUIRenderer implements IRenderer {

    private JFrame janela;
    private JPanel painel;

    public GUIRenderer() {
        painel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.WHITE);

                g2.setStroke(new BasicStroke(3));

                // Area principal do jogo
                g2.drawRect(20, 20, 300, 600);

                // Area da proxima peca
                g2.drawRect(350, 20, 180, 180);

            }
        };

        painel.setPreferredSize(new Dimension(550, 640)); // Area da parte preta
        painel.setBackground(Color.BLACK);
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


