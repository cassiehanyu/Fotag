package Model;

import DataHelper.Layout;
import DataHelper.Rate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by cassiehanyu on 2016-03-04.
 */
public class Model {
    private ArrayList<IView> views;

    private ArrayList<Image> imageModelList;
    private ArrayList<BufferedImage> imageList;
    private int totalImage;

    Layout curLayout;
    private boolean changeLayout;

    private Rate filterRate;
    private boolean filterSelected;

    private boolean filterRemoved;


    public Model(){
        views = new ArrayList<IView>();
        imageModelList = new ArrayList<>();
        imageList = new ArrayList<>();
        totalImage = 0;
        curLayout = Layout.GRIDLAYOUT;
        changeLayout = false;
        filterRate = Rate.ZERO;
        filterSelected = false;
        filterRemoved = false;
    }

    //region getter setter

    public BufferedImage getImage(int index){
        return imageList.get(index);
    }

    public String getImageName(int index){
        return imageModelList.get(index).getFileName();
    }

    public String getImageCreationDate(int index)
    {
        return imageModelList.get(index).getCreationDate();
    }

    public int getImageRate(int index){
        return imageModelList.get(index).getRate();

    }

    public int getTotalImage(){
        return totalImage;
    }


    public void setRating(int index, int rate){
        imageModelList.get(index).setRate(Rate.fromInt(rate));
        if(filterSelected) {
            updateAllViews();
        }
    }

    public Layout getCurLayout(){
        return curLayout;
    }

    public void setCurLayout(String layout){
        Layout newLayout = Layout.fromString(layout);
        if(curLayout != newLayout){
            changeLayout = true;
        }
        curLayout = newLayout;
        updateAllViews();
        changeLayout = false;

    }

    public boolean isChangeLayout(){
        return changeLayout;
    }

    public int getFilterRate(){
        return filterRate.getRate();
    }

    public void setFilterRate(int rate){
        filterRate = Rate.fromInt(rate);
        filterSelected = true;
        updateAllViews();
    }

    public boolean isFilterSelected()
    {
        return filterSelected;
    }

    public void removeFilter(){
        filterSelected = false;
        filterRemoved = true;
        updateAllViews();
        filterRemoved = false;
    }

    public boolean isFilterRemoved(){
        return filterRemoved;
    }

    public ArrayList<Integer> getVisibleImages(){
        ArrayList<Integer> indexes = new ArrayList<>();
        if(filterRate == Rate.ZERO){
            for (int i = 0; i < imageModelList.size(); i++) {
                if (imageModelList.get(i).getRate() == filterRate.getRate()) {
                    indexes.add(i);
                }
            }
        }else {
            for (int i = 0; i < imageModelList.size(); i++) {
                if (imageModelList.get(i).getRate() >= filterRate.getRate()) {
                    indexes.add(i);
                }
            }
        }
        return indexes;
    }



    //endregion

    public void loadImageModel(File[] files){
        for(File file : files) {
            String test = file.getPath();
            Image imageModel = new Image(file.getPath(), file.getName(), Rate.ZERO, new Date(file.lastModified()));
            imageModelList.add(imageModel);
            loadImage(file);
            totalImage++;
        }
        updateAllViews();
    }

    public void loadImage(File file){
        try {
            BufferedImage image = ImageIO.read(file);
            imageList.add(image);
        }catch (IOException ex){
            ex.printStackTrace();

        }
    }

    public void saveHistory(){
        try {
            FileOutputStream fos = new FileOutputStream("doc/history.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(imageModelList);
            oos.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void loadHistory(){
        try{
            File file = new File("doc/history.ser");
            if(file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
//                if(ois.available() > 0) {
                imageModelList = (ArrayList<Image>) ois.readObject();
//                }
                ois.close();
                for(Image imageModel : imageModelList){
                    loadImage(new File(imageModel.getFilePath()));
                }
                totalImage = imageModelList.size();
                updateAllViews();
            }
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }

    }


    //region MVC
    /** Add a new view of this triangle. */
    public void addView(IView view) {
        this.views.add(view);
        view.updateView();
    }

    /** Remove a view from this triangle. */
    public void removeView(IView view) {
        this.views.remove(view);
    }


    /** Update all the views that are viewing this triangle. */
    private void updateAllViews() {
        for (IView view : this.views) {
            view.updateView();
        }
    }
    //endregion
}
