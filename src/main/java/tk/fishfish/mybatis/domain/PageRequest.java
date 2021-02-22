package tk.fishfish.mybatis.domain;

import com.github.pagehelper.IPage;
import lombok.Data;

/**
 * 分页
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@Data
public class PageRequest implements IPage {

    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String orderBy;

}
