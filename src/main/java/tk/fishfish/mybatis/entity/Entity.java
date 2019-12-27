package tk.fishfish.mybatis.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * 实体
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@Data
public class Entity implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

}
