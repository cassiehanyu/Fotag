package View;

import DataHelper.Rate;
import Model.Model;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by cassiehanyu on 2016-03-12.
 */
public class ImagePanel extends JPanel{
    private Model model;
    private int index;

    private ImageIcon star_empty;
    private ImageIcon star_full;
    private ImageIcon image;
    private JLabel imageLabel;
    private JLabel imageName;
    private JLabel imageCreationDate;
    private ArrayList<JLabel> imageRate;

    private Border raisedetched;
    private Border empty;
    private Border compound;

    private int i;

    public ImagePanel(Model model, int index){
        this.model = model;
        this.index = index;
        this.setPreferredSize(new Dimension(195,180));
        this.setMaximumSize(new Dimension(195,180));

        imageRate = new ArrayList<>(5);

        star_empty = new ImageIcon("pic/star_empty.png");
        star_empty = new ImageIcon(star_empty.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        star_full = new ImageIcon("pic/star_full.png");
        star_full = new ImageIcon(star_full.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));

        image = new ImageIcon(model.getImage(index));
        image = new ImageIcon(image.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
        imageLabel = new JLabel(image);

        String image_name = model.getImageName(index);
        imageName = new JLabel(image_name);

        String image_creation_date = model.getImageCreationDate(index);
        imageCreationDate = new JLabel(image_creation_date);

        int rate = model.getImageRate(index);
        for(int i = 0; i < rate; i++){
            imageRate.add(new JLabel(star_full));
        }
        for(int i = rate; i < 5; i++){
            imageRate.add(new JLabel(star_empty));
        }

        initGridLayout();

//        Box vbox = Box.createVerticalBox();
//        Box hbox = Box.createHorizontalBox();
//
//        vbox.add(imageLabel);
//        vbox.add(imageName);
//        vbox.add(imageCreationDate);
//        hbox.add(Box.createHorizontalGlue());
//        for(JLabel label : imageRate){
//            hbox.add(label);
//        }
//        hbox.add(Box.createHorizontalGlue());
//        vbox.add(hbox);
//        this.add(vbox);


//        this.setBackground(new Color(50,50,50,64));
        empty = BorderFactory.createEmptyBorder(20,20,20,20);
        raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        compound = BorderFactory.createCompoundBorder(raisedetched,empty);
//        this.setBorder(compound);

        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.setOpaque(true);

        registerListener();


    }

    private void initGridLayout(){
        Box vbox = Box.createVerticalBox();
        Box hbox = Box.createHorizontalBox();

        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        vbox.add(imageLabel);
        imageName.setAlignmentX(Component.CENTER_ALIGNMENT);
        vbox.add(imageName);

        imageCreationDate.setAlignmentX(Component.CENTER_ALIGNMENT);
        vbox.add(imageCreationDate);

//        hbox.add(Box.createHorizontalGlue());
        for(JLabel label : imageRate){
            hbox.add(label);
        }
//        hbox.add(Box.createHorizontalGlue());
        hbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        vbox.add(hbox);
        this.add(vbox);
    }

    private void initListLayout(){

    }

    private void registerListener(){
        for(JLabel rate : imageRate){
            rate.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    int j;
                    JLabel curLabel = (JLabel) e.getSource();
                    for(j = 0; j < imageRate.size(); j++){
                        imageRate.get(j).setIcon(star_full);
                        if(imageRate.get(j) == curLabel) {
                            break;
                        }
                    }
                    for(j++ ; j < imageRate.size();j++){
                        imageRate.get(j).setIcon(star_empty);
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    int j;
                    JLabel curLabel = (JLabel) e.getSource();
                    for(j = 0; j < imageRate.size(); j++){
//                        imageRate.get(j).setIcon(star_full);

                        if(imageRate.get(j) == curLabel) {
                            break;
                        }
                    }
                    model.setRating(index,j+1);

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    int j;
//                    JLabel curLabel = (JLabel) e.getSource();
                    int rate = model.getImageRate(index);
                    for(j=0 ; j < imageRate.size();j++){
                        imageRate.get(j).setIcon(star_empty);
                    }
                    for(j = 0; j < rate; j++){
                        imageRate.get(j).setIcon(star_full);
//                        if(imageRate.get(j) == curLabel) {
//                            break;
//                        }
                    }
                    for(j = rate ; j < imageRate.size();j++){
                        imageRate.get(j).setIcon(star_empty);
                    }

                }

            });
        }


    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        g.drawImage(image,0,0,null);
//
//    }
}
