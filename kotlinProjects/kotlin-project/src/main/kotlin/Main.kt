

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
val listOfMessages :  ArrayList<Message> = ArrayList();

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
    }

    get("/chat/person/", { req, _ ->
        listOfMessages
    }, gson::toJson)

    delete("/chat/person/", { req, _ ->
        listOfMessages.clear()
    }, gson::toJson)

    delete("/chat/delete/", { req, _ ->
        messages.clear()
    }, gson::toJson)
}

@WebSocket
class ChatWSHandler {
    val sessions: ArrayList<Session> = ArrayList()

    @OnWebSocketConnect
    fun connected(session: Session) {
        sessions.add(session);
        broadcastToAllUsers()
        println("session connected")
    }

    @OnWebSocketMessage
    fun message(session: Session, message: String) {
        println("on message")
        val currentDate = createTimeStap();
        val uuid = UUID.randomUUID()
        val json = ObjectMapper().readTree(message)
        val message = Message(json.get("name").asText(), json.get("message").asText(), uuid, currentDate)
        if(message.name.isNullOrEmpty() || message.message.isNullOrEmpty()){
            halt(400 , "please pass the right data ")
        }
            messages.add(message);
            broadcastToAllUsers()
    }

    @OnWebSocketClose
    fun disconnect(session: Session, code: Int, reason: String?) {
        println("disconnect")
        println(sessions.indexOf(session));
        sessions.removeAt(sessions.indexOf(session))
    }

    fun broadcastToAllUsers() {
        sessions.forEach {
            emit(it);
        }
    }

    fun emit(session: Session) {
        session.remote.sendString(jacksonObjectMapper().writeValueAsString(messages))
    }
}

private fun createTimeStap(): String {
    val currentDate = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
        .withZone(ZoneOffset.UTC)
        .format(Instant.now())
    return currentDate
}



