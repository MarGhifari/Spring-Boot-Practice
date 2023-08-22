package com.example.demo.student;

// import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
public class StudentService {

	private StudentRepository studentRepository;

	@Autowired
    public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public List<Student> getStudents(){
		return studentRepository.findAll();
	}

	public void addNewStudent(Student student) {
		Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());

		if (studentByEmail.isPresent()){
			throw new RuntimeException("This email is already registered");
		}

		studentRepository.save(student);
	}

	public void deleteStudent(long studentId) {
		boolean exists = studentRepository.existsById(studentId);

		if (!exists){
			throw new RuntimeException ("Student with id " + studentId + " does not exists" );
		}

		studentRepository.deleteById(studentId);
	}

	@Transactional
    public void updateStudent(Long studentId, String name, String email) {
		Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException(
			String.format("No student found for ID %d", studentId)));

		if (name!=null && name.length() > 0 && !Objects.equals(student.getName(), name)){
			student.setName(name);
		}

		if (email!=null && email.length() > 0 && !Objects.equals(student.getEmail(), email)){
			Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
			if (studentOptional.isPresent()){
				throw new IllegalStateException("Email is already taken, use another email");
			} 
			student.setEmail(email);
		}
    }
}
