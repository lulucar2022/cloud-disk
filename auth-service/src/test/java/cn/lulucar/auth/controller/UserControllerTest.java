package cn.lulucar.auth.controller;

import cn.lulucar.auth.dto.ChangePasswordDto;
import cn.lulucar.auth.dto.LoginDto;
import cn.lulucar.auth.entity.LoginResponse;
import cn.lulucar.auth.entity.User;
import cn.lulucar.auth.service.IUserService;
import cn.lulucar.common.domain.Result;
import cn.lulucar.common.exception.AuthException;
import cn.lulucar.common.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author wxl
 * @date 2025/5/21 16:27
 * @description
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testLoginSuccess() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("123123");
        loginDto.setPassword("123123");

        User user = new User();
        user.setUsername("123123");

        LoginResponse response = new LoginResponse();
        response.setUser(user);
        response.setToken("testToken");

        when(userService.login(any(String.class), any(String.class))).thenReturn(response);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testRegisterSuccess() throws Exception {
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("newpassword");

        Result<String> result = Result.success("注册成功");

        doThrow(new AuthException("用户不存在或密码错误")).when(userService).login(any(String.class), any(String.class));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    public void testChangePasswordSuccess() throws Exception {
        ChangePasswordDto dto = new ChangePasswordDto();
        dto.setUserId("1");
        dto.setOldPassword("wrongoldpassword");
        dto.setNewPassword("newpassword");

        doThrow(new BusinessException("用户不存在或旧密码错误")).when(userService).changePassword(any(String.class), any(String.class), any(String.class));

        mockMvc.perform(post("/auth/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}