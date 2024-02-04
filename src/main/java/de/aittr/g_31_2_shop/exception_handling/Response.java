package de.aittr.g_31_2_shop.exception_handling;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Response {

    @JsonProperty("errorTime")
    private final long timestamp;
    private String message;

    public Response(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public Response(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public String getMessage() {
        return message;
    }


    @JsonProperty("errorTime")
    public String getFormattedTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp);
        return sdf.format(date);
    }
}

