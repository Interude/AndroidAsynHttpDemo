package nantian.com.androidasynhttpdemo.CommunicationHttp;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * Created by xiaochunyuan on 17/9/6.
 *
 * Http
 * */

public class AsynHttpHandleMessage {


    private static AsyncHttpClient client = new AsyncHttpClient();


    /**
     * get请求
     * <p>
     * url - 请求服务器的url
     * params - 请求参数
     * handlerinterface - 响应回调接口
     **/
    public static void get(String url, RequestParams params, ResponseHandlerInterface handlerInterface) {

        client.get(url, params, handlerInterface);


    }

    /**
     * post请求
     * <p>
     * url - 请求服务器的url
     * params - 请求参数
     * handlerinterface - 响应回调接口
     **/

    public static void post(String url, RequestParams params, ResponseHandlerInterface handlerInterface) {
        client.post(url, params, handlerInterface);
    }


    public static void download(String url, ResponseHandlerInterface handlerInterface) {
        client.get(url, handlerInterface);


    }


    /**
     * Base-Json-parse---->可以自定义对json的解析格式，比如GSON、或者JsonObject
     **/
    public static void postForCustomJson(String url, RequestParams params, ResponseHandlerInterface handlerInterface) {
        client.post(url, params, handlerInterface);

    }

    /**
     * 请求json格式的数据
     **/

    public static void postForJson(String url, RequestParams params, ResponseHandlerInterface handlerInterface) {

        client.post(url, params, handlerInterface);
    }


}
