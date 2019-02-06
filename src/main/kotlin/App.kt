import domain.User
import domain.UserDTO
import service.GenericResponse
import service.GenericService

const val str="----------------------------------"

inline fun <reified T> printRequest(method:String, response:GenericResponse<T>){
    println("$method REQUEST")
    println(str)
    val status=response.statusCode
    val message=response.message
    val data=response.data

    println(status)
    println(message)
    println(data)

    println()

}

fun main(args: Array<String>) {

    val userService= GenericService()
    //no inline
    //val userDTO=userService.getNoInline()q
    //inline

//    val third=Fuel.get("https://reqres.in/api/users?page=2").header(mapOf("User-Agent" to "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")).response().third
//    println(third)



    val getRequest=userService.get<User>()
    printRequest("GET", getRequest)

    val postRequest=userService.post<User,UserDTO>(getRequest.data)
    printRequest("POST", postRequest)

    postRequest.data.phone+="9"
    val putRequest = userService.put(postRequest.data.id, postRequest.data)
    printRequest("PUT", putRequest)

    val deleteRequest = userService.delete(putRequest.data.id)
    printRequest("DELETE",deleteRequest)


    // val postMessageWithToken=userService.post<User,UserDTO>(user, mapOf("token" to "16bbc981-92ed-4a0f-90e2-f7c4d91a4220"))
   // print(postMessageWithToken)


}
