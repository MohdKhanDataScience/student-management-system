
package com.khan.studentmanagement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class StudentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addStudentIntegrationTest() throws Exception {

        String studentJson = """
                {
                    "name": "Rahul",
                    "email": "rahul.integration@test.com",
                    "age": 25,
                    "phoneNumber": "9876543210"
                }
                """;

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(studentJson))
                .andExpect(status().isOk());
    }

    @Test
    void getAllStudentsIntegrationTest() throws Exception {

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk());
    }

    @Test
    void getStudentByIdIntegrationTest() throws Exception {

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteStudentIntegrationTest() throws Exception {

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk());
    }
}
