package com.iskhak.servicehelper.helpers;

import android.content.Context;
import android.text.TextUtils;

import com.iskhak.servicehelper.data.model.PackageModel;
import com.iskhak.servicehelper.data.model.Provider;
import com.iskhak.servicehelper.data.model.SelectedItems;
import com.iskhak.servicehelper.data.model.SelectedItemsAdd;
import com.iskhak.servicehelper.data.model.SelectedItemsAddExtra;
import com.iskhak.servicehelper.data.model.ServiceGroup;
import com.iskhak.servicehelper.data.model.ServiceItem;
import com.iskhak.servicehelper.data.model.TokenModel;
import com.iskhak.servicehelper.data.model.TotalServiceList;
import com.iskhak.servicehelper.data.model.dbServiceItem;
import com.iskhak.servicehelper.data.model.SelectedItemsList;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import timber.log.Timber;

public class DataHolder {

    private int mainSelectionPID;
    private String currentServiceName;
    private String mainQuestionText;
    private String extraQuestionText;
    private TotalServiceList serviceList;
    private Map<Integer, ServiceGroup> serviceGroupList;
    private SelectedItemsList selectedItems = new SelectedItemsList();
    private Date orderDate;
    private String orderNote;
    private String orderAddress;
    private List<SelectedItems> selectedItemsList = new ArrayList<>();
    private List<SelectedItemsAdd> selectedItemsAddList = new ArrayList<>();
    private List<SelectedItemsAddExtra> selectedItemsAddExtraList = new ArrayList<>();
    private PackageModel order;
    private TokenModel token;
    private Provider provider;

    private UserPreferences userPreferences;

    private static final DataHolder holder = new DataHolder();

    public static DataHolder getInstance()
    {
        return holder;
    }

    public void setContext(Context context){
        userPreferences = new UserPreferences(context);
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }


    public UserPreferences getUserPreferences(){
        return userPreferences;
    }

    public int getMainSelectionPID() {
        return mainSelectionPID;
    }

    public void setMainSelectionPID(int mainSelectionPID) {
        this.mainSelectionPID = mainSelectionPID;
    }

    public String getCurrentServiceName() {
        return currentServiceName;
    }

    public void setCurrentServiceName(String currentServiceName) {
        this.currentServiceName = currentServiceName;
    }

    public String getMainQuestionText() {
        return mainQuestionText;
    }

    public void setMainQuestionText(String mainQuestionText) {
        this.mainQuestionText = mainQuestionText;
    }

    public String getExtraQuestionText() {
        return extraQuestionText;
    }

    public void setExtraQuestionText(String extraQuestionText) {
        this.extraQuestionText = extraQuestionText;
    }

    public void setOrderProviderId(int providerId){
        order = order.toBuilder().setProviderID(providerId).build();
    }

    public TotalServiceList getServiceList() {
        return serviceList;
    }

    public void addServiceID(int serviceID){
        selectedItems.addServiceName(serviceID);
    }

    public String getSelectedService(){
        if(selectedItemsList!=null&& selectedItemsList.size()>0){
            return serviceList.getServiceGroups().get(selectedItemsList.get(0).serviceID()).name();
        }
        return "";
    }

    public PackageModel generatePackageModel(){
        PackageModel result = PackageModel.builder()
                .setOrderDate(new Timestamp(orderDate.getTime()))
                .setNotes(getOrderNote())
                .setAddress(getOrderAddress())
                .setSelectedItems(selectedItemsList)
                .setSelectedItemsAdd(selectedItemsAddList)
                .setSelectedItemsAddExtra(selectedItemsAddExtraList)
                .build();
        this.order = result;
        return result;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public TokenModel getToken(){
        return token;
    }
    public void setToken(TokenModel token){
        this.token = token;
    }

    public PackageModel getOrder(){
        return order;
    }

    public void setOrder(PackageModel order){
        this.order = order;
    }

    public void setServiceList(Map<Integer, ServiceGroup> serviceGroups){
        serviceGroupList = serviceGroups;
        serviceList = new TotalServiceList(serviceGroups, new ArrayList<dbServiceItem>(), new ArrayList<dbServiceItem>());
    }

    public Map<Integer, ServiceGroup> getServiceGroupNames(){
        return this.getServiceList().getServiceGroups();
    }

    public List<ServiceItem>  getMainSelectionNames(){
        if(serviceGroupList.containsKey(getMainSelectionPID()))
            return getSelectionNames(serviceGroupList.get(getMainSelectionPID()).mainSelections());
        return Collections.emptyList();
    }

    public List<ServiceItem> getExtraSelectionNames(){
        if(serviceGroupList.containsKey(getMainSelectionPID()))
            return getSelectionNames(serviceGroupList.get(getMainSelectionPID()).extraSelections());
        return Collections.emptyList();
    }

    private List<ServiceItem> getSelectionNames(List<dbServiceItem> items){
        List<ServiceItem> result = new ArrayList<>();
        for( dbServiceItem item:items){
            ServiceItem serviceItem = new ServiceItem(item.id(),item.name());
            result.add(serviceItem);
        }
        return result;
    }

    public void addSelectedItems(SelectedItems item){
        selectedItemsList.add(item);
    }

    public void addSelectedItemsAdd(SelectedItemsAdd item){
        selectedItemsAddList.add(item);
    }

    public void addSelectedItemsAddExtra(SelectedItemsAddExtra item){
        selectedItemsAddExtraList.add(item);
    }

    public void clearSelected(){
        selectedItemsList.clear();
        selectedItemsAddList.clear();
        selectedItemsAddExtraList.clear();
    }
}
