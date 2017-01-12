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
    private Integer providerId;

    private UserPreferences userPreferences;

    private static final DataHolder holder = new DataHolder();

    public static DataHolder getInstance()
    {
        return holder;
    }

    public void setContext(Context context){
        //DataHolder.getInstance().updateData();
        userPreferences = new UserPreferences(context);
    }

    public String getOrderDate() {
        String dateFormat = Constants.DATE_TIME_FORMAT;
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        return sdf.format(orderDate);
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
        Timber.d("Sent providerId:"+providerId);
        Timber.d("ProviderId:"+order.providerID());
        //this.providerId = providerId;
    }

//    public ArrayList<ServiceItem> getMainSelectionNames(){
//        return dbHelper.getMainSelectionNames();
//    }

/*    public ArrayList<ServiceItem> getExtraSelectionNames(){
        return dbHelper.getExtraSelectionNames();
    }*/

/*    public void setDbHelper(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void updateData(){
        dbHelper.updateData();
    }*/

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

/*    public void addMainSelectionID(int mainSelectionID, int mainSelectionPID){
        selectedItems.addMainSelection(mainSelectionID, mainSelectionPID);
    }

    public void addExtraSelectionID(int extraSelectionID, int extraSelectionPID){
        selectedItems.addExtraSelection(extraSelectionID, extraSelectionPID);
    }*/

    private ArrayList<dbServiceItem> getMainSelectionsID(){
        return selectedItems.getMainSelections();
    }

    private ArrayList<dbServiceItem> getExtraSelections(){
        return selectedItems.getExtraSelections();
    }

    private HashSet<Integer> getSelectedServices(){
        return selectedItems.getServicesID();
    }

/*    public void clearSelected(){
        selectedItems.clear();
    }*/

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

/*    public JSONObject generateJSON(){
        JSONObject result = new JSONObject();
        JSONObject order = new JSONObject();
        JSONArray serviceNames = new JSONArray();
        JSONArray mainSelectionNames = new JSONArray();
        JSONArray extraSelectionNames = new JSONArray();
        try {
            order.put("Date", getOrderDate());
            order.put("Notes", getOrderNote());
            order.put("Address",getOrderAddress());
            for(int id:getSelectedServices()){
                JSONObject service = new JSONObject();
                service.put("ID", id);
                serviceNames.put(service);
            }
            for(dbServiceItem serviceItem:getMainSelectionsID()){
                JSONObject mainSelection = new JSONObject();
                mainSelection.put("ID", serviceItem.id());
                mainSelection.put("PID", serviceItem.pid());
                mainSelectionNames.put(mainSelection );
            }
            for(dbServiceItem serviceItem:getExtraSelections()){
                JSONObject extraSelection = new JSONObject();
                extraSelection.put("ID", serviceItem.id());
                extraSelection.put("PID", serviceItem.id());
                extraSelectionNames.put(extraSelection);
            }
            result.put("Order", order);
            result.put("Services", serviceNames);
            result.put("MainSelections", mainSelectionNames);
            result.put("ExtraSelections", extraSelectionNames);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }*/


 /*   public void setServiceListJson(String json){
        this.serviceListJson = json;
        ArrayList<ServiceGroup> serviceGroupsList = new ArrayList<>();
        ArrayList<dbServiceItem> mainSelectionsList;
        ArrayList<dbServiceItem> extraSelectionsList;
        try{
            JSONObject rootJson = new JSONObject(json);
            JSONArray serviceGroups = rootJson.getJSONArray("Services");
            JSONArray mainSelections = rootJson.getJSONArray("MainSelections");
            JSONArray extraSelections = rootJson.getJSONArray("ExtraSelections");

            for(int i = 0;i<serviceGroups.length();i++){
                JSONObject groupItem = serviceGroups.getJSONObject(i);
                int id = Integer.parseInt(groupItem.optString("ID").toString());
                String name = groupItem.optString("Name").toString();
                String mainQuestion = groupItem.optString("MainQuestion").toString();
                String extraQuestion = groupItem.optString("ExtraQuestion").toString();

                Log.d("JSON PARSE", name);

                //ServiceGroup serviceGroup = new ServiceGroup(id, name, mainQuestion, extraQuestion);
                ServiceGroup serviceGroup = ServiceGroup.builder()
                        .setId(id)
                        .setName(name)
                        .setMainQuestion(mainQuestion)
                        .setExtraQuestion(extraQuestion)
                        .build();
                serviceGroupsList.add(serviceGroup);
            }

            mainSelectionsList = fillList(mainSelections);
            extraSelectionsList = fillList(extraSelections);

            this.serviceList = new TotalServiceList(serviceGroupsList,mainSelectionsList,extraSelectionsList);

        }catch (JSONException ex){
            ex.printStackTrace();
        }
    }*/

/*    private ArrayList<dbServiceItem> fillList( JSONArray jsonArray) throws JSONException {
        ArrayList<dbServiceItem> result = new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++){
            JSONObject serviceItem = jsonArray.getJSONObject(i);
            int id = Integer.parseInt(serviceItem .optString("ID").toString());
            int pid = Integer.parseInt(serviceItem .optString("PID").toString());
            String name = serviceItem .optString("Name").toString();

            Log.d("JSON PARSE", name);

            dbServiceItem dbserviceItem = dbServiceItem.builder()
                    .setId(id)
                    .setPid(pid)
                    .setName(name)
                    .build();

                    //new dbServiceItem(id, pid, name);
            result.add(dbserviceItem);
        }
        return result;
    }*/

/*    public int getTotalSum(String json){
        int result=-1;
        try {
            JSONObject jsonObject= new JSONObject(json);
            result = Integer.parseInt(jsonObject .optString("TotalSum").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return result;
    }*/

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

/*    public List<SelectedItems> getSelectedItemsList() {
        return selectedItemsList;
    }

    public List<SelectedItemsAdd> getSelectedItemsAddList() {
        return selectedItemsAddList;
    }

    public List<SelectedItemsAddExtra> getSelectedItemsAddExtraList() {
        return selectedItemsAddExtraList;
    }*/
}
