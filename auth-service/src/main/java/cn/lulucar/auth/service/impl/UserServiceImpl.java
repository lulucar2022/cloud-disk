package cn.lulucar.auth.service.impl;

import cn.lulucar.auth.entity.LoginResponse;
import cn.lulucar.auth.entity.User;
import cn.lulucar.auth.mapper.UserMapper;
import cn.lulucar.auth.service.IUserService;
import cn.lulucar.auth.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author wxl
 * @since 2025-05-09
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    public UserServiceImpl(PasswordEncoder passwordEncoder, JwtUtils jwtUtils, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.userMapper = userMapper;
    }

    @Override
    public LoginResponse login(String username, String password) {
        log.info("登录方法调用: username={}", username);
        // 查询用户
        QueryWrapper qw = new QueryWrapper<>();
        qw.eq("username", username);
        User user = userMapper.selectOne(qw);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            log.warn("登录失败: 用户不存在或密码错误, username={}", username);
            return null;
        }
        String token = jwtUtils.generateToken(user.getUsername());
        log.info("登录成功: username={}, token={}", username, token);
        return new LoginResponse(token, user);
    }

    @Override
    public boolean register(User user) {
        log.info("注册方法调用: username={}", user.getUsername());
        // 参数校验
        if (user.getUsername() == null || user.getPassword() == null) {
            log.warn("注册失败: 参数无效, username={}", user.getUsername());
            return false;
        }

        // 检查用户名是否存在
        if (query().eq("username", user.getUsername()).count() > 0) {
            log.warn("注册失败: 用户名已存在, username={}", user.getUsername());
            return false;
        }

        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        boolean success = save(user);
        if (success) {
            log.info("注册成功: username={}", user.getUsername());
        } else {
            log.warn("注册失败: 数据保存异常, username={}", user.getUsername());
        }
        return success;
    }

    @Override
    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        log.info("修改密码方法调用: userId={}", userId);
        User user = getById(userId);
        if (user == null || !passwordEncoder.matches(oldPassword, user.getPassword())) {
            log.warn("修改密码失败: 用户不存在或旧密码错误, userId={}", userId);
            return false;
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        boolean success = updateById(user);
        if (success) {
            log.info("修改密码成功: userId={}", userId);
        } else {
            log.warn("修改密码失败: 数据更新异常, userId={}", userId);
        }
        return success;
    }
}
