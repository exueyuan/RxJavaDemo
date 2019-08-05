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
import kotlinx.android.synthetic.main.activity_condition.*
import java.util.concurrent.TimeUnit








class ConditionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.duyi.rxjavademo.R.layout.activity_condition)
        title = "条件操作符"

        /**
         * 判断事件序列是否全部满足某个事件，如果都满足则返回 true，反之则返回 false。
         */
        bt_all.setOnClickListener {
            Observable.just(1, 2, 3, 4)
                .all(object : Predicate<Int> {
                    override fun test(integer: Int): Boolean {
                        return integer < 5
                    }
                })
                .subscribe(object : Consumer<Boolean> {
                    override fun accept(aBoolean: Boolean?) {
                        Log.d(TAG, "==================aBoolean " + aBoolean!!)
                    }
                })
        }

        /**
         * 可以设置条件，当某个数据满足条件时就会发送该数据，反之则不发送。
         */
        bt_takeWhile.setOnClickListener {
            Observable.just(1, 2, 3, 4)
                .takeWhile(object : Predicate<Int> {
                    override fun test(integer: Int): Boolean {
                        return integer < 3
                    }
                })
                .subscribe { integer -> Log.d(TAG, "========================integer " + integer!!) }
        }

        /**
         * 可以设置条件，当某个数据满足条件时不发送该数据，反之则发送。
         */
        bt_skipWhile.setOnClickListener {
            Observable.just(1, 2, 3, 4)
                .skipWhile(object : Predicate<Int> {
                    override fun test(integer: Int): Boolean {
                        return integer < 3
                    }
                })
                .subscribe { integer -> Log.d(TAG, "========================integer " + integer!!) }

        }

        /**
         * 可以设置条件，当事件满足此条件时，下一次的事件就不会被发送了。
         */
        bt_takeUntil.setOnClickListener {
            Observable.just(1, 2, 3, 4, 5, 6)
                .takeUntil(object : Predicate<Int> {
                    @Throws(Exception::class)
                    override fun test(integer: Int): Boolean {
                        return integer > 3
                    }
                })
                .subscribe { integer -> Log.d(TAG, "========================integer " + integer!!) }
        }

        /**
         * 当 skipUntil() 中的 Observable 发送事件了，原来的 Observable 才会发送事件给观察者。
         * 从结果可以看出，skipUntil() 里的 Observable 并不会发送事件给观察者。
         */
        bt_skipUntil.setOnClickListener {
            Observable.intervalRange(1, 5, 0, 1, TimeUnit.SECONDS)
                .skipUntil(Observable.intervalRange(6, 5, 3, 1, TimeUnit.SECONDS))
                .subscribe(object : Observer<Long> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "========================onSubscribe ")
                    }

                    override fun onNext(along: Long) {
                        Log.d(TAG, "========================onNext " + along!!)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "========================onError ")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "========================onComplete ")
                    }
                })
        }

        /**
         * 判断两个 Observable 发送的事件是否相同。
         */
        bt_sequenceEqual.setOnClickListener {
            Observable.sequenceEqual(
                Observable.just(1, 2, 3),
                Observable.just(1, 2, 3)
            )
                .subscribe { aBoolean -> Log.d(TAG, "========================onNext " + aBoolean!!) }

        }

        /**
         * 判断事件序列中是否含有某个元素，如果有则返回 true，如果没有则返回 false。
         */
        bt_contains.setOnClickListener {
            Observable.just(1, 2, 3)
                .contains(3)
                .subscribe { aBoolean -> Log.d(TAG, "========================onNext " + aBoolean!!) }

        }

        /**
         * 判断事件序列是否为空。
         */
        bt_isEmpty.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e -> e.onComplete() })
                .isEmpty
                .subscribe { aBoolean -> Log.d(TAG, "========================onNext " + aBoolean!!) }

        }

        /**
         * amb() 要传入一个 Observable 集合，但是只会发送最先发送事件的 Observable 中的事件，其余 Observable 将会被丢弃。
         */
        bt_amb.setOnClickListener {
            val list = mutableListOf<Observable<Long>>()

            list.add(Observable.intervalRange(1, 5, 2, 1, TimeUnit.SECONDS))
            list.add(Observable.intervalRange(6, 5, 0, 1, TimeUnit.SECONDS))

            Observable.amb(list)
                .subscribe(Consumer<Long> { aLong ->
                    Log.d(
                        TAG,
                        "========================aLong " + aLong!!
                    )
                })

        }

        /**
         * 如果观察者只发送一个 onComplete() 事件，则可以利用这个方法发送一个值。
         */
        bt_defaultIfEmpty.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e -> e.onComplete() })
                .defaultIfEmpty(666)
                .subscribe { integer -> Log.d(TAG, "========================onNext " + integer!!) }

        }


    }


}