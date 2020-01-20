import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import spark.Spark
import java.util.*
import kotlin.collections.ArrayList

@WebSocket
class ChatWSHandler {
    companion object {
        val sessions: ArrayList<Session> = ArrayList()

        fun broadcastSingleMessage(message: Message) {
            sessions.forEach {
                emitSingle(it , message);
            }
        }

        fun emitSingle(session: Session, message : Message) {
            session.remote.sendString(jacksonObjectMapper().writeValueAsString(message))
        }
    }

    @OnWebSocketConnect
    fun connected(session: Session) {
        sessions.add(session);
        broadcastToAllUsers()
    }

    @OnWebSocketMessage
    fun message(session: Session, message: String) {
        val currentDate = createTimeStap();
        val uuid = UUID.randomUUID()
        val json = ObjectMapper().readTree(message)
        val message = Message(json.get("name").asText(), json.get("message").asText(), uuid, currentDate)
        if(message.name.isNullOrEmpty() || message.message.isNullOrEmpty()){
            Spark.halt(400, "please pass the right data ")
        }
        messages.add(message);
        broadcastSingleMessage(message);
    }

    @OnWebSocketClose
    fun disconnect(session: Session, code: Int, reason: String?) {
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