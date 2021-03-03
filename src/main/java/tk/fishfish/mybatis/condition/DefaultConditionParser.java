package tk.fishfish.mybatis.condition;

import org.springframework.util.CollectionUtils;
import tk.fishfish.mybatis.annotation.Eq;
import tk.fishfish.mybatis.annotation.Gt;
import tk.fishfish.mybatis.annotation.Gte;
import tk.fishfish.mybatis.annotation.In;
import tk.fishfish.mybatis.annotation.IsNotNull;
import tk.fishfish.mybatis.annotation.IsNull;
import tk.fishfish.mybatis.annotation.Like;
import tk.fishfish.mybatis.annotation.Lt;
import tk.fishfish.mybatis.annotation.Lte;
import tk.fishfish.mybatis.annotation.NotEq;
import tk.fishfish.mybatis.annotation.NotIn;
import tk.fishfish.mybatis.annotation.NotLike;
import tk.fishfish.mybatis.entity.Entity;
import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * 默认条件解析
 *
 * @author 奔波儿灞
 * @version 1.3.0
 */
public class DefaultConditionParser implements ConditionParser {

    private final ConcurrentMap<Class<?>, List<FieldCondition>> conditionCache = new ConcurrentHashMap<>(16);

    @Override
    public Condition parse(Class<? extends Entity> entityClazz, Object condition) {
        Condition c = new Condition(entityClazz);
        if (condition == null) {
            return c;
        }
        if (condition instanceof Map) {
            Example.Criteria criteria = c.and();
            ((Map<?, ?>) condition).forEach((k, v) -> criteria.andEqualTo(k.toString(), v));
        } else {
            Class<?> clazz = condition.getClass();
            List<FieldCondition> fieldConditions = conditionCache.computeIfAbsent(clazz, (key) -> parseFieldCondition(clazz));
            if (CollectionUtils.isEmpty(fieldConditions)) {
                return c;
            }
            for (FieldCondition fieldCondition : fieldConditions) {
                buildCriteria(condition, fieldCondition, c.and());
            }
        }
        return c;
    }

    private List<FieldCondition> parseFieldCondition(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<FieldCondition> fieldConditions = new ArrayList<>();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Eq) {
                    fieldConditions.add(new FieldCondition(field, Op.EQ, annotation));
                } else if (annotation instanceof NotEq) {
                    fieldConditions.add(new FieldCondition(field, Op.NOT_EQ, annotation));
                } else if (annotation instanceof Gt) {
                    fieldConditions.add(new FieldCondition(field, Op.GT, annotation));
                } else if (annotation instanceof Gte) {
                    fieldConditions.add(new FieldCondition(field, Op.GTE, annotation));
                } else if (annotation instanceof Lt) {
                    fieldConditions.add(new FieldCondition(field, Op.LT, annotation));
                } else if (annotation instanceof Lte) {
                    fieldConditions.add(new FieldCondition(field, Op.LTE, annotation));
                } else if (annotation instanceof In) {
                    fieldConditions.add(new FieldCondition(field, Op.IN, annotation));
                } else if (annotation instanceof NotIn) {
                    fieldConditions.add(new FieldCondition(field, Op.NOT_IN, annotation));
                } else if (annotation instanceof Like) {
                    fieldConditions.add(new FieldCondition(field, Op.LIKE, annotation));
                } else if (annotation instanceof NotLike) {
                    fieldConditions.add(new FieldCondition(field, Op.NOT_LIKE, annotation));
                } else if (annotation instanceof IsNull) {
                    fieldConditions.add(new FieldCondition(field, Op.IS_NULL, annotation));
                } else if (annotation instanceof IsNotNull) {
                    fieldConditions.add(new FieldCondition(field, Op.IS_NOT_NULL, annotation));
                }
            }
        }
        return fieldConditions;
    }

    private void buildCriteria(Object condition, FieldCondition fieldCondition, Example.Criteria criteria) {
        String column;
        Object value = getValue(condition, fieldCondition.getField());
        switch (fieldCondition.getOp()) {
            case EQ:
                if (value != null) {
                    column = ((Eq) fieldCondition.getAnnotation()).property();
                    criteria.andEqualTo(column, value);
                }
                break;
            case NOT_EQ:
                if (value != null) {
                    column = ((NotEq) fieldCondition.getAnnotation()).property();
                    criteria.andNotEqualTo(column, value);
                }
                break;
            case GT:
                if (value != null) {
                    column = ((Gt) fieldCondition.getAnnotation()).property();
                    criteria.andGreaterThan(column, value);
                }
                break;
            case GTE:
                if (value != null) {
                    column = ((Gte) fieldCondition.getAnnotation()).property();
                    criteria.andGreaterThanOrEqualTo(column, value);
                }
                break;
            case LT:
                if (value != null) {
                    column = ((Lt) fieldCondition.getAnnotation()).property();
                    criteria.andGreaterThan(column, value);
                }
                break;
            case LTE:
                if (value != null) {
                    column = ((Lte) fieldCondition.getAnnotation()).property();
                    criteria.andGreaterThanOrEqualTo(column, value);
                }
                break;
            case IN:
                if (value != null) {
                    column = ((In) fieldCondition.getAnnotation()).property();
                    if (value.getClass().isArray()) {
                        criteria.andIn(column, Arrays.stream((Object[]) value).collect(Collectors.toList()));
                    } else if (value instanceof Iterable) {
                        criteria.andIn(column, (Iterable<?>) value);
                    } else {
                        throw new MapperException("操作[IN]必须是数组或集合类型");
                    }
                }
                break;
            case NOT_IN:
                column = ((NotIn) fieldCondition.getAnnotation()).property();
                if (value.getClass().isArray()) {
                    criteria.andIn(column, Arrays.stream((Object[]) value).collect(Collectors.toList()));
                } else if (value instanceof Iterable) {
                    criteria.andIn(column, (Iterable<?>) value);
                } else {
                    throw new MapperException("操作[NOT IN]必须是数组或集合类型");
                }
                break;
            case LIKE:
                if (value != null) {
                    Like like = (Like) fieldCondition.getAnnotation();
                    switch (like.policy()) {
                        case LEFT:
                            criteria.andLike(like.property(), "%" + value.toString());
                            break;
                        case RIGHT:
                            criteria.andLike(like.property(), value.toString() + "%");
                            break;
                        case ALL:
                            criteria.andLike(like.property(), "%" + value.toString() + "%");
                            break;
                    }
                }
                break;
            case NOT_LIKE:
                if (value != null) {
                    NotLike like = (NotLike) fieldCondition.getAnnotation();
                    switch (like.policy()) {
                        case LEFT:
                            criteria.andNotLike(like.property(), "%" + value.toString());
                            break;
                        case RIGHT:
                            criteria.andNotLike(like.property(), value.toString() + "%");
                            break;
                        case ALL:
                            criteria.andNotLike(like.property(), "%" + value.toString() + "%");
                            break;
                    }
                }
                break;
            case IS_NULL:
                column = ((IsNull) fieldCondition.getAnnotation()).property();
                criteria.andIsNull(column);
                break;
            case IS_NOT_NULL:
                column = ((IsNotNull) fieldCondition.getAnnotation()).property();
                criteria.andIsNull(column);
                break;
        }
    }

    private <C> Object getValue(C condition, Field field) {
        try {
            return field.get(condition);
        } catch (IllegalAccessException e) {
            throw new MapperException("反射字段值错误", e);
        }
    }

}