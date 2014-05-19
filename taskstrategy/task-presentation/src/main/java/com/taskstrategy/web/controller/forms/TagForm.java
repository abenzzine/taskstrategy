package com.taskstrategy.web.controller.forms;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 11/2/13
 * Time: 2:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class TagForm {

    private String name;
    private boolean favorite;
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color != null && !color.contains("#")) {
            this.color = "#" + color;
        } else {
            this.color = color;
        }
    }
}
