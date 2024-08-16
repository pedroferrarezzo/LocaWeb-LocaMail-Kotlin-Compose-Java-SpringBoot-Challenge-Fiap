package br.com.fiap.locawebmailapp.adapter

import android.util.Base64
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class ByteArrayBase64Adapter : TypeAdapter<ByteArray>() {
    override fun write(out: JsonWriter, value: ByteArray) {
        out.value(Base64.encodeToString(value, Base64.NO_WRAP))
    }

    override fun read(`in`: JsonReader): ByteArray {
        val base64String = `in`.nextString()
        return Base64.decode(base64String, Base64.NO_WRAP)
    }
}