package song.teamo1.domain.team.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import song.teamo1.domain.common.exception.team.exceptions.DuplicateTeamNameException;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
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
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = "1")
    @DisplayName("가입한 팀 조회 성공")
    void successGetTeams() throws Exception {
        mockMvc.perform(get("/team"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "1")
    @DisplayName("팀 생성 성공")
    void successCreateTeam() throws Exception{
        mockMvc.perform(
                post("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("name", "test create team controller name", "info", "test team info"))))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1"})
    @WithUserDetails(value = "1")
    @DisplayName("팀 생성 실패: 동일한 팀 이름 예외")
    void failCreateTeam(String teamName) throws Exception {
        mockMvc.perform(
                post("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("name", teamName, "info", "test team info"))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertThat(result.getResolvedException())
                            .isInstanceOf(DuplicateTeamNameException.class);
                });
    }

    @Test
    @WithUserDetails(value = "1")
    @DisplayName("팀 상세 조회")
    void successGetTeam() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/team/{teamId}", 1L)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        log.info(response);
    }

}