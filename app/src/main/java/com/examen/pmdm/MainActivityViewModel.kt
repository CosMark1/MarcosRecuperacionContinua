package com.examen.pmdm

import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException

class MainActivityViewModel : ViewModel() {
    private val _isVisible by lazy { MediatorLiveData<Boolean>() }
    val isVisible: LiveData<Boolean>
        get() = _isVisible

    private val _responseText by lazy { MediatorLiveData<String>() }
    val responseText: LiveData<String>
        get() = _responseText

    private val _items by lazy { MediatorLiveData<List<Personaje>>() }
    val items: LiveData<List<Personaje>>
        get() = _items

    suspend fun setIsVisibleInMainThread(value: Boolean) = withContext(Dispatchers.Main) {
        _isVisible.value = value
    }

    suspend fun setResponseTextInMainThread(value: String) = withContext(Dispatchers.Main) {
        _responseText.value = value
    }
/*
    suspend fun setItemsTextInMainThread(livedata: MediatorLiveData<List<Result>>) = withContext(Dispatchers.Main) {
        _items.value = livedata
    }
 */
    fun setItems(value : Personaje){
        _items.value = _items.value?.plus(value) ?: listOf(value)
    }

    fun peticionApiPersonajes() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                setIsVisibleInMainThread(true)
                val client = OkHttpClient()

                val request = Request.Builder()
                request.url("https://swapi.dev/api/people/")

                val call = client.newCall(request.build())
                call.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println(e.toString())
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(2000)
                            setResponseTextInMainThread("Algo ha ido mal")
                            setIsVisibleInMainThread(false)
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        println(response.toString())
                        response.body?.let { responseBody ->
                            val body = responseBody.string()
                            println(body)
                            val gson = Gson()

                            var planet = gson.fromJson(body, Resultados::class.java)
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(2000)
                                setResponseTextInMainThread("Hemos obtenido ${planet.results.size} personajes")
                                planet.results.forEach {
                                    setItems(it)
                                }
                                setIsVisibleInMainThread(false)
                            }
                        }
                    }
                })
            }
        }
    }

}