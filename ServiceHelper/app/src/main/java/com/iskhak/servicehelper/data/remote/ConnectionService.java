package com.iskhak.servicehelper.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iskhak.servicehelper.data.model.LoginInfo;
import com.iskhak.servicehelper.data.model.PackageModel;
import com.iskhak.servicehelper.data.model.Provider;
import com.iskhak.servicehelper.data.model.ServiceGroup;
import com.iskhak.servicehelper.data.model.TokenModel;
import com.iskhak.servicehelper.extra.Constants;
import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.helpers.MyGsonTypeAdapterFactory;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

public interface ConnectionService {

    String ENDPOINT = "http://padie.hopto.org:8080/padie/json/";
    String SERVICE_LIST = "serviceList";
    String SEND_ORDERS = "sendOrder";
    String GET_ORDERS_PRICE = "getOrderPrice";
    String LOGIN_PAGE = "login";
    String REGISTRATION_PAGE = "register";
    String PROVIDERS_PAGE = "providers";

    @GET(SERVICE_LIST)
    Observable<List<ServiceGroup>> getServices();

    @POST(SEND_ORDERS)
    Observable<PackageModel> sendOrder(
            @Header(Constants.TOKEN_HEADER)String token,
            @Body PackageModel order);

    @POST(GET_ORDERS_PRICE)
    Observable<PackageModel> getOrderPrice(
            @Header(Constants.TOKEN_HEADER)String token,
            @Body PackageModel order);

    @POST(LOGIN_PAGE)
    Observable<Response<TokenModel>> login(@Body LoginInfo loginInfo);

    @POST(REGISTRATION_PAGE)
    Observable<Response<TokenModel>> registration(@Body LoginInfo loginInfo);

    @GET(PROVIDERS_PAGE)
    Observable<List<Provider>> getProviders(
            @Header(Constants.TOKEN_HEADER) String token);

    class Creator{

        public static ConnectionService newConnectionService(){
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .setDateFormat(Constants.DATE_TIME_FORMAT)
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
