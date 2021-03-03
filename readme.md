# mybatis-spring-boot-starter

> mybatis 集成 pagehelper、通用 mapper

## 依赖

```xml

<dependency>
    <groupId>tk.fishfish</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.2.0.RELEASE</version>
</dependency>
```

## 快速使用

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

### entity

定义实体，字段默认驼峰转下划线策略。

```java

@Data
@Table(name = "oh_user")
@EqualsAndHashCode(callSuper = true)
public class User extends Entity {

    private String name;

    private String username;

    private String password;

    private Integer sex;

    private String email;

    private String departmentId;

    private Date createdAt;

    private Date updatedAt;

    @Transient
    private String ignore;

}
```

### condition

定义查询条件。

```java

@Data
public class UserCondition {

    @Like(property = "username", policy = Like.Policy.LEFT)
    private String username;

    @Eq(property = "sex")
    private Integer sex;

    @Like(property = "email")
    private String email;

    @In(property = "departmentId")
    private String[] departmentIds;

}
```

其中注解的 property 为实体的字段名称，查询时会自动转换为数据库对应字段

### repository

底层为 tk mapper 通用数据库 CRUD 方法

```java

@Mapper
public interface UserRepository extends Repository<User> {
}
```

### service

业务层继承通用的服务

```java
public interface UserService extends BaseService<User> {
}
```

```java

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
}
```

### controller

继承部分通用 API 接口

```java

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController extends BaseController<User> {

    private final UserService userService;

    @PostMapping("/page")
    public Page<User> page(@RequestBody Query<UserCondition> query) {
        return userService.page(query.getCondition(), query.getPage());
    }

}
```

## 版本

### 1.3.0-SNAPSHOT

修改：

- 新增 Condition 查询

版本：

- Spring Boot 2.3.7.RELEASE
- MyBatis Spring Boot Starter 2.1.3
- PageHelper 5.2.0
- Mapper 4.1.5

### 1.2.0.RELEASE

修改：

- 新增 Base Controller

版本：

- Spring Boot 2.3.7.RELEASE
- MyBatis Spring Boot Starter 2.1.3
- PageHelper 5.2.0
- Mapper 4.1.5

### 1.1.0.RELEASE

修改：

- 升级依赖
- 实体主键类型从 Long 调整为 String

版本：

- Spring Boot 2.3.7.RELEASE
- MyBatis Spring Boot Starter 2.1.3
- PageHelper 5.2.0
- Mapper 4.1.5

### 1.0.0.RELEASE

项目初始化

- 集成 PageHelper
- 集成 Mapper
- 新增 Base Service

版本：

- Spring Boot 2.1.2.RELEASE
- MyBatis Spring Boot Starter 2.0.1
- PageHelper 5.1.10
- Mapper 4.1.5
