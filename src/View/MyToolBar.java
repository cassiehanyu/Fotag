package View;

import Model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by cassiehanyu on 2016-03-04.
 */
public class MyToolBar extends JToolBar{
    private Model model;
    private JButton loadButton;
    private JToggleButton gridButton, listButton;
    private ImageIcon loadImage, gridImage, listImage;
    private ImageIcon star_empty;
    private ImageIcon star_full;
    private ArrayList<JLabel> rateFilter;

    public MyToolBar(Model model){
        this.model = model;
        rateFilter= new ArrayList<>(5);

        star_empty = new ImageIcon("pic/star_empty.png");
        star_empty = new ImageIcon(star_empty.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));

        star_full = new ImageIcon("pic/star_full.png");
        star_full = new ImageIcon(star_full.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));

        loadImage = new ImageIcon("pic/load.png");
        loadImage = new ImageIcon(loadImage.getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH));
        loadButton = new JButton(loadImage);

        gridImage = new ImageIcon("pic/grid.png");
        gridImage = new ImageIcon(gridImage.getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH));
        gridButton = new JToggleButton(gridImage);
        gridButton.setSelected(true);

        listImage = new ImageIcon("pic/list.png");
        listImage = new ImageIcon(listImage.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
        listButton = new JToggleButton(listImage);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(gridButton);
        buttonGroup.add(listButton);

        for(int i = 0; i < 5; i++){
            rateFilter.add(new JLabel(star_empty));
        }

        this.add(loadButton);
        this.add(new Separator());

        this.add(gridButton);
        this.add(listButton);

        this.add(Box.createHorizontalGlue());

        for(int i = 0; i < 5; i++){
            rateFilter.add(new JLabel(star_empty));
            this.add(rateFilter.get(i));
        }

        registerListeners();
    }


    private void registerListeners() {
        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("loading picture");
                File[] file = new File[]{new File("pic/star_gold.png")};
                model.loadImageModel(file, "Gold Start", 0);
            }
        });

        gridButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.setCurLayout("Grid");
            }
        });

        listButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.setCurLayout("List");
            }
        });

        for (JLabel rate : rateFilter) {
            rate.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    int j;
                    JLabel curLabel = (JLabel) e.getSource();
                    for (j = 0; j < rateFilter.size(); j++) {
                        rateFilter.get(j).setIcon(star_full);
                        if (rateFilter.get(j) == curLabel) {
                            break;
                        }
                    }
                    for (j++; j < rateFilter.size(); j++) {
                        rateFilter.get(j).setIcon(star_empty);
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    int j;
                    JLabel curLabel = (JLabel) e.getSource();
                    for (j = 0; j < rateFilter.size(); j++) {
//                        rateFilter.get(j).setIcon(star_full);

                        if (rateFilter.get(j) == curLabel) {
                            break;
                        }
                    }
                    model.setFilterRate(j+1);

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    int j;
//                    JLabel curLabel = (JLabel) e.getSource();
                    int rate = model.getFilterRate();
                    for (j = 0; j < rateFilter.size(); j++) {
                        rateFilter.get(j).setIcon(star_empty);
                    }
                    for (j = 0; j < rate; j++) {
                        rateFilter.get(j).setIcon(star_full);
                    }
                    for (j = rate; j < rateFilter.size(); j++) {
                        rateFilter.get(j).setIcon(star_empty);
                    }

                }

            });
        }
    }
}
