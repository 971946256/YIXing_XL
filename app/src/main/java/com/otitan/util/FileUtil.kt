package com.otitan.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.otitan.model.CityModel
import java.io.*
import java.net.URL


class FileUtil {
    companion object {
        fun getCityId(cityName: String, context: Context): String {
            var inputStream: InputStream? = null
            var bos: ByteArrayOutputStream? = null
            try {
                inputStream = context.assets.open("cityid.json")
                bos = ByteArrayOutputStream()
                val bytes = ByteArray(4 * 1024)
                var len: Int
                do {
                    len = inputStream.read(bytes)
                    if (len == -1) {
                        break
                    }
                    bos.write(bytes, 0, len)
                } while (true)
                val json = String(bos.toByteArray())
                val type = object : TypeToken<List<CityModel>>() {}.type
                val cityList = Gson().fromJson<List<CityModel>>(json, type)
                cityList.forEach {
                    if (it.countyname.contains(cityName)) {
                        return it.areaid
                    }
                }
            } catch (e: Exception) {
                Log.e("tag", "read cityid error:$e")
            } finally {
                try {
                    inputStream?.close()
                    bos?.close()
                } catch (e: IOException) {
                    Log.e("tag", "closeErr:$e")
                }
            }
            return ""
        }

        fun saveImg(url: String, context: Context): String {
            try {
                val connection = URL(url).openConnection()
                connection.connectTimeout = 5000
                connection.useCaches = false
                val inputStream = connection.getInputStream()
                val bmp = BitmapFactory.decodeStream(inputStream)
//                inputStream.close()
                val array = url.split("/")
                val imgName = array[array.size - 1]
                val savePath = "${Environment.getExternalStorageDirectory()}/Android/data/${context.applicationContext.packageName}"
                val file = File(savePath)
                if (!file.exists()) {
                    file.mkdirs()
                }
                val img = File("$savePath/$imgName")
                if (img.exists()) {
                    return img.absolutePath
                }
                val outputStream = FileOutputStream("$savePath/$imgName")
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
                return "$savePath/$imgName"
            } catch (e: Exception) {
                Log.e("tag", "saveErr:$e")
                return ""
            }
        }

        fun getImgPath(url: String, context: Context): String {
            val array = url.split("/")
            val imgName = array[array.size - 1]
            val savePath = "${Environment.getExternalStorageDirectory()}/Android/data/${context.applicationContext.packageName}"
            val img = File("$savePath/$imgName")
            if (img.exists()) {
                return img.absolutePath
            }
            return ""
        }
    }
}