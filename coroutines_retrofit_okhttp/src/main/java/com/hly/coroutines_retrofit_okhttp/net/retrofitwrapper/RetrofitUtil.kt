import android.text.TextUtils

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient


object RetrofitUtil {
    private const val DEFAULT_TIMEOUT = 10

    const val baseUrl: String = Api.baseUrl

    /**
     * 修改http头文件
     *
     * @return
     */
    fun genericClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val builder = chain.request().newBuilder()
                    val headerMap = HeaderUtils.generalHeaders()
                    if (headerMap != null) {
                        for ((key, value) in headerMap) {
                            if (!TextUtils.isEmpty(key)) {
                                builder.addHeader(key, value)
                            }
                        }
                    }
                    val request = builder.build()
                    chain.proceed(request)
                }.connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS).retryOnConnectionFailure(false)
                .build()
    }


}
