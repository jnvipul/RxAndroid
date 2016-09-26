package com.version2.rxandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text_view)
    TextView mTextView;

    ArrayList<String> mList = new ArrayList<>();

    Observable<String> mObservable = Observable.just("Version 2.0");

    Subscriber<String> mTextViewSubscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(final Throwable e) {
        }

        @Override
        public void onNext(final String s) {
            mTextView.setText(s);
        }
    };

    Subscriber<String> mToastSubscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(final Throwable e) {
        }

        @Override
        public void onNext(final String s) {
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
        }
    };


    // We are not creating a fully qualified subscriber
    Action1<String> textViewSetTextAction = new Action1<String>() {
        @Override
        public void call(final String s) {
            mTextView.setText(s);
        }
    };


    Func1<String, String> toUpperCase = new Func1<String, String>() {
        @Override
        public String call(final String s) {
            return s.toUpperCase();
        }
    };

    Func2<String, String, String> mergeRoutine = new Func2<String, String, String>() {
        @Override
        public String call(final String s1, final String s2) {
            return String.format("%s %s", s1, s2);
        }
    };


    Func1<List<String>, Observable<String>> geturls = new Func1<List<String>,
            Observable<String>>() {


        @Override
        public Observable<String> call(final List<String> list) {
            return Observable.from(list);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setup();
    }

    private void setup() {
        setupDummyList();

        // Using map operator
        // Using reduce operator - It acts like an accumulate operator
        Observable.just(mList)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(geturls)
                .reduce(mergeRoutine)
                .subscribe(mTextViewSubscriber);
    }

    private void setupDummyList() {
        mList.add("www.google.com");
        mList.add("www.facebook.com");
    }


}
