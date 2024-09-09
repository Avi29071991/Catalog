package com.avinash.zapcom.demo.base.domain

import io.reactivex.rxjava3.core.Scheduler
import java.util.concurrent.Executor

interface ThreadExecutor : Executor

interface PostExecutionThread {
    fun scheduler(): Scheduler
}
