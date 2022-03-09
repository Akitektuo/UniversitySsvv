package validation

import domain.Student

val GROUP_REGEX = Regex("[2-9][1-3][1-7]")

class StudentValidator : Validator<Student> {
    override fun validate(student: Student?) {
        student ?: throw ValidationException("Student cannot be null!\n")

        if (student.id.isNullOrBlank())
            throw ValidationException("Invalid ID!\n")

        if (student.name.isNullOrBlank())
            throw ValidationException("Invalid name!\n")

        if (!GROUP_REGEX.matches(student.group.toString()))
            throw ValidationException("Invalid group!\n")
    }
}