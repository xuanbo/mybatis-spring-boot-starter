package tk.fishfish.mybatis.service.impl;

import com.github.pagehelper.IPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.fishfish.mybatis.entity.Entity;
import tk.fishfish.mybatis.repository.Repository;
import tk.fishfish.mybatis.service.BaseService;

/**
 * 通用服务实现
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public abstract class BaseServiceImpl<T extends Entity> implements BaseService<T> {

    @Autowired
    protected Repository<T> repository;

    @Override
    public PageInfo<T> page(IPage page) {
        PageHelper.startPage(page);
        return PageInfo.of(repository.selectAll());
    }

    @Override
    public PageInfo<T> page(T entity, IPage page) {
        PageHelper.startPage(page);
        return PageInfo.of(repository.select(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(T entity) {
        String id = entity.getId();
        if (id == null) {
            repository.insertSelective(entity);
        } else {
            repository.updateByPrimaryKeySelective(entity);
        }
    }

    @Override
    public T findById(String id) {
        return repository.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        repository.deleteByPrimaryKey(id);
    }

}