package com.iskhak.serviceprovider.data.model;

public class ServiceItem {
    private int id;
    private String name = null;
    private boolean selected = false;

    public ServiceItem(Integer id, String name, boolean selected) {
        super();
        this.id = id;
        this.name = name;
        this.selected = selected;
    }

    public ServiceItem(Integer code, String name){
        this(code, name, false);
    }

    public Integer getID() {
        return id;
    }

    public void setID(Integer code) {
        this.id = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
