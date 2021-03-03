package tk.fishfish.mybatis.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询
 *
 * @author 奔波儿灞
 * @version 1.3.0
 */
@Data
public class Query<C> implements Serializable {

    private C condition;

    private PageRequest page;

}
