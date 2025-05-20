package cn.lulucar.auth.controller;


import cn.lulucar.audit.annotation.AuditLog;
import cn.lulucar.auth.dto.ChangePasswordDto;
import cn.lulucar.auth.dto.LoginDto;
import cn.lulucar.auth.entity.LoginResponse;
import cn.lulucar.auth.entity.User;
import cn.lulucar.auth.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        log.info("用户登录请求: username={}", loginDto.getUsername());
        LoginResponse response = userService.login(loginDto.getUsername(), loginDto.getPassword());
        if (response != null) {
            log.info("用户登录成功: username={}", loginDto.getUsername());
            // 使用 Sa-Token 登录
            StpUtil.login(response.getUser().getId());
            response.setToken(StpUtil.getTokenValue());
            return ResponseEntity.ok(response);
        } else {
            log.warn("用户登录失败: username={}", loginDto.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登录失败");
        }
    }

    /**
     * 用户注册接口
     */

    @AuditLog(value = "用户注册")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        log.info("用户注册请求: username={}", user.getUsername());
        boolean success = userService.register(user);
        if (success) {
            log.info("用户注册成功: username={}", user.getUsername());
            return ResponseEntity.ok().body("注册成功");
        } else {
            log.warn("用户注册失败: username={}", user.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("用户名已存在或参数无效");
        }
    }

    /**
     * 修改密码接口
     */
    @AuditLog(value = "修改密码")
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @Valid @RequestBody ChangePasswordDto dto) {
        log.info("修改密码请求: userId={}", dto.getUserId());
        boolean success = userService.changePassword(dto.getUserId(), dto.getOldPassword(), dto.getNewPassword());
        if (success) {
            log.info("修改密码成功: userId={}", dto.getUserId());
            return ResponseEntity.ok("密码修改成功");
        } else {
            log.warn("修改密码失败: userId={}", dto.getUserId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("修改失败：可能用户不存在或旧密码错误");
        }
    }
}
