package com.kodonho.android.rxandroid_basic04_lambda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import rx.Observable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "Rxandroid Basic4";
    Button lambda,map,flatmap,zip;
    TextView tv;
    ListView listView;
    ArrayAdapter<String> adapter;

    ArrayList<String> datas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lambda = (Button) findViewById(R.id.lambda);
        map = (Button) findViewById(R.id.map);
        flatmap = (Button) findViewById(R.id.flatmap);
        zip = (Button) findViewById(R.id.btnZip);

        lambda.setOnClickListener(this);
        map.setOnClickListener(this);
        flatmap.setOnClickListener(this);
        zip.setOnClickListener(this);

        tv = (TextView) findViewById(R.id.textView);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,datas);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lambda:
                doLambda();
                break;
            case R.id.map:
                doMap();
                break;
            case R.id.flatmap:
                doFlatMap();
                break;
            case R.id.btnZip:
                break;
        }
    }

    public void doLambda(){
        Observable<String> observable = Observable.just("I am Lambda!");
        observable.subscribe(
            item -> tv.setText(item),
            e -> e.printStackTrace(),
            () -> Log.i(TAG,"Completed")
        );
    }

    public void doMap(){
        Observable.from(new String[]{"dog","bird","chicken","horse","turtle","rabbit","tiger"})
            .map(item -> "[" + item + "]")
            .subscribe(
                item -> datas.add(item),
                e -> e.printStackTrace(),
                () -> adapter.notifyDataSetChanged()
            );
    }

    public void doFlatMap(){
        Observable.from(new String[]{"dog","bird","chicken","horse","turtle","rabbit","tiger"})
            .flatMap(item -> Observable.from(new String[]{ "name:"+item  ,    item.getBytes()+"", "code:"+item } ))
            .subscribe(
                item -> datas.add(item),
                e -> e.printStackTrace(),
                () -> adapter.notifyDataSetChanged()
            );
    }
}
