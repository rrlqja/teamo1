package song.teamo1.domain.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "JASYPT_ENCRYPTOR_PASSWORD=song"
})
@AutoConfigureMockMvc
class CommonControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void loginSuccessTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/login")
                        .param("username", "1")
                        .param("password", "1"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        String accessToken = objectMapper.readTree(response).get("accessToken").asText();
        Assertions.assertThat(accessToken)
                .isNotNull();

        mockMvc.perform(get("/").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }
}