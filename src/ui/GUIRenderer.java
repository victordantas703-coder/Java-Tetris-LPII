package ui;

import java.awt.Component;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import world.Grid;

public class GUIRenderer implements IRenderer {

    private JFrame janela;
    private JPanel painel;

    public GUIRenderer() {
        painel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(Color.WHITE);

                // Area principal do jogo
                g.drawRect(20, 20, 300, 600);

                // Area da proxima peca
                g.drawRect(350, 20, 90, 90);
            }
        };

        painel.setPreferredSize(new Dimension(460, 640));
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
    public void render(Grid grid) {
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


