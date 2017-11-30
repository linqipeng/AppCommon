package xyz.no21.appcommon;

import android.support.annotation.Keep;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cookie on 2017/10/7.
 * Email:l437943145@gmail.com
 * Desc
 */
@Keep
public class CommonItemBean {
    @Keep
    @SerializedName(value = "name")
    private String name;
    @Keep
    private long id;

    public CommonItemBean() {
    }

    public CommonItemBean(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
