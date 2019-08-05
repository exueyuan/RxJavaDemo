package com.duyi.rxjavademo

import android.content.Context
import android.content.Intent
import com.duyi.rxjavademo.activity.*

class IntentSkip {
    companion object {
        fun skipTopRxJavaBasicActivity(context: Context) {
            val intent = Intent(context, RxJavaBasicActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        fun skipTopCreateActivity(context: Context) {
            val intent = Intent(context, CreateActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        fun skipTopTransformActivity(context: Context) {
            val intent = Intent(context, TransformActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        fun skipTopCombinationActivity(context: Context) {
            val intent = Intent(context, CombinationActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        fun skipTopFunctionActivity(context: Context) {
            val intent = Intent(context, FunctionActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        fun skipTopFilterActivity(context: Context) {
            val intent = Intent(context, FilterActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        fun skipTopConditionActivity(context: Context) {
            val intent = Intent(context, ConditionActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}