package cn.lulucar.auth.controller;


import cn.lulucar.audit.annotation.AuditLog;
import cn.lulucar.auth.dto.ChangePasswordDto;
import cn.lulucar.auth.dto.LoginDto;
import cn.lulucar.auth.entity.LoginResponse;
import cn.lulucar.auth.entity.User;
import cn.lulucar.auth.service.IUserService;
import cn.lulucar.common.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import cn.dev33.satoken.stp.StpUtil;

/**
 *
 * @author wxl
 * @since 2025-05-09
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     * 用户登录接口
     */
    @AuditLog(value = "用户登录")
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginDto loginDto) {
        log.info("用户登录请求: username={}", loginDto.getUsername());
        LoginResponse response = userService.login(loginDto.getUsername(), loginDto.getPassword());
        StpUtil.login(response.getUser().getId());
        response.setToken(StpUtil.getTokenValue());
        return Result.success(response);
    }

    /**
     * 用户注册接口
     */

    @AuditLog(value = "用户注册")
    @PostMapping("/register")
    public Result<String> register(@RequestBody User user) {
        log.info("用户注册请求: username={}", user.getUsername());
        userService.register(user);
        return Result.success("注册成功");
    }

    /**
     * 修改密码接口
     */
    @AuditLog(value = "修改密码")
    @PostMapping("/change-password")
    public Result<String> changePassword(
            @Valid @RequestBody ChangePasswordDto dto) {
        log.info("修改密码请求: userId={}", dto.getUserId());
        userService.changePassword(dto.getUserId(), dto.getOldPassword(), dto.getNewPassword());
        return Result.success("密码修改成功");
    }
}
