package com.htcs.kehaoduo.common.annotations;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface HasRole {
    /**
     * 角色value值
     * @return
     */
    String[] value();

    /**
     * 多个value值使用and还是or进行组合
     * @return
     */
    Type type() default Type.AND;

    enum Type{
        AND,
        OR
    }
}
