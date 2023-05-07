package tech.ineb.sport.lib.common.strava.client;

import com.google.gson.Gson;

public class JSON {
    public static Gson getGson() {
        return new Gson();
    }
}
