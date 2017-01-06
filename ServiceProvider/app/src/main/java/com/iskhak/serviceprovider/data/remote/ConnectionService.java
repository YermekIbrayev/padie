package com.iskhak.serviceprovider.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iskhak.serviceprovider.data.model.LoginInfo;
import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.data.model.PathDate;
import com.iskhak.serviceprovider.data.model.ResponseOrder;
import com.iskhak.serviceprovider.data.model.ServiceGroup;
import com.iskhak.serviceprovider.data.model.TokenModel;
import com.iskhak.serviceprovider.helpers.Constants;
import com.iskhak.serviceprovider.helpers.MyGsonTypeAdapterFactory;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface ConnectionService {

    String ENDPOINT = "http://10.1.10.112:8080/padie/json/";
    String NEW_ORDERS = "getNewOrders/{deviceId}/{date}";
    String SEND_ON_ORDERS = "setViewedOrders";
    String ACCEPT_ORDER = "setAccepted/{pkgId}";
    String LOGIN_PAGE = "login";

    @GET(NEW_ORDERS )
    Observable<List<PackageModel>> getNewOrders(@Header(Constants.TOKEN_HEADER)String token,
                                                @Path("deviceId") String deviceId, @Path("date") PathDate viewed);

    @POST(SEND_ON_ORDERS)
    Call<Void> responseOrder(@Header(Constants.TOKEN_HEADER)String token,
                             @Body ResponseOrder responseOrder);

    @GET(ACCEPT_ORDER)
    Observable<Response<Void>> acceptOrder(@Header(Constants.TOKEN_HEADER)String token,
                                           @Path("pkgId") Integer pkgId);

    @POST(LOGIN_PAGE)
    Observable<Response<TokenModel>> login(@Body LoginInfo loginInfo);

    class Creator{
        public static ConnectionService newServicesList(){
            Gson gson = new GsonBuilder()
                    .setDateFormat(Constants.DATE_TIME_FORMAT)
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .create();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConnectionService.ENDPOINT)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(ConnectionService.class);
        }
    }
}
