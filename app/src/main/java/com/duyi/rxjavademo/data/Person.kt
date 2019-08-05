package com.duyi.rxjavademo.data

class Person(var name: String?, planList: List<Plan>) {
    var planList: List<Plan> = ArrayList()

    init {
        this.planList = planList
    }

}

class Plan(var time: String?, var content: String?) {
    var actionList: List<String> = ArrayList()
}

