package com.example.crud_test.services;

import com.example.crud_test.entities.Student;
import com.example.crud_test.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    //creare uno studente
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    //ottenere la lista di tutti gli studenti
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    //prendere uno studente specifico passando primary key come path variable
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    //aggiornare uno studente
    public Student updateStudent(Long id, Student studentDetails) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setName(studentDetails.getName());
                    student.setSurname(studentDetails.getSurname());
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    //aggiornare il valore isWorking value
    public Student updateStudentWorkingStatus(Long id, boolean isWorking) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setWorking(isWorking);
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    //cancellare uno studente
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
