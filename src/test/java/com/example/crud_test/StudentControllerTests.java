package com.example.crud_test;

import com.example.crud_test.controllers.StudentController;
import com.example.crud_test.entities.Student;
import com.example.crud_test.services.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void testCreateStudent() throws Exception {
        Student student = new Student();
        student.setName("Ilaria");
        student.setSurname("Faleschini");

        when(studentService.createStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ilaria\", \"surname\":\"Faleschini\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ilaria"))
                .andExpect(jsonPath("$.surname").value("Faleschini"));
    }


    @Test
    public void testGetAllStudents() throws Exception {
        Student student1 = new Student();
        student1.setName("Ilaria");
        student1.setSurname("Faleschini");

        Student student2 = new Student();
        student2.setName("Anna");
        student2.setSurname("Rossi");

        when(studentService.getAllStudents()).thenReturn(Arrays.asList(student1, student2));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Ilaria"))
                .andExpect(jsonPath("$[0].surname").value("Faleschini"))
                .andExpect(jsonPath("$[1].name").value("Anna"))
                .andExpect(jsonPath("$[1].surname").value("Rossi"));
    }

    @Test
    public void testGetStudentById() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Ilaria");
        student.setSurname("Faleschini");

        when(studentService.getStudentById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ilaria"))
                .andExpect(jsonPath("$.surname").value("Faleschini"));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Ilaria");
        student.setSurname("Faleschini");

        Student updatedStudent = new Student();
        updatedStudent.setName("Anna");
        updatedStudent.setSurname("Rossi");

        when(studentService.updateStudent(eq(1L), any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Anna\", \"surname\":\"Rossi\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Anna"))
                .andExpect(jsonPath("$.surname").value("Rossi"));
    }

    @Test
    public void testUpdateStudentWorkingStatus() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setWorking(false);

        Student updatedStudent = new Student();
        updatedStudent.setId(1L);
        updatedStudent.setWorking(true);

        when(studentService.updateStudentWorkingStatus(1L, true)).thenReturn(updatedStudent);

        mockMvc.perform(patch("/students/1/working")
                        .param("working", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.working").value(true));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk());
    }
}
