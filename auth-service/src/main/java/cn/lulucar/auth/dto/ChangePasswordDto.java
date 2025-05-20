package cn.lulucar.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author wxl
 * @date 2025/5/12 15:28
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {
    @NotNull(message = "用户ID不能为空")
    private String userId;
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, message = "新密码至少需要6个字符")
    private String newPassword;
}
