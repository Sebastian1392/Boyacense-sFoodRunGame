package views;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

public class DialogError extends JDialog{

    private static final long serialVersionUID = 1L;

    public static final String BACKGRAUND_PATH = "src/img/BG.png";
    public static final String LOGO_TEXT_PATH = "src/img/logoText.png";
    public static final int WIDTH_FRAME = 350;
    public static final int HEIGTH_FRAME = 280;

    private PanelError panelError;

    public DialogError(ActionListener listener){
        setUndecorated(true);
        setSize(new Dimension(WIDTH_FRAME,HEIGTH_FRAME));
        setResizable(false);
        setModal(true);
        setAlwaysOnTop(true); 
        setCursor(Utilities.cursor());

        panelError = new PanelError(listener);
        add(panelError);
    }
}