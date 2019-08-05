package com.duyi.rxjavademo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.duyi.rxjavademo.TAG
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiConsumer
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import kotlinx.android.synthetic.main.activity_combination.*
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit






class CombinationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.duyi.rxjavademo.R.layout.activity_combination)
        title = "组合操作符"

        /**
         * 可以将多个观察者组合在一起，然后按照之前发送顺序发送事件。需要注意的是，concat() 最多只可以发送4个事件。
         */
        bt_concat.setOnClickListener {
            Observable.concat(
                Observable.just(1, 2),
                Observable.just(3, 4),
                Observable.just(5, 6),
                Observable.just(7, 8)
            )
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(integer: Int) {
                        Log.d(TAG, "================onNext $integer")
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
        }

        /**
         * 与 concat() 作用一样，不过 concatArray() 可以发送多于 4 个被观察者。
         */
        bt_concatArray.setOnClickListener {
            Observable.concatArray(
                Observable.just(1, 2),
                Observable.just(3, 4),
                Observable.just(5, 6),
                Observable.just(7, 8),
                Observable.just(9, 10)
            )
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(integer: Int) {
                        Log.d(TAG, "================onNext $integer")
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })

        }

        /**
         * 这个方法月 concat() 作用基本一样，知识 concat() 是串行发送事件，而 merge() 并行发送事件。
         * mergeArray() 与 merge() 的作用是一样的，只是它可以发送4个以上的被观察者，这里就不再赘述了。
         */
        bt_merge.setOnClickListener {
            Observable.merge(
                Observable.interval(1, TimeUnit.SECONDS).map(object : Function<Long, String> {
                    @Throws(Exception::class)
                    override fun apply(aLong: Long): String {
                        return "A$aLong"
                    }
                }),
                Observable.interval(1, TimeUnit.SECONDS).map(object : Function<Long, String> {
                    @Throws(Exception::class)
                    override fun apply(aLong: Long): String {
                        return "B$aLong"
                    }
                })
            )
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(s: String) {
                        Log.d(TAG, "=====================onNext $s")
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
        }

        /**
         * 在 concatArray() 和 mergeArray() 两个方法当中，如果其中有一个被观察者发送了一个 Error 事件，那么就会停止发送事件，如果你
         * 想 onError() 事件延迟到所有被观察者都发送完事件后再执行的话，就可以使用  concatArrayDelayError() 和 mergeArrayDelayError()
         */
        bt_concatArrayDelayError.setOnClickListener {
            /**
             * 首先使用 concatArray() 来验证一下发送 onError() 事件是否会中断其他被观察者发送事件，代码如下
             */
            Observable.concatArray(
                Observable.create { e ->
                    e.onNext(1)
                    e.onError(NumberFormatException())
                }, Observable.just(2, 3, 4)
            )
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(integer: Int) {
                        Log.d(TAG, "===================onNext " + integer!!)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "===================onError ")
                    }

                    override fun onComplete() {

                    }
                })

            /**
             * 从结果可以知道，确实中断了，现在换用 concatArrayDelayError()，代码如下：
             */
            Observable.concatArrayDelayError(
                Observable.create { e ->
                    e.onNext(1)
                    e.onError(NumberFormatException())
                }, Observable.just(2, 3, 4)
            )
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(integer: Int) {
                        Log.d(TAG, "===================onNext " + integer!!)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "===================onError ")
                    }

                    override fun onComplete() {

                    }
                })

            /**
             * 从结果可以看到，onError 事件是在所有被观察者发送完事件才发送的。mergeArrayDelayError() 也是有同样的作用，这里不再赘述。
             */
        }

        /**
         * 会将多个被观察者合并，根据各个被观察者发送事件的顺序一个个结合起来，最终发送的事件数量会与源 Observable 中最少事件的数量一样。
         */
        bt_zip.setOnClickListener {
            Observable.zip(Observable.intervalRange(1, 5, 1, 1, TimeUnit.SECONDS)
                .map(object : Function<Long, String> {
                    override fun apply(aLong: Long): String {
                        val s1 = "A" + aLong!!
                        Log.d(TAG, "===================A 发送的事件 $s1")
                        return s1
                    }
                }),
                Observable.intervalRange(1, 6, 1, 1, TimeUnit.SECONDS)
                    .map(object : Function<Long, String> {

                        override fun apply(aLong: Long): String {
                            val s2 = "B" + aLong
                            Log.d(TAG, "===================B 发送的事件 $s2")
                            return s2
                        }
                    }),
                object : BiFunction<String, String, String> {
                    override fun apply(s: String, s2: String): String {
                        return s + s2
                    }
                })
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "===================onSubscribe ")
                    }

                    override fun onNext(s: String) {
                        Log.d(TAG, "===================onNext $s")
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
         * combineLatest() 的作用与 zip() 类似，但是 combineLatest() 发送事件
         * 的序列是与发送的时间线有关的，当 combineLatest() 中所有的 Observable 都发
         * 送了事件，只要其中有一个 Observable 发送事件，这个事件就会和
         * 其他 Observable 最近发送的事件结合起来发送，这样可能还是比较抽象，看看以下例子代码。
         */
        bt_combineLatest_combineLatestDelayError.setOnClickListener {
            Observable.combineLatest(
                Observable.intervalRange(1, 4, 1, 1, TimeUnit.SECONDS)
                    .map(object : Function<Long, String> {
                        override fun apply(aLong: Long): String {
                            val s1 = "A" + aLong!!
                            Log.d(TAG, "===================A 发送的事件 $s1")
                            return s1
                        }
                    }),
                Observable.intervalRange(1, 5, 2, 2, TimeUnit.SECONDS)
                    .map(object : Function<Long, String> {
                        override fun apply(aLong: Long): String {
                            val s2 = "B" + aLong!!
                            Log.d(TAG, "===================B 发送的事件 $s2")
                            return s2
                        }
                    }),
                BiFunction<String, String, String> { s, s2 -> s + s2 })
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "===================onSubscribe ")
                    }

                    override fun onNext(s: String) {
                        Log.d(TAG, "===================最终接收到的事件 $s")
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "===================onError ")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "===================onComplete ")
                    }
                })

            /**
             * 分析上述结果可以知道，当发送 A1 事件之后，因为 B 并没有发送任何事件，所以根本不会
             * 发生结合。当 B 发送了 B1 事件之后，就会与 A 最近发送的事件 A2 结合成 A2B1，这样只有后面
             * 一有被观察者发送事件，这个事件就会与其他被观察者最近发送的事件结合起来了。
                因为 combineLatestDelayError() 就是多了延迟发送 onError() 功能，这里就不再赘述了。
             */

        }

        /**
         * 与 scan() 操作符的作用也是将发送数据以一定逻辑聚合起来，这两个的区别在于 scan() 每处理一
         * 次数据就会将事件发送给观察者，而 reduce() 会将所有数据聚合在一起才会发送事件给观察者。
         */
        bt_reduce.setOnClickListener {
            Observable.just(0, 1, 2, 3)
                .reduce(object : BiFunction<Int, Int, Int> {
                    override fun apply(integer: Int, integer2: Int): Int {
                        val res = integer + integer2
                        Log.d(TAG, "====================integer $integer")
                        Log.d(TAG, "====================integer2 $integer2")
                        Log.d(TAG, "====================res $res")
                        return res
                    }
                })
                .subscribe(object : Consumer<Int> {
                    override fun accept(integer: Int?) {
                        Log.d(TAG, "==================accept " + integer!!)
                    }
                })
        }

        /**
         * 将数据收集到数据结构当中。
         */
        bt_collect.setOnClickListener {
            Observable.just(1, 2, 3, 4)
                .collect(object : Callable<ArrayList<Int>> {
                    override fun call(): ArrayList<Int> {
                        return ArrayList()
                    }
                },
                    object : BiConsumer<ArrayList<Int>, Int> {
                        override fun accept(integers: ArrayList<Int>, integer: Int) {
                            integers.add(integer)
                        }
                    })
                .subscribe(Consumer<ArrayList<Int>> { integers ->
                    Log.d(
                        TAG,
                        "===============accept $integers"
                    )
                })

        }

        /**
         * 在发送事件之前追加事件，startWith() 追加一个事件，startWithArray() 可以追加多个事件。追加的事件会先发出。
         */
        bt_startWith_startWithArray.setOnClickListener {
            Observable.just(5, 6, 7)
                .startWithArray(2, 3, 4)
                .startWith(1)
                .subscribe { integer -> Log.d(TAG, "================accept " + integer!!) }
        }

        /**
         * 返回被观察者发送事件的数量。
         */
        bt_count.setOnClickListener {
            Observable.just(1, 2, 3)
                .count()
                .subscribe { aLong -> Log.d(TAG, "=======================aLong " + aLong!!) }

        }

    }



}