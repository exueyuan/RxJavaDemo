package com.duyi.rxjavademo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.duyi.rxjavademo.R
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_rxjavabasic.*

class RxJavaBasicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjavabasic)
        title = "基础体验"
        bt_use.setOnClickListener {
            //创建被观察者
            val observable = Observable.create(ObservableOnSubscribe<Int> { e ->
                Log.d(com.duyi.rxjavademo.TAG, "=========================currentThread name: " + Thread.currentThread().name)
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onComplete()
            })

            //创建观察者
            val observer = object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                    Log.d(com.duyi.rxjavademo.TAG, "======================onSubscribe")
                }

                override fun onNext(integer: Int) {
                    Log.d(com.duyi.rxjavademo.TAG, "======================onNext " + integer!!)
                }

                override fun onError(e: Throwable) {
                    Log.d(com.duyi.rxjavademo.TAG, "======================onError")
                }

                override fun onComplete() {
                    Log.d(com.duyi.rxjavademo.TAG, "======================onComplete")
                }
            }
            //被观察者订阅观察者
            observable.subscribe(observer)
        }
        bt_link_use.setOnClickListener {
            //链式调用
            Observable.create(ObservableOnSubscribe<Int> { e ->
                Log.d(com.duyi.rxjavademo.TAG, "=========================currentThread name: " + Thread.currentThread().name)
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onComplete()
            })
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(com.duyi.rxjavademo.TAG, "======================onSubscribe")
                    }

                    override fun onNext(integer: Int) {
                        Log.d(com.duyi.rxjavademo.TAG, "======================onNext " + integer!!)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(com.duyi.rxjavademo.TAG, "======================onError")
                    }

                    override fun onComplete() {
                        Log.d(com.duyi.rxjavademo.TAG, "======================onComplete")
                    }
                })
        }


    }
}