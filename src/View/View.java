package View;

import DataHelper.Layout;
import Model.Model;
import Model.IView;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileCacheImageInputStream;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
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

    private int imageDisplayed;

    public View(Model model){
        this.model = model;
        this.imagePanelList = new ArrayList<>();

        this.setPreferredSize(new Dimension(width,height));
//        this.setBackground(new Color(237,119,119));
        this.setBackground(new Color(50,66,102));
//        this.setOpaque(false);
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
                        if(i >= imagePanelList.size()){
                            ImagePanel imagePanel = new ImagePanel(model, i);
                            imagePanelList.add(imagePanel);
                        }
                        View.this.add(imagePanelList.get(i));

                        if (model.getCurLayout() == Layout.LISTLAYOUT) {
                            View.this.add(Box.createVerticalStrut(10));
                        }
                    }
                    View.this.setPreferredSize(getViewPreferredSize(indexes.size()));
                    imageDisplayed = indexes.size();
                    View.this.repaint();
                    View.this.updateUI();
                }else if(model.isFilterRemoved() && !model.isFilterSelected()){
                    View.this.removeAll();
                    for(int i = 0; i < model.getTotalImage(); i++){
                        if(i >= imagePanelList.size()){
                            ImagePanel imagePanel = new ImagePanel(model, i);
                            imagePanelList.add(imagePanel);
                        }
                        View.this.add(imagePanelList.get(i));

                        if (model.getCurLayout() == Layout.LISTLAYOUT) {
                            View.this.add(Box.createVerticalStrut(10));
                        }
                    }
                    View.this.setPreferredSize(getViewPreferredSize(model.getTotalImage()));
                    imageDisplayed = model.getTotalImage();
                    View.this.revalidate();
                    View.this.repaint();
                    View.this.updateUI();
                }
                else {
                    addImages();
                    View.this.setPreferredSize(getViewPreferredSize(imagePanelList.size()));
                    imageDisplayed = imagePanelList.size();
//                    View.this.setPreferredSize(new Dimension(600,900));
                    View.this.revalidate();
//                    View.this.validate();
//                    View.this.invalidate();
                    View.this.repaint();
                    View.this.updateUI();
                }
            }
        });

        registerListener();
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//
//    }

    private void registerListener(){
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("View width: " + View.this.getWidth() + "   View Height: " + View.this.getHeight());
                View.this.setPreferredSize(getViewPreferredSize(View.this.imageDisplayed));
//                View.this.setPreferredSize(getViewPreferredSize(View.this.imagePanelList.size()));

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
        this.setPreferredSize(getViewPreferredSize(model.getTotalImage()));
        imageDisplayed = model.getTotalImage();
        this.revalidate();
        this.repaint();
    }

    private void initListLayout(){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.removeAll();
        imagePanelList.clear();
        empty = BorderFactory.createEmptyBorder(10,10,0,10);
        this.setBorder(empty);
        addImages();
        this.setPreferredSize(getViewPreferredSize(model.getTotalImage()));
        imageDisplayed = model.getTotalImage();
        this.revalidate();
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

    private Dimension getViewPreferredSize(int totalItem){
        int width,height;
        if(getParent()!=null){
            width = getParent().getWidth()-10;
        }else{
            width = this.width;
        }
//        int height = getParent().getHeight()-10;
        int newWidth, newHeight;
        int panelWidth, panelHeight;
        double item;
        int itemInRow;
        if(model.getCurLayout() == Layout.GRIDLAYOUT){
            panelWidth = 195 + 10;
            panelHeight = 180 + 10;

            item= (double)width/(double)panelWidth;
            itemInRow = (int) Math.floor(item);
//            if(item - itemInRow > 0.5){
//                itemInRow++;
//            }

            newWidth = (itemInRow * panelWidth + 10);
            newWidth = (newWidth < this.width)? this.width : newWidth;

            newHeight = (int) Math.ceil((double)totalItem/(double) itemInRow) * panelHeight + 10;
            newHeight = (newHeight < this.height)? this.height : newHeight;
        }else{
            panelWidth = 500+10;
            panelHeight = 180+10;
            newWidth = this.width;
            newHeight = panelHeight * totalItem + 10;
        }
        int test = this.getWidth();
        int test2 = this.getHeight();
        return new Dimension(newWidth, newHeight);
    }

}
