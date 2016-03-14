package View;

import DataHelper.Layout;
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
    private ImageIcon realImage;
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
//        this.setPreferredSize(new Dimension(195,180));
//        this.setMaximumSize(new Dimension(195,180));

        imageRate = new ArrayList<>(5);

        star_empty = new ImageIcon("pic/star_empty.png");
        star_empty = new ImageIcon(star_empty.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        star_full = new ImageIcon("pic/star_full.png");
        star_full = new ImageIcon(star_full.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));

        realImage = new ImageIcon(model.getImage(index));
        image = new ImageIcon(realImage.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
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

        if(model.getCurLayout() == Layout.GRIDLAYOUT) {
            initGridLayout();
        }else{
            initListLayout();
        }


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
        this.setPreferredSize(new Dimension(195,180));
        this.setMaximumSize(new Dimension(195,180));

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
        this.setPreferredSize(new Dimension(500,150));
        this.setMaximumSize(new Dimension(500,150));

        Box hbox1 = Box.createHorizontalBox();
        Box hbox2 = Box.createHorizontalBox();
        Box vbox = Box.createVerticalBox();
//        vbox.add(Box.createVerticalGlue());

        hbox1.add(Box.createHorizontalStrut(30));
//        hbox1.add(Box.createHorizontalGlue());

        System.out.println(this.getSize().width);
        hbox1.setPreferredSize(new Dimension(500,150));

        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
//        hbox1.setBorder(raisedetched);
        imageLabel.setAlignmentY(CENTER_ALIGNMENT);
        hbox1.add(imageLabel);

        imageName.setAlignmentX(Component.LEFT_ALIGNMENT);
        vbox.add(imageName);
        vbox.add(Box.createVerticalStrut(10));


        imageCreationDate.setAlignmentX(Component.LEFT_ALIGNMENT);
//        vbox.setBorder(raisedetched);
        vbox.add(imageCreationDate);
        vbox.add(Box.createVerticalStrut(10));


        for(JLabel label : imageRate){
            hbox2.add(label);
        }
        hbox2.setAlignmentX(Component.LEFT_ALIGNMENT);
        vbox.add(hbox2);
//        vbox.add(Box.createVerticalGlue());

        hbox1.add(Box.createHorizontalGlue());
        vbox.setAlignmentY(CENTER_ALIGNMENT);
        hbox1.add(vbox);
        hbox1.add(Box.createHorizontalGlue());

        this.add(hbox1);

//        hbox1.setAlignmentY(Component.TOP_ALIGNMENT);

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

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JDialog dialog = new JDialog();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setTitle("Image Loading Demo");

                dialog.add(new JLabel(transformImage(500,500,realImage)));

                dialog.pack();
//                dialog.setLocationByPlatform(true);
                System.out.println(imageLabel.getLocation().getX());
                dialog.setLocation((int) MouseInfo.getPointerInfo().getLocation().getX(),
                        (int) MouseInfo.getPointerInfo().getLocation().getY());
//                dialog.setPreferredSize(new Dimension(400,400));
                dialog.setSize(new Dimension(500,500));
                dialog.setVisible(true);
            }
        });

    }

    private ImageIcon transformImage(int width, int height, ImageIcon before){
        if(before.getIconWidth() < width && before.getIconHeight() < height){
            return before;
        }else if(before.getIconWidth() > before.getIconHeight()){
            double newHeight = (double)width/(double)before.getIconWidth()*(double)getHeight();
            return new ImageIcon(before.getImage().getScaledInstance(width, (int) newHeight, Image.SCALE_SMOOTH));
        }else{
            double newWidth = (double)height/(double)before.getIconHeight()*(double)getWidth();
            return new ImageIcon(before.getImage().getScaledInstance((int)newWidth, height, Image.SCALE_SMOOTH));
        }
    }



}
