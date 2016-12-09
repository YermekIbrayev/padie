package com.iskhak.servicehelper.extra;

import com.iskhak.servicehelper.data.model.ServiceGroup;
import com.iskhak.servicehelper.data.model.dbServiceItem;

import java.util.ArrayList;
import java.util.List;

public class TotalServiceList {
    private List<ServiceGroup> serviceGroups = new ArrayList<>();
    private List<dbServiceItem> mainQuestions = new ArrayList<>();
    private List<dbServiceItem> extraQuestions = new ArrayList<>();

    public TotalServiceList(List<ServiceGroup> serviceGroups, List<dbServiceItem> mainQuestions, List<dbServiceItem> extraQuestions){
        this.serviceGroups = serviceGroups;
        this.mainQuestions = mainQuestions;
        this.extraQuestions = extraQuestions;
    }

    public List<ServiceGroup> getServiceGroups() {
        return serviceGroups;
    }

    public void setServiceGroups(ArrayList<ServiceGroup> serviceGroups) {
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
