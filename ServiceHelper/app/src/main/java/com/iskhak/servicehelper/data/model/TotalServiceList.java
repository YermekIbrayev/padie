package com.iskhak.servicehelper.data.model;

import com.iskhak.servicehelper.data.model.ServiceGroup;
import com.iskhak.servicehelper.data.model.dbServiceItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalServiceList {
    private Map<Integer, ServiceGroup> serviceGroups = new HashMap<>();
    private List<dbServiceItem> mainQuestions = new ArrayList<>();
    private List<dbServiceItem> extraQuestions = new ArrayList<>();

    public TotalServiceList(Map<Integer, ServiceGroup> serviceGroups, List<dbServiceItem> mainQuestions, List<dbServiceItem> extraQuestions){
        this.serviceGroups = serviceGroups;
        this.mainQuestions = mainQuestions;
        this.extraQuestions = extraQuestions;
    }

    public Map<Integer,ServiceGroup> getServiceGroups() {
        return serviceGroups;
    }

    public void setServiceGroups(Map<Integer, ServiceGroup> serviceGroups) {
        this.serviceGroups = serviceGroups;
    }

    public List<dbServiceItem> getMainQuestions() {
        return mainQuestions;
    }

    public void setMainQuestions(ArrayList<dbServiceItem> mainQuestions) {
        this.mainQuestions = mainQuestions;
    }

    public List<dbServiceItem> getExtraQuestions() {
        return extraQuestions;
    }

    public void setExtraQuestions(ArrayList<dbServiceItem> extraQuestions) {
        this.extraQuestions = extraQuestions;
    }
}
