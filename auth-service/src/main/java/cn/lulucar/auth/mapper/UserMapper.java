package cn.lulucar.auth.mapper;

import cn.lulucar.auth.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author wxl
 * @since 2025-05-09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
