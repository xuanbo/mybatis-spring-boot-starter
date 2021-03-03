package tk.fishfish.mybatis.condition;

import lombok.Data;
import tk.fishfish.mybatis.annotation.Eq;
import tk.fishfish.mybatis.annotation.In;
import tk.fishfish.mybatis.annotation.Like;

/**
 * 用户条件
 *
 * @author 奔波儿灞
 * @version 1.3.0
 */
@Data
public class UserCondition {

    @Like(property = "username", policy = Like.Policy.LEFT)
    private String username;

    @Eq(property = "sex")
    private Integer sex;

    @Like(property = "email")
    private String email;

    @In(property = "departmentId")
    private String[] departmentIds;

}
