package com.codepalace.chatbot.ui

import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codepalace.chatbot.Api.RetrofitBuilder
import com.codepalace.chatbot.Data.User
import com.codepalace.chatbot.Dto.CorpusDto
import com.codepalace.chatbot.Dto.CorpusDto2
import com.codepalace.chatbot.R
import com.codepalace.chatbot.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var binding: ActivityRegisterBinding

class Register : AppCompatActivity() {

    var id: String = ""
    var pw: String = ""
    var auth: String = ""
    var name: String = ""
    var phone: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        id = userId.text.toString()
        pw = userPassword.text.toString()
        auth = userAuth.text.toString()
        phone = userPhone.text.toString()
        name = userName.text.toString()

        binding.btnSign.setOnClickListener {
            val user = User()
            user.user_id = userId.text.toString()
            user.user_pw = userPassword.text.toString()
            user.user_auth = userAuth.text.toString()
            user.user_phone = userPhone.text.toString()
            user.user_name = userName.text.toString()

            Log.d("BUTTON CLICKED", "id: " + user.user_id + ", pw: " + user.user_pw)
            Signup(user)
        }

        binding.btnUpdate.setOnClickListener {
            val user = User()
            user.user_id = userId.text.toString()
            user.user_pw = userPassword.text.toString()
            user.user_phone = userPhone.text.toString()
            user.user_name = userName.text.toString()

            Log.d("BUTTON CLICKED", "id: " + user.user_id + ", pw: " + user.user_pw)
            Update(user)
        }    //???????????? ?????? ?????? ???

        binding.btnDelete.setOnClickListener {
            val user = User()
            user.user_id = userId.text.toString()
            Delete(user)
        }    //???????????? ?????? ?????? ???

        binding.btnUserlist.setOnClickListener { //???????????? ?????????
            Userlist(); //????????????
        }

