package com.iskhak.serviceprovider.helpers;

import android.content.Context;

import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.data.model.TokenModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataHolder {

    private Map<Integer, PackageModel> orders;
    private Map<Integer, PackageModel> indexedOrders;
    private INewOrderSender sender;
    private TokenModel token;

    private UserPreferences userPreferences;

    private boolean isRunning;

    public static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance(){
        return holder;
    }

    public DataHolder(){
/*        fillRequestsList();*/
    }

    public void setContext(Context context){
        userPreferences = new UserPreferences(context);
    }

    public UserPreferences getUserPreferences(){
        return userPreferences;
    }

    //for testing
    private void fillRequestsList(){
/*        int NEW_DATA_COUNT=2;
        totalRequestsList = new ArrayList<>();
        for(int i=0;i<NEW_DATA_COUNT; i++) {
            RequestElement request = new RequestElement();
            try {
                request.setOrderDate(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse("22/11/2016 22:10:15"));
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                request.setOrderDate(new Date());
            }
            request.setServiceName("Maid Service");
            request.setName("Cecilia Chapman");
            request.setAddress("711-2880 Nulla, St.Mankato Mississippi, 96522, (257) 563-7401");
            request.setNotes("I have a dog");
            request.setPrice(100);
            totalRequestsList.add(request);
        }*/
    }
    public TokenModel getToken(){
        return token;
    }
    public void setToken(TokenModel token){
        this.token = token;
    }

    public boolean isRunning(){
        return isRunning;
    }

    public void stopActivity(){
        isRunning = false;
    }

    public void startActivity(){
        isRunning = true;
    }





    public void updateOrders(int id, PackageModel order) {
        if(orders==null)
            orders = new HashMap<>();
        orders.put(id,order);
        if(indexedOrders==null)
            indexedOrders = new HashMap<>();
        indexedOrders.put(order.id(),order);
    }

    public PackageModel getOrderByPosition(int position){
        return orders.get(position);
    }

    public PackageModel getOrderById(int id){
        if(indexedOrders!=null&&indexedOrders.containsKey(id))
            return indexedOrders.get(id);
        return null;
    }

    public void setIndexedOrders(Map<Integer, PackageModel> indexedOrders){
        this.indexedOrders =indexedOrders;
    }

    public void setSender(INewOrderSender sender){
        this.sender = sender;
    }

    public INewOrderSender getSender(){
        return sender;
    }

    public void clearOrders(){
        if(orders!=null)
            orders.clear();
        if(indexedOrders!=null)
            indexedOrders.clear();
    }
}
