import com.github.kittinunf.fuel.Fuel
import domain.User
import domain.UserDTO
import service.GenericService


fun main(args: Array<String>) {
    val str="----------------------------------"
    val userService= GenericService()
    //no inline
    //val userDTO=userService.getNoInline()q
    //inline

//    val third=Fuel.get("https://reqres.in/api/users?page=2").header(mapOf("User-Agent" to "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")).response().third
//    println(third)



    println("GET REQUEST")
    println(str)

    val getRequest=userService.get<User>()

    val getStatus=getRequest.statusCode
    val getMessage=getRequest.message
    val getUser=getRequest.data

    println(getStatus)
    println(getMessage)
    println(getUser)

    println()

    println("POST REQUEST")
    println(str)
    val postRequest=userService.post<User,UserDTO>(getUser)
    val postStatus=postRequest.statusCode
    val postMessage=postRequest.message
    val postUser=postRequest.data

    println(postStatus)
    println(postMessage)
    println(postUser)

    println()

    println("PUT REQUEST")
    println(str)

    postRequest.data.phone+="9"

    val putRequest = userService.put(postUser.id, postUser)

    val putStatus=putRequest.statusCode
    val putMessage=putRequest.message
    val putUser=putRequest.data

    println(putStatus)
    println(putMessage)
    println(putUser)


    println()
    println("DELETE REQUEST")
    println(str)

    val deleteRequest = userService.delete<UserDTO>(putUser.id)

    val deletedStatus=deleteRequest.statusCode
    val deletedMessage=deleteRequest.message
    val deletedUser=deleteRequest.data

    println(deletedStatus)
    println(deletedMessage)
    println(deletedUser)


    // val postMessageWithToken=userService.post<User,UserDTO>(user, mapOf("token" to "16bbc981-92ed-4a0f-90e2-f7c4d91a4220"))
   // print(postMessageWithToken)


}
