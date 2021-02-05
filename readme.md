# mybatis-spring-boot-starter

> mybatis 集成 pagehelper、通用 mapper

## 依赖

```xml

<dependency>
    <groupId>tk.fishfish</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.1.0.RELEASE</version>
</dependency>
```

## 使用

### 配置

前缀：

- mybatis

  mybatis 官方配置

- mybatis.page-helper

  pagehelper 配置

- mybatis.mapper

  通用 mapper 配置

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

## 版本

### 1.1.0.RELEASE

修改：

- 升级依赖
- 实体主键类型从 Long 调整为 String

版本：

- Spring Boot 2.3.7.RELEASE
- MyBatis Spring Boot Starter 2.1.3
- PageHelper 5.2.0
- Mapper 4.1.5

### 1.0.1.RELEASE

项目初始化

- Spring Boot 2.1.2.RELEASE
- MyBatis Spring Boot Starter 2.0.1
- PageHelper 5.1.10
- Mapper 4.1.5
