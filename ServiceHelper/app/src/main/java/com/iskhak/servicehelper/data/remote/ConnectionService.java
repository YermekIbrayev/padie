package com.iskhak.servicehelper.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iskhak.servicehelper.data.model.PackageModel;
import com.iskhak.servicehelper.data.model.ServiceGroup;
import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.helpers.MyGsonTypeAdapterFactory;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

public interface ConnectionService {

    String ENDPOINT = "http://10.1.10.100:8080/padie/json/";
    String SERVICE_LIST = "serviceList";
    String SEND_ORDERS = "sendOrder";
    String GET_ORDERS_PRICE = "getOrderPrice";

    @GET(SERVICE_LIST)
    Observable<List<ServiceGroup>> getServices();

    @POST(SEND_ORDERS)
    Observable<PackageModel> sendOrder(@Body PackageModel order);

    @POST(GET_ORDERS_PRICE)
    Observable<PackageModel> getOrderPrice(@Body PackageModel order);

    class Creator{

        public static ConnectionService newConnectionService(){
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .setDateFormat(DataHolder.TOTAL_DATE_FORMAT)
                    .create();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(ConnectionService.class);
        }
    }
}
