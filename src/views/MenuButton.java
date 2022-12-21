package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JButton;

public class MenuButton extends JButton{

    private static final long serialVersionUID = 1L;

    public MenuButton(Color colorBackGraund, Icon text, Dimension size, ActionListener listener, String command) {
        setPreferredSize(size);
        addActionListener(listener);
        setActionCommand(command);
        setBackground(colorBackGraund);
        setIcon(text);
        setOpaque(false);
        setFocusable(false);
        setBorderPainted(false);
        setCursor(Utilities.cursor());
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Dimension arcs = new Dimension(20, 20);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        graphics.setColor(Color.BLACK);
        graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        super.paint(g);
    }
    
}