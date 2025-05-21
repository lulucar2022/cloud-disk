package cn.lulucar.auth.service;

import cn.lulucar.auth.entity.LoginResponse;
import cn.lulucar.auth.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author wxl
 * @since 2025-05-09
 */
public interface IUserService extends IService<User> {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录响应对象（含 token）
     */
    LoginResponse login(String username, String password);

    /**
     * 用户注册
     *
     * @param user 用户信息
     */
    void register(User user);

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void changePassword(String userId, String oldPassword, String newPassword);
}

