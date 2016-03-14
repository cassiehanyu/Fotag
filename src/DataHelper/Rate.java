package DataHelper;

import java.io.Serializable;

/**
 * Created by cassiehanyu on 2016-03-12.
 */
public enum Rate implements Serializable {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    Rate(int value){
        this.value = value;
    }

    private int value;

    public int getRate()

    {
        return value;
    }

    public static Rate fromInt(int i){
        for(Rate rate : Rate.values()){
            if(rate.getRate() == i){
                return rate;
            }
        }
        return null;
    }

}
