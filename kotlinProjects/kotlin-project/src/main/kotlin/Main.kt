

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import spark.Spark.*
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class Message(var name: String, var message : String , var uuid: UUID , var date : String);
val gson = Gson()
val messages  : ArrayList<Message> = ArrayList();

fun main() {
    staticFileLocation("/public")
    webSocket("/chat/", ChatWSHandler::class.java)
    post("/chat/person/") { req, _ ->
        val currentDate = createTimeStap()
        val message = gson.fromJson(req.body(), Message::class.java)
        val uuid = UUID.randomUUID()
        if(message.name.isNullOrEmpty() || message.message.isNullOrEmpty()){
            halt(400 , "please pass the right data ")
        }
        message.date = currentDate;
        message.uuid = uuid;
        messages.add(message)
        ChatWSHandler.broadcastSingleMessage(message)
    }

    get("/chat/person/", { req, _ ->
        messages
    }, gson::toJson)

    delete("/chat/person/", { req, _ ->
        messages.clear()
    }, gson::toJson)

    delete("/chat/delete/", { req, _ ->
        messages.clear()
    }, gson::toJson)
}



fun createTimeStap(): String {
    val currentDate = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
        .withZone(ZoneOffset.UTC)
        .format(Instant.now())
    return currentDate
}



