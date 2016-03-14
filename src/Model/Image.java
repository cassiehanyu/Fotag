package Model;

import DataHelper.Rate;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cassiehanyu on 2016-03-12.
 */
public class Image implements Serializable{
    private String filePath;
    private String fileName;
    private Rate rate;
    private Date creationDate;

    public Image(String filePath, String name, Rate rate, Date date){
        this.filePath = filePath;
        this.fileName = name;
        this.rate = rate;
        this.creationDate = date;
    }

    public String getFilePath(){
        return filePath;
    }

    public String getFileName(){
        return fileName;
    }

    public String getCreationDate()
    {
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(creationDate);
        return formattedDate;
    }

    public int getRate(){
        return rate.getRate();
    }

    public void setRate(Rate rate){
        this.rate = rate;
    }

}
