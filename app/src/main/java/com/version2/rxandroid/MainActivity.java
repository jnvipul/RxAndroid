package com.version2.rxandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text_view)
    TextView mTextView;

    Observable.OnSubscribe mObservableAction = new Observable.OnSubscribe<String>() {
        @Override
        public void call(final Subscriber<? super String> subscriber) {
            subscriber.onNext("Version 2.0");
            subscriber.onCompleted();
        }
    };

    Observable<String> mObservable = Observable.create(mObservableAction);

    Subscriber<String> mTextViewSubscriber =  new Subscriber<String>() {
        @Override
        public void onCompleted() {}

        @Override
        public void onError(final Throwable e) {}

        @Override
        public void onNext(final String s) {
            mTextView.setText(s);
        }
    };

    Subscriber<String> mToastSubscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {}

        @Override
        public void onError(final Throwable e) {}

        @Override
        public void onNext(final String s) {
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
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
        mObservable.observeOn(AndroidSchedulers.mainThread());
        mObservable.subscribe(mTextViewSubscriber);
        mObservable.subscribe(mToastSubscriber);
    }
}
