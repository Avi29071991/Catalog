package com.avinash.zapcom.demo.base.domain

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UIThread : PostExecutionThread {
    override fun scheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}

class JobExecutor @Inject constructor() : ThreadExecutor {
    private val threadPoolExecutor = ThreadPoolExecutor(
        CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_THREAD, TimeUnit.SECONDS,
        LinkedBlockingDeque(), JobThreadFactory()
    )

    private class JobThreadFactory : ThreadFactory {
        private var counter = 0
        override fun newThread(r: Runnable?): Thread {
            return Thread(r, "$BASE_NAME_THREAD ${counter++}")
        }
    }

    override fun execute(command: Runnable?) {
        command?.run{
            threadPoolExecutor.execute(this)
        }
    }

    companion object {
        private const val CORE_POOL_SIZE = 3
        private const val MAXIMUM_POOL_SIZE = 5
        private const val KEEP_ALIVE_THREAD: Long = 10
        private const val BASE_NAME_THREAD = "zapcom_"
    }
}
