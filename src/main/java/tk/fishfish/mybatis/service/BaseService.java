package tk.fishfish.mybatis.service;

import com.github.pagehelper.IPage;
import com.github.pagehelper.PageInfo;
import tk.fishfish.mybatis.entity.Entity;

/**
 * 通用服务
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public interface BaseService<T extends Entity> {

    /**
     * 分页查询
     *
     * @param page 分页参数
     * @return 分页数据
     */
    PageInfo<T> page(IPage page);

    /**
     * 字段精确匹配，分页查询
     *
     * @param entity 实体
     * @param page   分页参数
     * @return 分页数据
     */
    PageInfo<T> page(T entity, IPage page);

    /**
     * 新增
     *
     * @param entity 实体
     */
    void insert(T entity);

    /**
     * 更新
     *
     * @param entity 实体
     */
    void update(T entity);

    /**
     * 新增或更新
     *
     * @param entity 实体
     */
    void save(T entity);

    /**
     * 查询
     *
     * @param id 主键
     * @return 实体
     */
    T findById(String id);

    /**
     * 删除
     *
     * @param id 主键
     */
    void deleteById(String id);

}
