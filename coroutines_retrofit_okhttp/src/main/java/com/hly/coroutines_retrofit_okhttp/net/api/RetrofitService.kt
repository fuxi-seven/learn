import retrofit2.http.GET

interface RetrofitService {

    @GET(Api.json)
    suspend fun json(): JsonBean

}
