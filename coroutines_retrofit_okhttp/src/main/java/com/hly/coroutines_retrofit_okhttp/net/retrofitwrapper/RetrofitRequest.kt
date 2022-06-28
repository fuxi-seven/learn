import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit

class RetrofitRequest private constructor() {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .client(RetrofitUtil.genericClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RetrofitUtil.baseUrl)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }
    val retrofitService: RetrofitService by lazy {
        retrofit.create(RetrofitService::class.java)
    }


    companion object {
        val instance: RetrofitRequest by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { RetrofitRequest() }
    }
}