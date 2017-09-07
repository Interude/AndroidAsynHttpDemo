package nantian.com.androidasynhttpdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import nantian.com.androidasynhttpdemo.CommunicationHttp.AsynHttpHandleMessage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button get, post, download, requestJson,Json;
    private LinearLayout linearLayout;
    private TextView tv;
    private ImageView im;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get = (Button) findViewById(R.id.get);
        post = (Button) findViewById(R.id.post);
        download = (Button) findViewById(R.id.download);
        requestJson = (Button) findViewById(R.id.requestJson);
        Json = (Button) findViewById(R.id.Json);
        tv = (TextView) findViewById(R.id.tv);
        im = (ImageView) findViewById(R.id.im);

        linearLayout = (LinearLayout) findViewById(R.id.mainlayout);
        get.setOnClickListener(this);
        post.setOnClickListener(this);
        download.setOnClickListener(this);
        requestJson.setOnClickListener(this);
        Json.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.get:
                /**get请求**/
                setSnackbar("Get");
                RequestParams params = new RequestParams();
                params.add("name", "xiaochunyuan");
                params.add("age", "25");
                AsynHttpHandleMessage.get("http://192.168.1.106:8080/ser", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        try {
                            System.out.println("@@successful@@--" + statusCode);
                            String responseData = new String(responseBody, "UTF-8");

                            setSnackbar(responseData);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        System.out.println("@@Failure");
                        setSnackbar("error:" + statusCode);
                    }
                });


                break;

            case R.id.post:
                /**post请求**/
                RequestParams paramss = new RequestParams();

                paramss.add("name", "xiaochunyuan");
                paramss.add("age", "25");

                AsynHttpHandleMessage.post("http://192.168.1.106:8080/ser", paramss, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        System.out.println("@@successful@@:" + statusCode);
                        try {
                            String response = new String(responseBody, "UTF-8");

                            setSnackbar(response);

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        System.out.println("@@Failure@@:" + statusCode);
                        setSnackbar("error:" + statusCode);

                    }
                });

                break;

            case R.id.download:
                /**下载图片**/
                setSnackbar("download");
                AsynHttpHandleMessage.download("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=4136761584,4034449126&fm=27&gp=0.jpg", new FileAsyncHttpResponseHandler(this) {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                        setSnackbar("error:" + statusCode);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, File file) {
                        try {
                            InputStream is = new FileInputStream(file);

                            Bitmap bitmap = BitmapFactory.decodeStream(is);

                            im.setImageBitmap(bitmap);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });

                break;

            /**请求json数据**/
            case R.id.requestJson:

                setSnackbar("jsonResquest");
                RequestParams param = new RequestParams();


                AsynHttpHandleMessage.postForCustomJson("http://192.168.1.106:8080/ser", param, new BaseJsonHttpResponseHandler<JSONObject>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONObject response) {

                        setSnackbar("success");

                        // setSnackbar(response.optString("name"));

                        if (response == null) {

                            setSnackbar("@NULL@");
                        } else {
                            //setAlertDailog("解析完成");
                            setSnackbar(response.toString());
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSONObject errorResponse) {

                        setSnackbar("Failure");
                    }

                    @Override
                    protected JSONObject parseResponse(String rawJsonData, boolean isFailure) throws Throwable {

                        //setAlertDailog("开始解析Json");


                        JSONObject object = new JSONObject(rawJsonData);


                        return object;
                    }
                });


                break;
            case R.id.Json:

              AsynHttpHandleMessage.postForJson("http://192.168.1.106:8080/ser",null,new myJsonResponseHandler());


                break;


        }
    }


    /**
     * 设置SnackBar
     **/

    private void setSnackbar(String content) {
        if (snackbar == null) {
            snackbar = Snackbar.make(linearLayout, content, Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        snackbar.setText(content);
        snackbar.show();

    }


    /**
     *
     * 对话框
     * **/
    AlertDialog.Builder builder = null ;
    public void setAlertDailog(String msg)
    {
        if (builder == null)
        {
            builder = new AlertDialog.Builder(this);

            builder.setMessage(msg);

            builder.show();
        }

        builder.setMessage(msg);

        builder.show();





    }


    class myJsonResponseHandler extends JsonHttpResponseHandler
    {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
           // super.onSuccess(statusCode, headers, response);
            //setSnackbar("hello"+statusCode);

           // setSnackbar(response.toString());

           // System.out.println(response.toString());

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
           // super.onFailure(statusCode, headers, throwable, errorResponse);

            /****statusCode == 200有可能是响应的报文不是json（json语法错误）****/
            setSnackbar("sorry"+statusCode);

        }
    }
}
