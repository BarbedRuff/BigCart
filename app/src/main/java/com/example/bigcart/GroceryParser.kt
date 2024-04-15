package com.example.bigcart
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.File


class GroceryParser {
    fun initDatabase(database: String="/data/data/com.example.bigcart/databases/grocery.db", apiKey:String, apiHost:String){
        var dbFile = File(database)
        val fileExists = dbFile.exists()
        if(!fileExists){
            dbFile.getParentFile().mkdirs();
            dbFile.createNewFile()
            var sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(database, null)
            sqLiteDatabase.execSQL("CREATE TABLE products (\n" +
                    "\tfood_id STRING PRIMARY KEY,\n" +
                    "\tlabel STRING NOT NULL,\n" +
                    "\timage STRING\n" +
                    ");")
            sqLiteDatabase.execSQL(
                "CREATE TABLE favourite (\n" +
                        "\tfood_id STRING PRIMARY KEY\n" +
                        ");"
            )
            parser(apiKey, apiHost, sqLiteDatabase)
        }
    }

    fun parser(apiKey:String, apiHost:String, sqLiteDatabase: SQLiteDatabase){
        val client = OkHttpClient()
        var url = "https://edamam-food-and-grocery-database.p.rapidapi.com/api/food-database/v2/parser?nutrition-type=cooking&category%5B0%5D=generic-foods&health%5B0%5D=alcohol-free"
        for(i in 0..20){
            val request = Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", apiHost)
                .build()
            val response = client.newCall(request).execute()
            val jObject = JSONObject(response.body?.string())
            val foodList = jObject.getJSONArray("hints");
            for(i in 0..<foodList.length()){
                val foodProps = JSONObject(foodList[i].toString())
                val food = JSONObject(foodProps.get("food").toString())
                var insertValues = ContentValues()
                insertValues.put("food_id", food.get("foodId").toString())
                insertValues.put("image", if(food.has("image")) food.get("image").toString() else "")
                val label = food.get("label").toString()
                insertValues.put(
                    "label",
                    if(label.length > 10 ) label.substring(0, 10) + "..." else label
                )
                Log.d("MEOW", label);
                sqLiteDatabase.insert("products", null, insertValues)
            }
            url = JSONObject(JSONObject(jObject.get("_links").toString()).get("next").toString()).get("href").toString()
        }
    }
}