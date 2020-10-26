package org.csuf.cpsc411

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.utils.io.*
import org.csuf.cpsc411.Dao.claim.Claim
import org.csuf.cpsc411.Dao.claim.ClaimDao
import java.lang.reflect.Type
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    routing {
        get("/ClaimService/getAll"){
            val claimList = ClaimDao().getAll()
            println("The number of Claims : ${claimList.size}")
            // JSON Serialization/Deserialization
            val respJsonStr = Gson().toJson(claimList)
            call.respondText(respJsonStr, status= HttpStatusCode.OK, contentType= ContentType.Application.Json)
        }

        post("/ClaimService/add"){
            val contType = call.request.contentType()
            val data = call.request.receiveChannel()
            val dataLength = data.availableForRead
            var output = ByteArray(dataLength)
            data.readAvailable(output)
            val str = String(output)

            // JSON serialization/deserialization
            // GSON (Google Library)
            val gsonString = Gson().fromJson(str, Claim::class.java)
            val cObj = Claim(UUID.randomUUID(), gsonString.title, gsonString.date, isSolved = false)
            val dao = ClaimDao().addClaim(cObj)

            println("HTTP message is using POST method with /post ${contType} ${str}")
            call.respondText("The POST request was successfully processed. ",
                    status= HttpStatusCode.OK, contentType = ContentType.Text.Plain)
        }
    }
}

