package com.duyi.rxjavademo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.duyi.rxjavademo.IntentSkip
import com.duyi.rxjavademo.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "目录"
        bt_basic.setOnClickListener {
            IntentSkip.skipTopRxJavaBasicActivity(this)
        }
        bt_create.setOnClickListener {
            IntentSkip.skipTopCreateActivity(this)
        }
        bt_transform.setOnClickListener {
            IntentSkip.skipTopTransformActivity(this)
        }
        bt_combination.setOnClickListener {
            IntentSkip.skipTopCombinationActivity(this)
        }
        bt_function.setOnClickListener {
            IntentSkip.skipTopFunctionActivity(this)
        }
        bt_filter.setOnClickListener {
            IntentSkip.skipTopFilterActivity(this)
        }
        bt_condition.setOnClickListener {
            IntentSkip.skipTopConditionActivity(this)
        }
    }
}
