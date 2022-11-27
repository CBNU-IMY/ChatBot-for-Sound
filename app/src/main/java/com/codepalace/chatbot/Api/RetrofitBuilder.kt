package com.codepalace.chatbot.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitBuilder {
    var userapi: UserApi
    var corpusapi: CorpusApi
    init{
        val retrofit= Retrofit.Builder()
            //.baseUrl("http://113.198.137.188:81/")   //요청 보내는 API 서버 url /로 끝나야 함
            //.baseUrl("http://172.17.0.4:8080/")//my docker location
            //.baseUrl("http://49.143.65.133:8080/")// my 공유기 location
            .baseUrl("http://113.198.137.200:8080/")// nova 공유기 location
            .addConverterFactory(ScalarsConverterFactory.create() )
            .addConverterFactory(GsonConverterFactory.create())//Gson을 역직렬화
            .build()
        userapi=retrofit.create(UserApi::class.java)
        corpusapi=retrofit.create(CorpusApi::class.java)


    }
}