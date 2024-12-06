package song.teamo1.domain.team.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "JASYPT_ENCRYPTOR_PASSWORD=song"
})
@AutoConfigureMockMvc
class TeamControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithUserDetails(value = "1")
    void successSaveTeam() throws Exception{
        mockMvc.perform(post("/team/save")
                        .param("name", "test team name")
                        .param("info", "test team info"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/team/{teamId}"));

    }

}