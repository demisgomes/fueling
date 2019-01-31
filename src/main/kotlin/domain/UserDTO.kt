package domain

import java.util.Date


data class UserDTO(val id:Int, val name:String, val email:String, val password:String, var phone:String, val gender:String, val birthDate: Date, val creationDate: Date?, val modificationDate: Date?, val admin:Boolean)