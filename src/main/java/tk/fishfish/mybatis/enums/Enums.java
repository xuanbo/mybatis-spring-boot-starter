package tk.fishfish.mybatis.enums;

import org.springframework.util.StringUtils;

/**
 * 枚举工具类
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
public final class Enums {

    private Enums() {
        throw new IllegalStateException("Utils");
    }

    public static <E extends Enum<E>> E valueOf(Class<E> type, String value) {
        E[] constants = type.getEnumConstants();
        for (E e : constants) {
            if (!(e instanceof EnumType)) {
                continue;
            }
            if (((EnumType) e).getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

}
