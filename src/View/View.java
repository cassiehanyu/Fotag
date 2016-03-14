package View;

import DataHelper.Layout;
import Model.Model;
import Model.IView;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

/**
 * Created by cassiehanyu on 2016-03-04.
 */
public class View extends JPanel{
    private Model model;
    private ArrayList<ImagePanel> imagePanelList;
    private Border empty;

    private int width = 625;
    private int height = 580;

    public View(Model model){
        this.model = model;
        this.imagePanelList = new ArrayList<>();

        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.black);
//        this.setLayout(new FlowLayout(FlowLayout.LEADING,10,10));
        initGridLayout();


//        JLabel label = new JLabel(new ImageIcon("pic/grid.png"));
//        label.setOpaque(true);
//        this.add(label);

        model.addView(new IView() {
            @Override
            public void updateView() {
                if(model.isChangeLayout()){
                    if(model.getCurLayout() == Layout.GRIDLAYOUT){
                        initGridLayout();
                    }else{
                        initListLayout();
                    }
                }

                if(model.isFilterSelected()){
                    View.this.removeAll();
                    ArrayList<Integer> indexes = model.getVisibleImages();
                    for(Integer i : indexes){
                        View.this.add(imagePanelList.get(i));
                        if (model.getCurLayout() == Layout.LISTLAYOUT) {
                            View.this.add(Box.createVerticalStrut(10));
                        }
                    }
                    View.this.validate();
                    View.this.repaint();
                } else {
                    addImages();
                    View.this.validate();
                }
            }
        });

        registerListener();
    }

    private void registerListener(){
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("View width: " + View.this.getWidth() + "   View Height: " + View.this.getHeight());

            }
        });
    }


    private void initGridLayout(){
        this.setLayout(new FlowLayout(FlowLayout.LEADING,10,10));
        this.removeAll();
        imagePanelList.clear();
        empty = BorderFactory.createEmptyBorder(0,0,0,0);
        this.setBorder(empty);
        addImages();
        this.validate();
        this.repaint();
    }

    private void initListLayout(){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.removeAll();
        imagePanelList.clear();
        empty = BorderFactory.createEmptyBorder(10,10,0,10);
        this.setBorder(empty);
        addImages();
        this.validate();
        this.repaint();
    }

    private void addImages(){
        for (int i = imagePanelList.size(); i < model.getTotalImage(); i++) {
            System.out.println("outputing");
            ImagePanel imagePanel = new ImagePanel(model, i);
//                    imagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            imagePanelList.add(imagePanel);
            View.this.add(imagePanel);
            if (model.getCurLayout() == Layout.LISTLAYOUT) {
                View.this.add(Box.createVerticalStrut(10));
            }
        }
    }

}
