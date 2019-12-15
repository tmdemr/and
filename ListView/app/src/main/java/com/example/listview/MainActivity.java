package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.NameValuePair;
import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.client.entity.UrlEncodedFormEntity;
import ch.boye.httpclientandroidlib.client.methods.HttpPost;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import ch.boye.httpclientandroidlib.message.BasicNameValuePair;

public class MainActivity extends AppCompatActivity {

    ListView list;
    EditText et_name;
    Button btn_add;

    ArrayList<String> items;

    String getMsg = ""; // 서버로부터 전달 받는 데이터
    InputStream is = null; // Json input stream

     CustomAdapter mAdapter;
    AsyncTask_test test = null; // 어싱크 태스크 클래스를 사용하기 위한 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new CustomAdapter();

        list = findViewById(R.id.list);
        et_name = findViewById(R.id.et_name);
        btn_add = findViewById(R.id.btn_add);

        // 비동기 방식으로 AysncTask가 시작됨
        // xml 연결이 끝나고 난 후 해주는게 좋음

        items = new ArrayList<String>();
        final String[] text = {"일", "이", "삼", "사", "오"};

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Toast.makeText(getApplicationContext(), text[index], Toast.LENGTH_SHORT).show();
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {
                items.remove(index);
                adapter.notifyDataSetChanged();

                return false;
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //위에서 생성한 어레이리스트에 에디트 텍스트에 작성한 내용을 추가
                items.add(et_name.getText().toString());
                //어댑터 갱신
                adapter.notifyDataSetChanged();

                showToast("아이템 추가입니다.");
            }
        });
        test = new AsyncTask_test();
        test.execute();
    }
    void showToast(String str){
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    class AsyncTask_test extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
            //NameValuePair 클래스를 이용한 list 변수는 앱상의 변수를 php에 POST 방식을 ㅗ전달하기 위해 사용
            ArrayList<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair("userId","홍길동"));

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://localhost/Login");
            // try-catch 경우 - 파일처리 , 네트워크
                httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));//UTF-8로 변환해주는 부분
                HttpResponse response = httpClient.execute(httpPost);
                // 아래 코드부터는 요청에 대한 응답을 받아와서 처리하는 코드
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                String line = ""; //결과를 한줄씩 읽어서 저장할 변수
                getMsg = ""; //getMsg 변수 초기화 ( 리스트 갱신등의 이유로 기존의 값이 남아 있지 않도록 하기 위해.
                // 더 이상 읽어들일 내용이 없을 때까지 반복 수행
                while((line = reader.readLine())!=null){
                    getMsg += line;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return getMsg;
        }
        @Override
        protected void onPostExecute(String _result){
            // 결과물이 onPostExecute 의 s로 날아옴
           // if(s.equals("success") || s == "success")
            //{
                // 로그인 성공
//            }

            try {
                JSONObject root = new JSONObject(_result);
                //JSONArray [] 로 시작하는 부분
                JSONArray results = new JSONArray(root.getString("results")); // results 를 없애면 results 로 들어갈 부분
                for(int i = 0; i <results.length(); i++){
                    JSONObject content = results.getJSONObject(i);
                    String id = content.getString("id");//rows 옆, 태그값  php 에서 가공했던 것.
                    String pass = content.getString("pass");
                    String desc = content.getString("desc");
                    String etc = content.getString("etc");

                    mAdapter.addItem(id, pass, desc, etc);
                    mAdapter.notifyDataSetChanged();
                }
                test = null;
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}

