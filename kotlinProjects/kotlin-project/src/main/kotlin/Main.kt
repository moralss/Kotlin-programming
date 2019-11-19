import com.google.gson.Gson
import spark.Spark.*
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.UUID
import kotlin.collections.ArrayList


data class Person(var uuid :UUID, val name : String, val message : String, var date : String)

val gson = Gson()
val list: ArrayList<Person> = ArrayList()

fun main(args: Array<String>) {
    post("/person/") { req, _ ->
        val currentDate = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())

        val uuid = UUID.randomUUID()

        println(uuid);
        val person = gson.fromJson(req.body(), Person::class.java)
        person.date = currentDate;
        person.uuid = uuid;
        println(person)
        list.add(person)
        halt(201)
    }

    get("/person/", { req, _ ->
        list
    }, gson::toJson)

    delete("/person/", { req, _ ->
        list.clear()
    }, gson::toJson)
}


