package service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import exception.UserException

class GenericService{
    val objectMapper= jacksonObjectMapper()
    val getUrl="http://www.mocky.io/v2/5c53456d3200005000f7f1e5"
    val postUrl="http://localhost:7000/users"
    val loginUrl="http://localhost:8080/login"

    inline fun <reified T> delete(id:Int): GenericResponse<T>{
        val (_,response,result) =
                Fuel.delete("$postUrl/$id").response()
        return responseHandler(response,result,"DELETE")
    }


    inline fun <reified T> put(id:Int, requestObject: T): GenericResponse<T>{
        val (_,response,result) =
                Fuel.put("$postUrl/$id").jsonBody(ObjectMapper().writeValueAsString(requestObject)).response()
        return responseHandler(response,result,"PUT")
    }


    inline fun <reified T, reified S> post(requestObject: T): GenericResponse<S>{
        val (_,response,result) =
                Fuel.post(postUrl).jsonBody(ObjectMapper().writeValueAsString(requestObject)).response()
        return responseHandler(response,result,"POST")
    }

    inline fun <reified T, reified S> post(requestObject: T, headers:Map<String,String>): GenericResponse<S>{
        val (_,response,result) =
                Fuel.post(loginUrl).header(headers).jsonBody(ObjectMapper().writeValueAsString(requestObject)).response()
        return responseHandler(response,result,"POST")
    }


    inline fun <reified T, reified S> post2(requestObject: T): GenericResponse<S>{
        val (_,response,result) =
                postUrl.httpPost().jsonBody(ObjectMapper().writeValueAsString(requestObject)).response()
        return responseHandler(response,result,"POST")
    }

    inline fun <reified T> get():GenericResponse<T>{
        val (_,response,result) =
                Fuel.get(getUrl).response()
        return responseHandler(response,result,"GET")
    }

    inline fun<reified T>  responseHandler(response: Response, result:Result<ByteArray,FuelError>,method:String): GenericResponse<T> {
        if(!response.isSuccessful){
            val errorMessage=ObjectMapper().readTree(response.data)
            throw UserException("error on $method\nStatus: ${response.statusCode}\nError: $errorMessage")
        }
        val responseData=objectMapper.readValue(result.get(), T::class.java)
        return GenericResponse(response.statusCode, responseData, response.responseMessage)
    }
}

