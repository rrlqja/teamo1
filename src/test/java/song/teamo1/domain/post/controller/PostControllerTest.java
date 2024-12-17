package song.teamo1.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import song.teamo1.domain.post.repository.PostJpaRepository;

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
class PostControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    PostJpaRepository postRepository;

    @Test
    @WithUserDetails("1")
    @DisplayName("티밍 생성 성공")
    void successCreateTeaming() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/post/teaming")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(Map.of("title", "test title", "content", "test content", "teamId", "1"))))
                .andExpect(status().isOk())
                .andReturn();

        Long id = Long.valueOf(objectMapper.readTree(mvcResult.getResponse().getContentAsString())
                .get("id").toString());

        assertThat(postRepository.findById(id))
                .isNotEmpty()
                .get().hasFieldOrPropertyWithValue("title", "test title")
                .hasFieldOrPropertyWithValue("content", "test content");
    }

    @Test
    @WithUserDetails("1")
    @DisplayName("티밍 생성 실패: 소속되어있지 않은 팀")
    void failCreateTeamingByTeamMemberNotFoundException() throws Exception {
        mockMvc.perform(post("/post/teaming")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(Map.of("title", "test title", "content", "test content", "teamId", "-1"))))
                .andExpect(status().isBadRequest());
    }

}