package service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import exception.UserException

class GenericService{
    val objectMapper= jacksonObjectMapper()
    val getUrl="http://www.mocky.io/v2/5c53456d3200005000f7f1e5"
    val postUrl="http://localhost:7000/users"
    val loginUrl="http://localhost:8080/login"

    inline fun <reified T> delete(id:Int): GenericResponse<T>{
        val (_,response,result) =
                Fuel.delete("$postUrl/$id").response()
        if(!response.isSuccessful){
            val errorMessage=ObjectMapper().readTree(response.data)
            throw UserException("error on DELETE\nStatus: ${response.statusCode}\nError: $errorMessage")
        }
        val responseData=objectMapper.readValue(result.get(), T::class.java)
        return GenericResponse(response.statusCode, responseData, response.responseMessage)
    }


    inline fun <reified T> put(id:Int, requestObject: T): GenericResponse<T>{
        val (_,response,result) =
                Fuel.put("$postUrl/$id").jsonBody(ObjectMapper().writeValueAsString(requestObject)).response()
        if(!response.isSuccessful){
            val errorMessage=ObjectMapper().readTree(response.data)
            throw UserException("error on PUT\nStatus: ${response.statusCode}\nError: $errorMessage")
        }
        val responseData=objectMapper.readValue(result.get(), T::class.java)
        return GenericResponse(response.statusCode, responseData, response.responseMessage)
    }


    inline fun <reified T, reified S> post(requestObject: T): GenericResponse<S>{
        val (_,response,result) =
                Fuel.post(postUrl).jsonBody(ObjectMapper().writeValueAsString(requestObject)).response()
        if(!response.isSuccessful){
            val errorMessage=ObjectMapper().readTree(response.data)
            throw UserException("error on POST\nStatus: ${response.statusCode}\nError: $errorMessage")
        }
        val responseData=objectMapper.readValue(result.get(), S::class.java)
        return GenericResponse(response.statusCode, responseData, response.responseMessage)
    }

    inline fun <reified T, reified S> post(requestObject: T, headers:Map<String,String>): GenericResponse<S>{
        val (_,response,result) =
                Fuel.post(loginUrl).header(headers).jsonBody(ObjectMapper().writeValueAsString(requestObject)).response()
        if(!response.isSuccessful){
            val errorMessage=ObjectMapper().readTree(response.data)
            throw UserException("error on POST\nStatus: ${response.statusCode}\nError: $errorMessage")
        }
        val responseData=objectMapper.readValue(result.get(), S::class.java)
        return GenericResponse(response.statusCode, responseData, response.responseMessage)
    }


    inline fun <reified T, reified S> post2(requestObject: T): GenericResponse<S>{
        val (_,response,result) =
                postUrl.httpPost().jsonBody(ObjectMapper().writeValueAsString(requestObject)).response()
        if(!response.isSuccessful){
            val errorMessage=ObjectMapper().readTree(response.data)
            throw UserException("error on POST\nStatus: ${response.statusCode}\nError: $errorMessage")
        }
        val responseData=objectMapper.readValue(result.get(), S::class.java)
        return GenericResponse(response.statusCode, responseData, response.responseMessage)
    }

    inline fun <reified T> get():GenericResponse<T>{
        val (_,response,result) =
                Fuel.get(getUrl).response()
        //val (_,response,result) = getUrl.httpGet().response()
        if(!response.isSuccessful){
            val errorMessage=ObjectMapper().readTree(response.data)
            throw UserException("error on GET\nStatus: ${response.statusCode}\nError: $errorMessage")
        }
        val responseData=objectMapper.readValue(result.get(), T::class.java)
        return GenericResponse(response.statusCode, responseData, response.responseMessage)
    }

}