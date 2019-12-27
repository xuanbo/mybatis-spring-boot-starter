# mybatis-spring-boot-starter

> mybatis 集成 pagehelper、通用 mapper

## 依赖

还未发布到 maven 公共仓库

```xml
<dependency>
    <groupId>tk.fishfish</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## 使用

排除`org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration`

### 配置

前缀：

- mybatis

    mybatis官方配置

- mybatis.page-helper

    pagehelper配置

- mybatis.mapper

    mapper配置

示例：

```yml
mybatis:
  # mapper的xml位置
  mapper-locations: classpath:mapper/*Mapper.xml
  # 包别名
  type-aliases-package: xxx.yyy.zzz.entity
  configuration:
    # 下划线转驼峰
    map-underscore-to-camel-case: true
  # 分⻚
  page-helper:
    helper-dialect: mysql
    reasonable: "true"
    params: count=countSql
  # 通用mapper
  mapper:
    mappers:
      - tk.fishfish.mybatis.repository.Repository
    not-empty: true
```
