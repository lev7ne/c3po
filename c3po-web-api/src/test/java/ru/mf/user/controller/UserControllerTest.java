package ru.mf.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.mf.user.UserDto;
import ru.mf.user.service.UserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = { UserController.class, UserService.class })
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createUser_shouldReturnCreatedUser() throws Exception {
        // given
        UserDto createUserDto = new UserDto(1L, "Luke", "Skywalker", "luke.skywalker@starwars.com");
        given(userService.saveUser(any(UserDto.class))).willReturn(createUserDto);

        // when, then
        mockMvc.perform(post("/api/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createUserDto.getId()))
                .andExpect(jsonPath("$.firstName").value(createUserDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(createUserDto.getLastName()))
                .andExpect(jsonPath("$.email").value(createUserDto.getEmail()));

        verify(userService, times(1)).saveUser(any(UserDto.class));
    }

    @Test
    void findAllUsers_shouldReturnListOfUsers() throws Exception {
        // given
        List<UserDto> users = List.of(
                new UserDto(1L, "Luke", "Skywalker", "luke.skywalker@starwars.com"),
                new UserDto(2L, "Leia", "Organa", "leia.organa@starwars.com")
        );
        given(userService.findAllUsers()).willReturn(users);

        // when, then
        mockMvc.perform(get("/api/admin/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(users.size()))
                .andExpect(jsonPath("$[0].firstName").value("Luke"))
                .andExpect(jsonPath("$[1].firstName").value("Leia"));

        verify(userService, times(1)).findAllUsers();
    }
}