package com.semba.androidsamples.ArchCoroutines

import kotlinx.coroutines.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CoroutinesWork {


    fun executeNonBlocking() = runBlocking {
        println("1 from Thread ${Thread.currentThread().name}")

        val customDispatcher = Executors.newFixedThreadPool(2).asCoroutineDispatcher()
        this.launch(customDispatcher) {
            printDelayed("3 from Thread ${Thread.currentThread().name}")
        }
        println("4 from Thread ${Thread.currentThread().name}")
        (customDispatcher.executor as ExecutorService).shutdown()
    }

    suspend private fun printDelayed(msg: String) {
        delay(1000)
        println(msg)
    }

    fun exampleAsyncAwait() = runBlocking {
        val deferred1 = this.async {
            printDelayed("1 from Thread ${Thread.currentThread().name}")
        }
        deferred1.await()

        val deferred2 = withContext(Dispatchers.Default) {printDelayed("2 from Thread ${Thread.currentThread().name}")}
    }

    fun exampleLaunchGLobal() = runBlocking {

        val job = GlobalScope.launch {
            printDelayed("1 from Thread ${Thread.currentThread().name}")
        }

        printDelayed("2 from Thread ${Thread.currentThread().name}")
        job.join() // suspends current coroutine until job is done  -- Or remove "GlobalScope" and launch on this scope then we will not be in need to this line any more.
    }

    fun exampleDoStuffOnMainImmediately() = runBlocking {

        launch(Dispatchers.Main.immediate) {
            //UI Stuff
        }
    }

    fun exampleCancelAndExceptionHandler() = runBlocking {
        val job = Job()
        val exceptionHandler = CoroutineExceptionHandler{
            coroutineContext, throwable ->  //Broadcast the error
        }
        launch(Dispatchers.Default + job + exceptionHandler) {
            //This Coroutine is cancelable and is monitored against any Exception.
        }

        job.cancel()//Interrupt the Coroutine
    }

    fun exampleDeferredScopeAware() = runBlocking {
        val deferred1 = async(Dispatchers.Default) { /*GetFirstValue()*/ }
        val deferred2 = async(Dispatchers.IO) { /*GetSecondValue()*/ }
        useValues(deferred1.await(), deferred2.await())
    }

}