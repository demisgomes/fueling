package domain

import com.fasterxml.jackson.annotation.JsonIgnore

class Phone (@JsonIgnore val id:Int?, val ddd: Int, val number: String)
