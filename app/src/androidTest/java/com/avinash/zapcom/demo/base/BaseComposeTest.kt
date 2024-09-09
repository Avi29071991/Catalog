package com.avinash.zapcom.demo.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.IdlingRegistry
import com.avinash.zapcom.demo.base.domain.JobExecutor
import com.avinash.zapcom.demo.base.domain.PostExecutionThread
import com.avinash.zapcom.demo.base.domain.ThreadExecutor
import com.avinash.zapcom.demo.base.domain.UIThread
import com.avinash.zapcom.demo.di.NullOnEmptyConverterFactory
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class BaseComposeTest {

    @get:Rule
    val testSchedulerRule = RxImmediateSchedulerRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()

    val threadExecutor: ThreadExecutor = JobExecutor()
    val postExecutionThread: PostExecutionThread = UIThread()

    private val client = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()

    fun buildMockRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Before
    open fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        mockWebServer.start(8080)
    }

    fun enqueueMockResponse(fileName: String, code: Int) {
        javaClass.classLoader?.getResourceAsStream(fileName)?.readBytes()?.run {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(code)
                    .setBody(String(this))
            )
        }
    }

    fun enqueueMockResponse(requestList: Map<String, String>) {
        mockWebServer.dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                var responseBody: ByteArray? = null
                requestList.forEach { (key, value) ->
                    if (request.path?.contains(key) == true) {
                        responseBody = javaClass.classLoader?.getResourceAsStream(value)?.readBytes()
                    }
                }
                val mockResponse = MockResponse().setResponseCode(200)
                responseBody?.run {
                    mockResponse.setBody(String(this))
                }

                return mockResponse
            }
        }
    }

    fun enqueueMockResponseData(data: String, code: Int) {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(data)
        )
    }

    @After
    open fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        mockWebServer.shutdown()
    }

    open fun performTask(timeInMillis: Long = 1500L, task: () -> Unit) {
        Thread.sleep(timeInMillis)
        task()
    }
}