package com.duyi.rxjavademo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.duyi.rxjavademo.TAG
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.activity_main.bt_filter
import java.io.Serializable
import java.util.concurrent.TimeUnit


class FilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.duyi.rxjavademo.R.layout.activity_filter)
        title = "过滤操作符"

        /**
         * 通过一定逻辑来过滤被观察者发送的事件，如果返回 true 则会发送事件，否则不会发送。
         */
        bt_filter.setOnClickListener {
            var i = 0
            Observable.just(1, 2, 3)
                .filter(object : Predicate<Int> {
                    override fun test(integer: Int): Boolean {
                        Log.d(TAG, "==================text$integer ")
                        return integer < 2
                    }

                })
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "==================onSubscribe ")
                    }

                    override fun onNext(integer: Int) {
                        i += integer
                        Log.d(TAG, "==================onNext " + integer!!)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "==================onError ")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "==================onComplete ")
                    }
                })
        }

        /**
         * 可以过滤不符合该类型事件
         */
        bt_ofType.setOnClickListener {
            Observable.just<Serializable>(1, 2, 3, "chan", "zhide")
                .ofType(Int::class.java)
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "==================onSubscribe ")
                    }

                    override fun onNext(integer: Int) {
                        Log.d(TAG, "==================onNext " + integer)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "==================onError ")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "==================onComplete ")
                    }
                })
        }

        /**
         * 跳过正序某些事件，count 代表跳过事件的数量
         */
        bt_skip.setOnClickListener {
            Observable.just(1, 2, 3)
                .skip(2)
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "==================onSubscribe ")
                    }

                    override fun onNext(integer: Int) {
                        Log.d(TAG, "==================onNext " + integer!!)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "==================onError ")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "==================onComplete ")
                    }
                })
        }

        /**
         * 过滤事件序列中的重复事件。
         */
        bt_distinct.setOnClickListener {
            Observable.just(1, 2, 3, 3, 2, 1)
                .distinct()
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "==================onSubscribe ")
                    }

                    override fun onNext(integer: Int) {
                        Log.d(TAG, "==================onNext " + integer!!)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "==================onError ")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "==================onComplete ")
                    }
                })

        }

        /**
         * 过滤掉连续重复的事件
         */
        bt_distinctUtilChanged.setOnClickListener {
            Observable.just(1, 2, 3, 3, 2, 1)
                .distinctUntilChanged()
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "==================onSubscribe ")
                    }

                    override fun onNext(integer: Int) {
                        Log.d(TAG, "==================onNext " + integer!!)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "==================onError ")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "==================onComplete ")
                    }
                })
        }

        /**
         * 控制观察者接收的事件的数量。
         * takeLast() 的作用就是控制观察者只能接受事件序列的后面几件事情，这里就不再讲解了，大家可以自己试试。
         */
        bt_take.setOnClickListener {
            Observable.just(1, 2, 3, 4, 5)
                .take(3)
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "==================onSubscribe ")
                    }

                    override fun onNext(integer: Int) {
                        Log.d(TAG, "==================onNext " + integer!!)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "==================onError ")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "==================onComplete ")
                    }
                })
        }

        /**
         * 如果两件事件发送的时间间隔小于设定的时间间隔则前一件事件就不会发送给观察者。
         * throttleWithTimeout() 与此方法的作用一样，这里就不再赘述了。
         */
        bt_debounce.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e ->
                e.onNext(1)
                Thread.sleep(900)
                e.onNext(2)
            })
                .debounce(1, TimeUnit.SECONDS)
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "===================onSubscribe ")
                    }

                    override fun onNext(integer: Int) {
                        Log.d(TAG, "===================onNext " + integer!!)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "===================onError ")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "===================onComplete ")
                    }
                })

        }

        /**
         * firstElement() 取事件序列的第一个元素，lastElement() 取事件序列的最后一个元素。
         */
        bt_firstElement_lastElement.setOnClickListener {
            Observable.just(1, 2, 3, 4)
                .firstElement()
                .subscribe(object : Consumer<Int> {
                    override fun accept(integer: Int?) {
                        Log.d(TAG, "====================firstElement " + integer!!)
                    }
                })

            Observable.just(1, 2, 3, 4)
                .lastElement()
                .subscribe(object : Consumer<Int> {
                    override fun accept(integer: Int?) {
                        Log.d(TAG, "====================lastElement " + integer!!)
                    }
                })

        }

        /**
         * elementAt() 可以指定取出事件序列中事件，但是输入的 index 超出事件
         * 序列的总数的话就不会出现任何结果。这种情况下，你想发出异常信息的话
         * 就用 elementAtOrError() 。
         */
        bt_elementAt_elementAtOrError.setOnClickListener {
            Observable.just(1, 2, 3, 4)
                .elementAt(0)
                .subscribe { integer -> Log.d(TAG, "====================accept " + integer!!) }


            Observable.just(1, 2, 3, 4)
                .elementAtOrError(5)
                .subscribe { integer -> Log.d(TAG, "====================accept " + integer!!) }


        }


    }


}