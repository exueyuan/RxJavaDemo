package com.duyi.rxjavademo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.duyi.rxjavademo.R
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_create.*
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.FutureTask
import java.util.concurrent.TimeUnit

class CreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        title = "创建操作符"

        bt_create.setOnClickListener {
            //创建被观察者
            val observable = Observable.create(ObservableOnSubscribe<String> { e ->
                e.onNext("Hello Observer")
                e.onComplete()
            })
            val observer = object : Observer<String> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(s: String) {
                    Log.d("chan", "=============onNext $s")
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    Log.d("chan", "=============onComplete ")
                }
            }


            observable.subscribe(observer)
        }

        bt_just.setOnClickListener {
            Observable.just(1, 2, 3)
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(com.duyi.rxjavademo.TAG, "=================onSubscribe")
                    }

                    override fun onNext(integer: Int) {
                        Log.d(com.duyi.rxjavademo.TAG, "=================onNext $integer")
                    }

                    override fun onError(e: Throwable) {
                        Log.d(com.duyi.rxjavademo.TAG, "=================onError ")
                    }

                    override fun onComplete() {
                        Log.d(com.duyi.rxjavademo.TAG, "=================onComplete ")
                    }
                })
        }

        bt_from_array.setOnClickListener {
            val array = arrayOf(1, 2, 3, 4)
            Observable.fromArray(*array)
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(com.duyi.rxjavademo.TAG, "=================onSubscribe")
                    }

                    override fun onNext(integer: Int) {
                        Log.d(com.duyi.rxjavademo.TAG, "=================onNext $integer")
                    }

                    override fun onError(e: Throwable) {
                        Log.d(com.duyi.rxjavademo.TAG, "=================onError ")
                    }

                    override fun onComplete() {
                        Log.d(com.duyi.rxjavademo.TAG, "=================onComplete ")
                    }
                })
        }

        /**
         * 这里的 Callable 是 java.util.concurrent 中的 Callable，Callable 和 Runnable 的用法基本一致，只是它会返回一个结果值，这个结果值就是发给观察者的。
         */
        bt_from_callable.setOnClickListener {
            Observable.fromCallable { 1 }
                .subscribe { integer -> Log.d(com.duyi.rxjavademo.TAG, "================accept " + integer!!) }
        }

        bt_from_future.setOnClickListener {
            val futureTask = FutureTask(Callable {
                Log.d(com.duyi.rxjavademo.TAG, "CallableDemo is Running")
                "返回结果"
            })

            Observable.fromFuture(futureTask)
                .doOnSubscribe { futureTask.run() }
                .subscribe { s -> Log.d(com.duyi.rxjavademo.TAG, "================accept $s") }
        }

        bt_from_iterable.setOnClickListener {
            val list = ArrayList<Int>()
            list.add(0)
            list.add(1)
            list.add(2)
            list.add(3)
            Observable.fromIterable(list)
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(com.duyi.rxjavademo.TAG, "=================onSubscribe")
                    }

                    override fun onNext(integer: Int) {
                        Log.d(com.duyi.rxjavademo.TAG, "=================onNext " + integer!!)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(com.duyi.rxjavademo.TAG, "=================onError ")
                    }

                    override fun onComplete() {
                        Log.d(com.duyi.rxjavademo.TAG, "=================onComplete ")
                    }
                })
        }

        bt_defer.setOnClickListener {
            // i 要定义为成员变量
            var i: Int? = 100
            val observable = Observable.defer { Observable.just(i!!) }

            i = 200

            val observer = object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(integer: Int) {
                    Log.d(com.duyi.rxjavademo.TAG, "================onNext $integer")
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            }

            observable.subscribe(observer)

            i = 300

            observable.subscribe(observer)
        }

        bt_timer.setOnClickListener {
            Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(object : Observer<Long> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(aLong: Long) {
                        Log.d(com.duyi.rxjavademo.TAG, "===============onNext $aLong")
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
        }
        //每隔一段时间发一个事件
        bt_interval.setOnClickListener {
            Observable.interval(4, TimeUnit.SECONDS)
                .subscribe(object : Observer<Long> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(com.duyi.rxjavademo.TAG, "==============onSubscribe ")
                    }

                    override fun onNext(aLong: Long) {
                        Log.d(com.duyi.rxjavademo.TAG, "==============onNext " + aLong)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
        }
        //可以指定发送interval事件的开始值和数量。
        bt_interval_range.setOnClickListener {
            Observable.intervalRange(2, 5, 2, 1, TimeUnit.SECONDS)
                .subscribe(object : Observer<Long> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(com.duyi.rxjavademo.TAG, "==============onSubscribe ")
                    }

                    override fun onNext(aLong: Long) {
                        Log.d(com.duyi.rxjavademo.TAG, "==============onNext " + aLong!!)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
        }

        //同时发送一定范围的事件序列
        bt_range.setOnClickListener {
            Observable.range(2, 5)
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(com.duyi.rxjavademo.TAG, "==============onSubscribe ")
                    }

                    override fun onNext(aLong: Int) {
                        Log.d(com.duyi.rxjavademo.TAG, "==============onNext " + aLong!!)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
        }

        bt_rangeLong.setOnClickListener {
            Observable.rangeLong(2, 5)
                .subscribe(object : Observer<Long> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(com.duyi.rxjavademo.TAG, "==============onSubscribe ")
                    }

                    override fun onNext(aLong: Long) {
                        Log.d(com.duyi.rxjavademo.TAG, "==============onNext " + aLong!!)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
        }

        /**
         * empty() ： 直接发送 onComplete() 事件
         * never()：不发送任何事件
         * error()：发送 onError() 事件
         */
        bt_empty_never_error.setOnClickListener {
            Observable.empty<Any>()
                .subscribe(object : Observer<Any> {

                    override fun onSubscribe(d: Disposable) {
                        Log.d(com.duyi.rxjavademo.TAG, "==================onSubscribe")
                    }

                    override fun onNext(o: Any) {
                        Log.d(com.duyi.rxjavademo.TAG, "==================onNext")
                    }

                    override fun onError(e: Throwable) {
                        Log.d(com.duyi.rxjavademo.TAG, "==================onError $e")
                    }

                    override fun onComplete() {
                        Log.d(com.duyi.rxjavademo.TAG, "==================onComplete")
                    }
                })
        }
    }
}