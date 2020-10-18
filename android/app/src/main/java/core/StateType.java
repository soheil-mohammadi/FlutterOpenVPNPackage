package core;

import java.io.Serializable;

import androidx.room.TypeConverter;

public enum StateType implements Serializable {

    CONNECTED (10) ,
    CONNECTING (12) ,
    DISCONNECTED (14) ,
    DIS_FROM_ANDROID_SYSTEM(16) ,
    FAILED(18) ,
    NET_PROBLEM (20) ,
    DISCONNECTED_FAILED (22) ,
    GETTING_CONFIG(24);

    private int code;

    StateType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }


    @TypeConverter
    public static StateType getType(Integer numeral){
        for(StateType ds : values()){
            if(ds.code == numeral){
                return ds;
            }
        }
        return null;
    }

    @TypeConverter
    public static Integer getTypeInt(StateType status){

        if(status != null)
            return status.code;

        return  null;
    }

}
