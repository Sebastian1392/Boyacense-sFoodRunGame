package views;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import persistense.FileManager;

public class PanelScreenShots extends JPanel{

    private static final long serialVersionUID = 1L;

    public static final String BACKGRAUND_PATH = "src/img/BG.png";
    public static final double TOTAL_COLS = 3;

    private int rows;
    private ArrayList<Image> screenList;
    private int positionImage;
    
    public PanelScreenShots(){
        setOpaque(false);
        setVisible(true);
    }

    private void addButtons(ArrayList<Image> list,ActionListener listener){
        int j = 0;
        for (int i = 0; i < rows; i++) {
            int k = 0;
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.setOpaque(false);
            while(k < TOTAL_COLS && j < list.size()){
                panel.add(new ButtonScreenShots(list.get(j),j,listener));
                k++;
                j++;
            }
            add(panel);
        }
    }

    public void addImagesToPanel(ActionListener listener,ArrayList<Image> screenList){
        removeAll();
        this.screenList = screenList;
        rows = (int)(Math.ceil(screenList.size()/TOTAL_COLS));
        setLayout(new GridLayout(rows,1));
        addButtons(screenList,listener);
        revalidate();
        repaint();
    }

    public Image getImage(int numberImage){
        positionImage = numberImage;
        return screenList.get(positionImage);
    }

    public void changeImage(boolean side){
        if(side){
            positionImage += (positionImage < (screenList.size() - 1))? 1 : 0;
        }else{
            positionImage += (positionImage > 0)? -1 : 0;
        }
    }

    public int getPositionImage() {
        return positionImage;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(FileManager.readImage(BACKGRAUND_PATH),0,0,this.getWidth(), this.getHeight(), this);
        super.paint(g);
    }  
}