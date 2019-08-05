package com.duyi.rxjavademo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.duyi.rxjavademo.TAG
import com.duyi.rxjavademo.data.Person
import com.duyi.rxjavademo.data.Plan
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.observables.GroupedObservable
import kotlinx.android.synthetic.main.activity_transform.*
import java.util.concurrent.TimeUnit




class TransformActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.duyi.rxjavademo.R.layout.activity_transform)
        title = "转换操作符"
        /**
         * map 可以将被观察者发送的数据类型转变成其他的类型
         */
        bt_map.setOnClickListener {
            Observable.just(1, 2, 3)
                .map(object : Function<Int, String> {
                    override fun apply(integer: Int): String {
                        return "I'm $integer"
                    }
                })
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {
                        Log.e(com.duyi.rxjavademo.TAG, "===================onSubscribe")
                    }

                    override fun onNext(s: String) {
                        Log.e(com.duyi.rxjavademo.TAG, "===================onNext $s")
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
        }


        /**
         * flatMap() 其实与 map() 类似，但是 flatMap() 返回的是一个 Observerable。现在用一个例子来说明 flatMap() 的用法。
         */
        bt_flatMap.setOnClickListener {
            //如果用map,将每个人的每个计划的每个行动都打印出来，就得这么写
            val personList = mutableListOf<Person>()
            Observable.fromIterable<Person>(personList)
                .map(Function<Person, List<Plan>> { person -> person.planList })
                .subscribe(object : Observer<List<Plan>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(plans: List<Plan>) {
                        for (plan in plans) {
                            val planActionList = plan.actionList
                            for (action in planActionList) {
                                Log.d(TAG, "==================action $action")
                            }
                        }
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })

            //但是用了flatmap就不一样了，打印出来就是这样的
            Observable.fromIterable<Person>(personList)
                .flatMap(Function<Person, ObservableSource<Plan>> { person -> Observable.fromIterable(person.planList) })
                .flatMap { plan -> Observable.fromIterable(plan.actionList) }
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(s: String) {
                        Log.d(TAG, "==================action: $s")
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
        }

        /**
         * concatMap() 和 flatMap() 基本上是一样的，只不过 concatMap() 转发出来的事件是有序的，而 flatMap() 是无序的。
         */
        bt_concatMap.setOnClickListener {
            //flatmap的无序性
            val personList = mutableListOf<Person>()
            Observable.fromIterable<Person>(personList)
                .flatMap(Function<Person, ObservableSource<Plan>> { person ->
                    if ("chan" == person.name) {
                        Observable.fromIterable(person.planList).delay(10, TimeUnit.MILLISECONDS)
                    } else Observable.fromIterable(person.planList)
                })
                .subscribe(object : Observer<Plan> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(plan: Plan) {
                        Log.d(TAG, "==================plan " + plan.content!!)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })


            //concat的有序性
            Observable.fromIterable<Person>(personList)
                .concatMap(Function<Person, ObservableSource<Plan>> { person ->
                    if ("chan" == person.name) {
                        Observable.fromIterable(person.planList).delay(10, TimeUnit.MILLISECONDS)
                    } else Observable.fromIterable(person.planList)
                })
                .subscribe(object : Observer<Plan> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(plan: Plan) {
                        Log.d(TAG, "==================plan " + plan.content!!)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
        }

        /**
         * 从需要发送的事件当中获取一定数量的事件，并将这些事件放到缓冲区当中一并发出。
         * buffer 有两个参数，一个是 count，另一个 skip。count 缓冲区元素的数量，skip 就代表缓冲区满了之后，发送下一次事件序列的时候要跳过多少元素。这样说可能还是有点抽象，直接看代码：
         */
        bt_buffer.setOnClickListener {
            Observable.just(1, 2, 3, 4, 5)
                .buffer(1, 2)
                .subscribe(object : Observer<List<Int>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(integers: List<Int>) {
                        Log.d(TAG, "================缓冲区大小： " + integers.size)
                        for (i in integers) {
                            Log.d(TAG, "================元素： $i")
                        }
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
        }

        /**
         * 将发送的数据进行分组，每个分组都会返回一个被观察者。
         * 在 groupBy() 方法返回的参数是分组的名字，每返回一个值，那就代表会创建一个组，以上的代码就是将1~10的数据分成3组
         */
        bt_groupBy.setOnClickListener {
            Observable.just(5, 2, 3, 4, 1, 6, 8, 9, 7, 10)
                .groupBy(object : Function<Int, Int> {
                    override fun apply(integer: Int): Int? {
                        return integer % 3
                    }
                })
                .subscribe(object : Observer<GroupedObservable<Int, Int>> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "====================onSubscribe ")
                    }

                    override fun onNext(integerIntegerGroupedObservable: GroupedObservable<Int, Int>) {
                        Log.d(TAG, "====================onNext ")
                        integerIntegerGroupedObservable.subscribe(object : Observer<Int> {
                            override fun onSubscribe(d: Disposable) {
                                Log.d(TAG, "====================GroupedObservable onSubscribe ")
                            }

                            override fun onNext(integer: Int) {
                                Log.d(TAG,
                                    "====================GroupedObservable onNext  groupName: " + integerIntegerGroupedObservable.key + " value: " + integer
                                )
                            }

                            override fun onError(e: Throwable) {
                                Log.d(TAG, "====================GroupedObservable onError ")
                            }

                            override fun onComplete() {
                                Log.d(TAG, "====================GroupedObservable onComplete ")
                            }
                        })
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "====================onError ")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "====================onComplete ")
                    }
                })
        }

        /**
         * 浏览
         * 将数据以一定的逻辑聚合起来。
         */
        bt_scan.setOnClickListener {
            Observable.just(1, 2, 3, 4, 5)
                .scan(object : BiFunction<Int, Int, Int> {
                    override fun apply(integer: Int, integer2: Int): Int {
                        Log.d(TAG, "====================apply ")
                        Log.d(TAG, "====================integer $integer")
                        Log.d(TAG, "====================integer2 $integer2")
                        return integer + integer2
                    }
                })
                .subscribe(object : Consumer<Int> {
                    override fun accept(integer: Int) {
                        Log.d(TAG, "====================accept $integer")
                    }
                })

        }

        /**
         * 发送指定数量的事件时，就将这些事件分为一组。window 中的 count 的参数就是代表指定的数量，例如将 count 指定为2，那么每发2个数据就会将这2个数据分成一组。
         */
        bt_window.setOnClickListener {
            Observable.just(1, 2, 3, 4, 5)
                .window(2)
                .subscribe(object : Observer<Observable<Int>> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "=====================onSubscribe ")
                    }

                    override fun onNext(integerObservable: Observable<Int>) {
                        integerObservable.subscribe(object : Observer<Int> {
                            override fun onSubscribe(d: Disposable) {
                                Log.d(TAG, "=====================integerObservable onSubscribe ")
                            }

                            override fun onNext(integer: Int) {
                                Log.d(
                                    TAG,
                                    "=====================integerObservable onNext " + integer!!
                                )
                            }

                            override fun onError(e: Throwable) {
                                Log.d(TAG, "=====================integerObservable onError ")
                            }

                            override fun onComplete() {
                                Log.d(TAG, "=====================integerObservable onComplete ")
                            }
                        })
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "=====================onError ")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "=====================onComplete ")
                    }
                })

        }
    }
}