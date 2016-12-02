package com.belatrix.pickmeup.util;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import com.belatrix.pickmeup.model.MyUser;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by juanp on 16/11/16.
 */

public class MyUserDeserializer implements JsonDeserializer<MyUser>  {

    @Override
    public MyUser deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jobject = (JsonObject) json;
        MyUser entryUser = new MyUser();
        for (Map.Entry<String, JsonElement> entryMember : jobject.entrySet())
        {
            entryUser = new Gson().fromJson(entryMember.getValue(), MyUser.class);
            entryUser.setId(entryMember.getKey().toString());
        }
        return entryUser;
    }
}
