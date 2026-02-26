---
ruleType: Model Request
description: 中间件使用规范，包含RPC、消息队列、缓存等中间件使用规约
globs:
---
rule编写规则: https://km.sankuai.com/collabpage/2710344014

# 中间件使用规约

## RPC框架规约

### 契约和接口
- 【强制】Pigeon和MTthrift的契约修改必须保证向下兼容
- 【强制】契约定义，避免删除已有字段、或修改字段顺序
- 【强制】契约定义，新增的required字段必须周知上游
- 【强制】注解方式定义Thrift契约，必须使用@ThriftField注明字段编号

### 服务部署和调用
- 【强制】server端单端口部署，client端使用nettyIO且不指定端口
- 【强制】禁止服务端同时提供Pigeon和MTthrift两种协议的服务
- 【强制】通过服务名发现服务，禁止依赖端口
- 【强制】复用ThriftClientProxy/ReferenceBean等RPC Client对象，非活跃对象必须调用destroy()销毁

### RPC使用规范
- 【强制】使用RPC框架原生异步接口，禁止业务层包装同步调用为异步
- 【强制】禁止在list、map和set中的key或value写入null
- 【建议】serviceInterface遵守命名规范：GroupId.ArtifactId.ServiceName定义名称

## 消息队列(Mafka)规约

### 消息生产规范
- 【强制】消息生产必须处理失败情况，验证ProducerResult状态
- 【强制】不要每次发送消息都创建一个新的实例，使用单例或spring bean管理发送者实例
- 【建议】消息发送成功后，建议业务侧打印msgID，方便问题排查

### 消息消费规范
- 【强制】Mafka消费者一定要将所有的异常都catch住，并慎用CONSUMER_FAILURE
- 【建议】Mafka消息可能重复，业务侧如果不能忍受重复消费需要在消费逻辑中进行幂等处理
- 【建议】控制消息发送的大小，Tracer和消息Body加起来不要超过1M

### 消息队列使用建议
- 【建议】接入生产或消费时，不要自行在主题名字后拼接_dev/_test等后缀
- 【建议】如果希望消息按分区聚合消费，建议使用带有partKey参数的接口
- 【建议】对可用性和发送延迟要求较高的场合，不要使用指定分区Key发送功能
- 【建议】不能接受消息丢失的场合，不要使用延迟消息发送接口
- 【建议】发送延迟消息时，消息的时间不要集中在接近的区段内到期
- 【建议】增量业务不要使用事务消息、优先级队列等MQ将要下线的功能

## 缓存规约

### Cellar规约
- 【强制】key不要包含空白字符或者转义字符
- 【建议】设置数据过期时间，对于当做持久存储使用的场景定期保鲜
- 【建议】使用String.getBytes()方法序列化key，方便在管理平台查询数据和排查服务端问题
- 【建议】批量请求key个数建议不要超过100个
- 【建议】复杂数据结构(list，set，map，prefix接口)的元素不要存储超过1000个
- 【建议】不要在同一个pkey下存放过多skey，个数建议小于1000

### Squirrel规约
- 【强制】Key的命名需要言简意赅、长度不超过44个字节
- 【强制】杜绝大key，String类型的value超过1MB(建议小于512KB)需要治理
- 【强制】杜绝热点key(QPS阈值：5000)，建议通过本地缓存、分桶等方案解决
- 【建议】设置合理的过期时间，key的过期时间应该尽量的短
- 【建议】合理的批量查询数量，multi以及pipeline等批量操作中的key个数不要太多
- 【建议】避免使用复杂度为O(N)的集合类操作，比如Hgetall等

## 检索(ES)规约

- 【建议】深度分页(超过10,000行)使用Search After
- 【建议】使用分词查询match替代模糊查询，模糊查询性能较差

## 配置中心(Lion)规约

### 基本使用规范
- 【强制】单个key值长度限制在500K以内
- 【强制】禁止在已下线或未注册的appkey新增Lion配置
- 【强制】通过@LionConfig，@MtConfig注解获取变量对象时，严禁使用时修改
- 【强制】使用@MtConfig注解时，变量必须使用static、volatile进行修饰

### 配置类型和使用建议
- 【强制】@MtConfig注解只支持6种基本类型，不支持List、Set等数据类型
- 【建议】不使用外部参数直接/间接作为key读取Lion配置，存在内存隐患和业务隐患
- 【建议】复杂Lion配置建议使用Json、List、Map等具备格式校验的Lion类型
- 【建议】合理使用Lion配置分组功能，拆分隔离配置
- 【建议】推荐使用ConfigEvent中的oldValue和value，防止监听器回调和使用时数据不一致

## 定时任务(Crane)规约

### 任务配置规范
- 【强制】任务名需要满足规范：不少于5个字符，不超过100个字符，以字母或数字开头并结尾
- 【强制】中止执行中任务场景，需要业务通过增加兜底开关或者识别Crane中断标记的方式支持
- 【建议】任务名建议使用appKey前缀方式，避免受到Crane系统任务名唯一影响

### 任务执行规范
- 【建议】任务要配置重试，业务执行逻辑要支持幂等
- 【建议】任务代码逻辑无需Catch异常，否则异常情况无法被Crane捕获、无法触发重试

## 示例

### RPC调用示例
```java
// 正确的RPC调用方式
@Autowired
private UserService userService;

public void processUser(Long userId) {
    try {
        UserDTO user = userService.getUser(userId);
        // 处理业务逻辑
    } catch (Exception e) {
        log.error("RPC调用失败", e);
        // 错误处理
    }
}
```

### 缓存使用示例
```java
// Squirrel缓存使用示例
public class UserCache {
    private static final String USER_KEY_TEMPLATE = "user.info_%s";

    public UserInfo getUserFromCache(Long userId) {
        String key = String.format(USER_KEY_TEMPLATE, userId);
        return squirrelClient.get(key, UserInfo.class);
    }
}
