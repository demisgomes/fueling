package service

data class GenericResponse<T>(val statusCode:Int, val data: T, val message:String)