        binding.btnCorpus.setOnClickListener {
            Corpuslist();
        }
        binding.btnSoundTest.setOnClickListener {

        }
        binding.btnAccept.setOnClickListener {
            startActivity(Intent(this@Register,Chatbot::class.java).putExtra("stage", "accept"))
        }
        binding.btnBargain.setOnClickListener {
            startActivity(Intent(this@Register,Chatbot::class.java).putExtra("stage", "bargain"))
        }
        binding.btnRefuse.setOnClickListener {
            startActivity(Intent(this@Register,Chatbot::class.java).putExtra("stage", "refuse"))
        }

    }
    fun Signup(user: User){
        val call = RetrofitBuilder.userapi.postSignupResponse(user)
        call.enqueue(object : Callback<String> { // ????????? ?????? ?????? ?????????
            override fun onResponse( // ????????? ????????? ??????
                call: Call<String>,
                response: Response<String>
            ) {
                if(response.isSuccessful()){ // ?????? ??? ?????? ??????
                    Log.d("RESPONSE_OK: ", response.body().toString())
                    var t1 = Toast.makeText(this@Register, "??????", Toast.LENGTH_SHORT)
                    t1.show()

                }else{
                    // ?????? ?????? but ?????? ??????
                    Log.d("RESPONSE_NO", "FAILURE")
                    var t1 = Toast.makeText(this@Register, "??????", Toast.LENGTH_SHORT)
                    t1.show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // ????????? ????????? ??????
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
            }
        })
    }

    fun Userlist(){
        val textviewresult= binding.textViewResult
        val call = RetrofitBuilder.userapi.getUserlistResponse()
        call.enqueue(object : Callback<String> { // ????????? ?????? ?????? ?????????
            override fun onResponse( // ????????? ????????? ??????
                call: Call<String>,
                response: Response<String>
            ) {
                if(response.isSuccessful()){ // ?????? ??? ?????? ??????
                    Log.d("RESPONSE: ", response.body().toString())
                    textviewresult.setText(response.body().toString())
                }else{
                    // ?????? ?????? but ?????? ??????
                    Log.d("RESPONSE", "FAILURE")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // ????????? ????????? ??????
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
            }
        })
    }


    fun Update(user: User){
        val call = RetrofitBuilder.userapi.postUpdateResponse(user.user_id,user.user_pw.toString(),user.user_phone.toString(),user.user_name.toString(),)
        call.enqueue(object : Callback<String> { // ????????? ?????? ?????? ?????????
            override fun onResponse( // ????????? ????????? ??????
                call: Call<String>,
                response: Response<String>
            ) {
                if(response.isSuccessful()){ // ?????? ??? ?????? ??????
                    Log.d("RESPONSE: ", response.body().toString())

                }else{
                    // ?????? ?????? but ?????? ??????
                    Log.d("RESPONSE", "FAILURE")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // ????????? ????????? ??????
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
            }
        })
    }

    fun Delete(user: User){
        val call = RetrofitBuilder.userapi.deleteUserResponse(user.user_id.toString())
        call.enqueue(object : Callback<String> { // ????????? ?????? ?????? ?????????
            override fun onResponse( // ????????? ????????? ??????
                call: Call<String>,
                response: Response<String>
            ) {
                if(response.isSuccessful()){ // ?????? ??? ?????? ??????
                    Log.d("RESPONSE: ", response.body().toString())

                }else{
                    // ?????? ?????? but ?????? ??????
                    Log.d("RESPONSE", "FAILURE")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // ????????? ????????? ??????
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
            }
        })
    }

    fun Corpuslist(){
        val textviewresult= binding.textViewResult
        val call = RetrofitBuilder.corpusapi.getAllByMaincategoryResponse("??????")
        call.enqueue(object : Callback<List<CorpusDto>> { // ????????? ?????? ?????? ?????????
            override fun onResponse( // ????????? ????????? ??????
                call: Call<List<CorpusDto>>,
                response: Response<List<CorpusDto>>
            ) {
                if(response.isSuccessful()){ // ?????? ??? ?????? ??????
                    println("response.body()!!.size = ${response.body()!!.size}")

                    val total=java.lang.StringBuilder();
                    for(i in 0 until response.body()!!.size){
                        val sb=java.lang.StringBuilder();
                        sb.append(response.body()?.get(i)?.corpus_id)
                        sb.append(response.body()?.get(i)?.status_keyword)
                        sb.append(response.body()?.get(i)?.emotion_maincategory)
                        sb.append(response.body()?.get(i)?.emotion_subcategory)
                        sb.append(response.body()?.get(i)?.system_response1)
                        sb.append(response.body()?.get(i)?.human_sentence2)

                        total.append(sb)
                        if(i==4){
                            textviewresult.setText(total)
                        }
                    }

                    val result=response.body()?.get(0)?.system_response1
                   // println("result = ${result}")
                }else{
                    // ?????? ?????? but ?????? ??????
                    Log.d("RESPONSE", "FAILURE")
                }
            }

            override fun onFailure(call: Call<List<CorpusDto>>, t: Throwable) {
                // ????????? ????????? ??????
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
            }
        })
    }

    fun Corpuslist2(){
        val textviewresult= binding.textViewResult
        val call = RetrofitBuilder.corpusapi.getAllByMaincategoryResponse("??????")
        call.enqueue(object : Callback<List<CorpusDto>> { // ????????? ?????? ?????? ?????????
            override fun onResponse( // ????????? ????????? ??????
                call: Call<List<CorpusDto>>,
                response: Response<List<CorpusDto>>
            ) {
                if(response.isSuccessful()){ // ?????? ??? ?????? ??????
                    val corpus = CorpusDto2(response.body()?.get(0)!!.system_response1)
                    sendData(corpus)
                }else{
                    // ?????? ?????? but ?????? ??????
                    Log.d("RESPONSE", "FAILURE")
                }
            }

            override fun onFailure(call: Call<List<CorpusDto>>, t: Throwable) {
                // ????????? ????????? ??????
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
            }
        })
    }

    fun sendData(corpus: CorpusDto2): CorpusDto2{
        return corpus;
    }
}