package com.example.crud_test;

import com.example.crud_test.entities.Student;
import com.example.crud_test.repositories.StudentRepository;
import com.example.crud_test.services.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    public void testCreateStudent() {
        Student student = new Student();
        student.setName("Ilaria");
        student.setSurname("Faleschini");
        when(studentRepository.save(student)).thenReturn(student);

        Student createdStudent = studentService.createStudent(student);
        assertEquals("Ilaria", createdStudent.getName());
        assertEquals("Faleschini", createdStudent.getSurname());
    }

    @Test
    public void testGetAllStudents() {
        // Similar implementation for testing getAllStudents
    }

    @Test
    public void testGetStudentById() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Ilaria");
        student.setSurname("Faleschini");
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Optional<Student> foundStudent = studentService.getStudentById(1L);
        assertTrue(foundStudent.isPresent());
        assertEquals("Ilaria", foundStudent.get().getName());
    }

    @Test
    public void testUpdateStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Ilaria");
        student.setSurname("Faleschini");

        Student updatedDetails = new Student();
        updatedDetails.setName("Anna");
        updatedDetails.setSurname("Rossi");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedDetails);

        Student updatedStudent = studentService.updateStudent(1L, updatedDetails);
        assertEquals("Anna", updatedStudent.getName());
    }

    @Test
    public void testUpdateStudentWorkingStatus() {
        Student student = new Student();
        student.setId(1L);
        student.setWorking(false);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student updatedStudent = studentService.updateStudentWorkingStatus(1L, true);
        assertTrue(updatedStudent.isWorking());
    }

    @Test
    public void testDeleteStudent() {
        doNothing().when(studentRepository).deleteById(1L);
        studentService.deleteStudent(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }
}
