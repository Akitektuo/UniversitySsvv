package repository

import domain.Student
import validation.StudentValidator

class StudentMemoryRepository : AbstractMemoryRepository<String, Student>(StudentValidator())