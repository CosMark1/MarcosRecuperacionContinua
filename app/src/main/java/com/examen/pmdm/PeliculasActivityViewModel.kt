package com.examen.pmdm

import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException

class PeliculasActivityViewModel : ViewModel() {
    private val _isVisible by lazy { MediatorLiveData<Boolean>() }
    val isVisible: LiveData<Boolean>
        get() = _isVisible

    private val _responseText by lazy { MediatorLiveData<String>() }
    val responseText: LiveData<String>
        get() = _responseText

    private val _itemsPeliculas by lazy { MediatorLiveData<List<Peliculas>>() }
    val itemsPeliculas: LiveData<List<Peliculas>>
        get() = _itemsPeliculas

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
    fun setItems(value : Peliculas){
        _itemsPeliculas.value = _itemsPeliculas.value?.plus(value) ?: listOf(value)
    }

    fun peticionApiPeliculas() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                setIsVisibleInMainThread(true)
                val client = OkHttpClient()

                val request = Request.Builder()
                request.url("https://swapi.dev/api/films/")

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

                            var planet = gson.fromJson(body, ResultadosPeliculas::class.java)
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(2000)
                                setResponseTextInMainThread("Hemos obtenido ${planet.results.size} peliculas")
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