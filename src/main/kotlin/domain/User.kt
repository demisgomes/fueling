package domain

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class User(val name:String, val email:String, val password:String, val admin:Boolean, val phone:String, val gender:String, val birthDate: Date)