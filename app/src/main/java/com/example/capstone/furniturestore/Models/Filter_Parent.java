package com.example.capstone.furniturestore.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tejalpatel on 2018-04-16.
 */

public class Filter_Parent implements Serializable {
    private String parentName;
    private ArrayList<Filter_Child> childDataItems;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public ArrayList<Filter_Child> getChildDataItems() {
        return childDataItems;
    }

    public void setChildDataItems(ArrayList<Filter_Child> childDataItems) {
        this.childDataItems = childDataItems;
    }
}
