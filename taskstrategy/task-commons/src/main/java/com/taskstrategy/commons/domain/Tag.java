package com.taskstrategy.commons.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 10/14/13
 * Time: 7:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class Tag extends BaseDomain {

    @NotNull(message = "Task Tag name cannot be empty.", groups = Default.class)
    @Size(min = 1, max = 20, message = "Task Tag name cannot be empty and must be less than 20 characters.", groups = Default.class)
    private String name;
    @NotNull(message = "Task Tag user id cannot be empty.", groups = Default.class)
    @Size(min = 1, message = "Task Tag user id cannot be empty.", groups = Default.class)
    private String userId;
    private boolean favorite;
    private String displayColor;

    public Tag() {
        displayColor = "#000000";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getDisplayColor() {
        return displayColor;
    }

    public void setDisplayColor(String displayColor) {
        this.displayColor = displayColor;
    }

}
