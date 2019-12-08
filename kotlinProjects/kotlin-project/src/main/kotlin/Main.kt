

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


class Message(val name: String, val message : String , val uuid: UUID , val date : String);
data class Person(var uuid :UUID, val name : String, val message : String, var date : String)


val gson = Gson()
val list: ArrayList<Person> = ArrayList()

fun main() {

    staticFileLocation("/public")
    webSocket("/chat/", ChatWSHandler::class.java)
    post("/chat/person/") { req, _ ->
        val currentDate = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())
        val person = gson.fromJson(req.body(), Person::class.java)

        val uuid = UUID.randomUUID()


        if(person.name.isNullOrEmpty() || person.message.isNullOrEmpty()){
            halt(400 , "validation error ")
        }

        println(uuid);
        person.date = currentDate;
        person.uuid = uuid;
        println(person)
        list.add(person)
    }

    get("/chat/person/", { req, _ ->
        list
    }, gson::toJson)

    delete("/chat/person/", { req, _ ->
        list.clear()
    }, gson::toJson)

    delete("/chat/delete/", { req, _ ->
        list.clear()
    }, gson::toJson)


}


@WebSocket
class ChatWSHandler {

    val listOfMessage = HashMap<Session, Message>()

    @OnWebSocketConnect
    fun connected(session: Session) {
        session.remote.sendString(jacksonObjectMapper().writeValueAsString(listOfMessage))
        println("session connected")
    }

    @OnWebSocketMessage
    fun message(session: Session, message: String) {
        println("on message")
        val currentDate = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())


        val uuid = UUID.randomUUID()
        val json = ObjectMapper().readTree(message)
        val message = Message(json.get("name").asText() , json.get("message").asText() , uuid , currentDate)
        listOfMessage.put(session , message);
        session.remote.sendString(jacksonObjectMapper().writeValueAsString(listOfMessage))
        println("json msg ${message}")

        emit(session , message)
        broadcastToOthers(session, message)
    }

    @OnWebSocketClose
    fun disconnect(session: Session, code: Int, reason: String?) {
        println("disconnect")
    }

    fun emit(session: Session, message: Message) = session.remote.sendString(jacksonObjectMapper().writeValueAsString(message))
    fun broadcastToOthers(session: Session, message: Message) = listOfMessage.filter{ it.key != session }.forEach {
    emit(it.key , message);
}


}




