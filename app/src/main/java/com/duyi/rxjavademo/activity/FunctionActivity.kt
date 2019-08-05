package com.duyi.rxjavademo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.duyi.rxjavademo.TAG
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_function.*
import java.util.concurrent.TimeUnit






class FunctionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.duyi.rxjavademo.R.layout.activity_function)
        title = "功能操作符"

        /**
         * 延迟一段事件发送事件。
         */
        bt_delay.setOnClickListener {
            Observable.just(1, 2, 3)
                .delay(2, TimeUnit.SECONDS)
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "=======================onSubscribe")
                    }

                    override fun onNext(integer: Int) {
                        Log.d(TAG, "=======================onNext " + integer!!)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        Log.d(TAG, "=======================onSubscribe")
                    }
                })
        }

        /**
         * Observable 每发送一件事件之前都会先回调这个方法。
         */
        bt_doOnEach.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e ->
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onError(NumberFormatException())
                e.onComplete()
            })
                .doOnEach(object : Consumer<Notification<Int>> {
                    override fun accept(integerNotification: Notification<Int>) {
                        Log.d(TAG, "==================doOnEach " + integerNotification.getValue())
                    }
                })
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
         * Observable 每发送 onNext() 之前都会先回调这个方法。
         */
        bt_doOnNext.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e ->
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onComplete()
            })
                .doOnNext { integer -> Log.d(TAG, "==================doOnNext " + integer!!) }
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
         * Observable 每发送 onNext() 之后都会回调这个方法。
         */
        bt_doAfterNext.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e ->
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onComplete()
            })
                .doAfterNext { integer -> Log.d(TAG, "==================doAfterNext " + integer!!) }
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
         * Observable 每发送 onComplete() 之前都会回调这个方法。
         */
        bt_doOnComplete.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e ->
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onComplete()
            })
                .doOnComplete(object : Action {
                    override fun run() {
                        Log.d(TAG, "==================doOnComplete ")
                    }
                })
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "==================onSubscribe ")
                    }

                    override fun onNext(integer: Int) {
                        Log.d(TAG, "==================onNext $integer")
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
         * Observable 每发送 onError() 之前都会回调这个方法。
         */
        bt_doOnError.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e ->
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onError(NullPointerException())
            })
                .doOnError { throwable -> Log.d(TAG, "==================doOnError $throwable") }
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "==================onSubscribe ")
                    }

                    override fun onNext(integer: Int) {
                        Log.d(TAG, "==================onNext $integer")
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
         * Observable 每发送 onSubscribe() 之前都会回调这个方法。
         */
        bt_doOnSubscribe.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e ->
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onComplete()
            })
                .doOnSubscribe { Log.d(TAG, "==================doOnSubscribe ") }
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
         * 当调用 Disposable 的 dispose() 之后回调该方法。
         */
        bt_doOnDispose.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e ->
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onComplete()
            })
                .doOnDispose { Log.d(TAG, "==================doOnDispose ") }
                .subscribe(object : Observer<Int> {
                    private var d: Disposable? = null

                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "==================onSubscribe ")
                        this.d = d
                    }

                    override fun onNext(integer: Int) {
                        Log.d(TAG, "==================onNext " + integer!!)
                        d!!.dispose()
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
         * 在回调 onSubscribe 之前回调该方法的第一个参数的回调方法，可以使用该回调方法决定是否取消订阅。
         *
         * doOnLifecycle() 第二个参数的回调方法的作用与 doOnDispose() 是一样的，现在用下面的例子来讲解：
         */
        bt_doOnLifecycle.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e ->
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onComplete()
            })
                .doOnLifecycle(
                    {
                        Log.d(TAG, "==================doOnLifecycle accept")
                    },
                    {
                        Log.d(TAG, "==================doOnLifecycle Action")
                    })
                .doOnDispose { Log.d(TAG, "==================doOnDispose Action") }
                .subscribe(object : Observer<Int> {
                    private var d: Disposable? = null
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "==================onSubscribe ")
                        this.d = d
                    }

                    override fun onNext(integer: Int) {
                        Log.d(TAG, "==================onNext " + integer!!)
                        d!!.dispose()
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
         * doOnTerminate 是在 onError 或者 onComplete 发送之前回调，而 doAfterTerminate 则是 onError 或者 onComplete 发送之后回调。
         */
        bt_doOnTerminate_doAfterTerminate.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e ->
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onError(NullPointerException())
                e.onComplete()
            })
                .doOnTerminate { Log.d(TAG, "==================doOnTerminate ") }
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
         * 在所有事件发送完毕之后回调该方法。
         *
         * 这里可能你会有个问题，那就是 doFinally() 和 doAfterTerminate() 到底有什么区
         * 别？区别就是在于取消订阅，如果取消订阅之后 doAfterTerminate() 就不会被回
         * 调，而 doFinally() 无论怎么样都会被回调，且都会在事件序列的最后。
         *
         */
        bt_doFinally.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e ->
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onComplete()
            })
                .doFinally { Log.d(TAG, "==================doFinally ") }
                .doOnDispose { Log.d(TAG, "==================doOnDispose ") }
                .doAfterTerminate { Log.d(TAG, "==================doAfterTerminate ") }
                .subscribe(object : Observer<Int> {
                    private var d: Disposable? = null
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "==================onSubscribe ")
                        this.d = d
                    }

                    override fun onNext(integer: Int) {
                        Log.d(TAG, "==================onNext " + integer!!)
                        d!!.dispose()
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
         * 当接受到一个 onError() 事件之后回调，返回的值会回调 onNext() 方法，并正常结束该事件序列。
         */
        bt_onErrorReturn.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e ->
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onError(NullPointerException())
            }).onErrorReturn {
                404
            }.subscribe(object : Observer<Int> {
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
         * 当接收到 onError() 事件时，返回一个新的 Observable，并正常结束事件序列。
         */
        bt_onErrorResumeNext.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e ->
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onError(NullPointerException())
            }).onErrorResumeNext(object : Function<Throwable, ObservableSource<Int>> {
                override fun apply(t: Throwable): ObservableSource<Int> {
                    return Observable.just(4, 5, 6)
                }

            }).subscribe(object : Observer<Int> {
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
         * 与 onErrorResumeNext() 作用基本一致，但是这个方法只能捕捉 Exception。
         */
        bt_onExceptionResumeNext.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e ->
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onError(Error("404"))
            })
                .onExceptionResumeNext(object : Observable<Int>() {
                    override fun subscribeActual(observer: Observer<in Int>) {
                        observer.onNext(333)
                        observer.onComplete()
                    }
                })
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
         * 如果出现错误事件，则会重新发送所有事件序列。times 是代表重新发的次数。
         */
        bt_retry.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e ->
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onError(Exception("404"))
            })
                .retry(2)
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
         * 出现错误事件之后，可以通过此方法判断是否继续发送事件。
         */
        bt_retryUtil.setOnClickListener {
            var i = 0
            Observable.create(ObservableOnSubscribe<Int> { e ->
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onError(Exception("404"))
            })
                .retryUntil { i == 6 }
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
         * 当被观察者接收到异常或者错误事件时会回调该方法，这个方
         * 法会返回一个新的被观察者。如果返回的被观察者发送 Error 事件
         * 则之前的被观察者不会继续发送事件，如果发送正常事件则之前的被观
         * 察者会继续不断重试发送事件。
         */
        bt_retryWhen.setOnClickListener {
            Observable.create(ObservableOnSubscribe<String> { e ->
                e.onNext("chan")
                e.onNext("ze")
                e.onNext("de")
                e.onError(Exception("404"))
                e.onNext("haha")
            })
                .retryWhen { throwableObservable ->
                    throwableObservable.flatMap { throwable ->
                        if (throwable.toString() != "java.lang.Exception: 404") {
                            Observable.just("可以忽略的异常")
                        } else {
                            Observable.error(Throwable("终止啦"))
                        }
                    }
                }
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "==================onSubscribe ")
                    }

                    override fun onNext(s: String) {
                        Log.d(TAG, "==================onNext $s")
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "==================onError $e")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "==================onComplete ")
                    }
                })

        }

        /**
         * 重复发送被观察者的事件，times 为发送次数。
         */
        bt_repeat.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e ->
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onComplete()
            }).repeat(2)
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "===================onSubscribe ")
                    }

                    override fun onNext(integer: Int) {
                        Log.d(TAG, "===================onNext " + integer!!)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        Log.d(TAG, "===================onComplete ")
                    }
                })

        }

        /**
         * 这个方法可以会返回一个新的被观察者设定一定逻辑来决定是否重复发送事件。
         *
         * 这里分三种情况，如果新的被观察者返回 onComplete 或者 onError 事件，则旧的被观察者不会继续发送事件。如果被观察者返回其他事件，则会重复发送事件。
         */
        bt_repeatWhen.setOnClickListener {
            Observable.create(ObservableOnSubscribe<Int> { e ->
                e.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onComplete()
            }).repeatWhen (object:Function<Observable<Any>,ObservableSource<Any>>{
                override fun apply(t: Observable<Any>): ObservableSource<Any> {
                    return Observable.empty()
                    //  return Observable.error(new Exception("404"));
                    //  return Observable.just(4); null;
                }
            }).subscribe(object : Observer<Int> {
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

            /**
             * 指定被观察者的线程，要注意的时，如果多次调用此方法，只有第一次有效。
             */
            bt_subscribeOn.setOnClickListener {
                Observable.create(ObservableOnSubscribe<Int> { e ->
                    Log.d(
                        TAG,
                        "=========================currentThread name: " + Thread.currentThread().name
                    )
                    e.onNext(1)
                    e.onNext(2)
                    e.onNext(3)
                    e.onComplete()
                })
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(object : Observer<Int> {
                        override fun onSubscribe(d: Disposable) {
                            Log.d(TAG, "======================onSubscribe")
                        }

                        override fun onNext(integer: Int) {
                            Log.d(TAG, "======================onNext " + integer!!)
                        }

                        override fun onError(e: Throwable) {
                            Log.d(TAG, "======================onError")
                        }

                        override fun onComplete() {
                            Log.d(TAG, "======================onComplete")
                        }
                    })

            }

            /**
             * 指定观察者的线程，每指定一次就会生效一次。
             */
            bt_observeOn.setOnClickListener {
                Observable.just(1, 2, 3)
                    .observeOn(Schedulers.newThread())
                    .flatMap(object : Function<Int, ObservableSource<String>> {
                        override fun apply(integer: Int): ObservableSource<String> {
                            Log.d(
                                TAG,
                                "======================flatMap Thread name " + Thread.currentThread().name
                            )
                            return Observable.just("chan$integer")
                        }

                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<String> {
                        override fun onSubscribe(d: Disposable) {
                            Log.d(TAG, "======================onSubscribe")
                        }

                        override fun onNext(s: String) {
                            Log.d(
                                TAG,
                                "======================onNext Thread name " + Thread.currentThread().name
                            )
                            Log.d(TAG, "======================onNext $s")
                        }

                        override fun onError(e: Throwable) {
                            Log.d(TAG, "======================onError")
                        }

                        override fun onComplete() {
                            Log.d(TAG, "======================onComplete")
                        }
                    })

            }

        }
    }


}