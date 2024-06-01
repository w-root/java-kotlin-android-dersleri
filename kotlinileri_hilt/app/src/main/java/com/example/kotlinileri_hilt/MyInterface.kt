package com.example.kotlinileri_hilt

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Singleton

interface MyInterface{
    fun printFunction() : String
}
//
//@InstallIn(ActivityComponent::class)
//@Module
//abstract class MyModule(){
//    @ActivityScoped
//    @Binds
//    abstract fun bindingFunction(myImplementer: InterfaceImplementer) : MyInterface
//}

@InstallIn
@Module
abstract class MyModule(){

    @Singleton
    @Provides
    fun bindingFunction() : MyInterface{
        return InterfaceImplementer()
    }
}