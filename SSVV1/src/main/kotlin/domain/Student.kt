package domain

data class Student(override var id: String, var name: String, var group: Int) : HasID<String>