package com.example.crud_test.controllers;

import com.example.crud_test.entities.Student;
import com.example.crud_test.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    //creare uno studente
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    //ottenere la lista di tutti gli studenti
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    //prendere uno studente specifico passando primary key come path variable
    @GetMapping("/{id}")
    public Optional<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    //aggiornare uno studente
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        return studentService.updateStudent(id, studentDetails);
    }

    //aggiornare il valore isWorking value
    @PatchMapping("/{id}/working")
    public Student updateStudentWorkingStatus(@PathVariable Long id, @RequestParam boolean working) {
        return studentService.updateStudentWorkingStatus(id, working);
    }

    //cancellare uno studente
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
