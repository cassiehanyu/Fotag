package View;

import Model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
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
    private ImageIcon not_ranked;
    private ImageIcon cross;

    private JLabel crossLabel;
    private JLabel notRanked;
    private ArrayList<JLabel> rateFilter;

    public MyToolBar(Model model){
        this.model = model;
        rateFilter= new ArrayList<>(5);

        star_empty = new ImageIcon("pic/star_empty.png");
        star_empty = new ImageIcon(star_empty.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));

        star_full = new ImageIcon("pic/star_full.png");
        star_full = new ImageIcon(star_full.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));

        not_ranked = new ImageIcon("pic/no.png");
        not_ranked = new ImageIcon(not_ranked.getImage().getScaledInstance(27,27,Image.SCALE_SMOOTH));

        cross = new ImageIcon("pic/cross.png");
        cross = new ImageIcon(cross.getImage().getScaledInstance(12,12,Image.SCALE_SMOOTH));

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

//        for(int i = 0; i < 5; i++){
//            rateFilter.add(new JLabel(star_empty));
//        }

        this.add(loadButton);
        this.add(new Separator());

        this.add(gridButton);
        this.add(listButton);

        this.add(Box.createHorizontalGlue());

        notRanked = new JLabel(not_ranked);
//        notRanked.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.add(notRanked);
        for(int i = 0; i < 5; i++){
            rateFilter.add(new JLabel(star_empty));
            this.add(rateFilter.get(i));
        }
        crossLabel = new JLabel(cross);
        crossLabel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        this.add(crossLabel);

        registerListeners();
    }


    private void mouseExit(){
        int j;
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

    private void registerListeners() {
        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                JFileChooser chooser = new JFileChooser();
//                chooser.setCurrentDirectory(new File("./doc"));
//                chooser.setMultiSelectionEnabled(true);
//                int retrival = chooser.showOpenDialog(null);
//                if(retrival == JFileChooser.APPROVE_OPTION) {
                    System.out.println("loading picture");
                    File[] file = new File[]{new File("pic/star_gold.png")};
//                    File[] file = chooser.getSelectedFiles();
                    model.loadImageModel(file);
//                }
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

        crossLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.removeFilter();
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
                    mouseExit();

                }

            });

            notRanked.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    model.setFilterRate(0);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    mouseExit();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    for (int j = 0; j < rateFilter.size(); j++) {
                        rateFilter.get(j).setIcon(star_empty);
                    }
                }
            });
        }
    }
}
