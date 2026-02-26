---
ruleType: Model Request
description: 工程结构和应用分层规范，包含模块设计、包结构等规约
globs:
---
rule编写规则: https://km.sankuai.com/collabpage/2710344014

# 工程结构规范

## 应用分层

### 基本分层原则
- 【建议】一个项目仓库最多只包含一个发布模块
- 【建议】业务系统应优先使用"模块(Module)"实现代码结构分层，对于定时任务等简单服务可使用包(Package)分层
- 【建议】利用"依赖倒置"把外域信息转换为本域上下文实体再使用，实现本领域和外部依赖解耦

### 推荐工程结构
推荐的工程结构如下，各团队可参考业务复杂性按需选择：
- **三层架构**：starter + application + infrastructure
- **四层架构**：starter + application + domain + infrastructure

### 各层职责定义

#### 接口层 (demo-client，可选)
- 含义：服务端接口模块
- 职责：负责定义接口和模型，包括各类协议的接口及模型
- 依赖关系：无

#### 接入层 (demo-starter)
- 含义：服务启动接入实现模块
- 职责：负责项目启动配置，包含服务配置、不同环境的配置文件等。负责接口协议适配、请求转发、响应结果封装、异常处理、日志打点、定时任务接入、消息监听等工作
- 依赖关系：demo-client(如有) + demo-application

#### 应用层 (demo-application)
- 含义：应用层模块
- 职责：负责用例实现和基础设施层接口定义，通常串联内部领域服务接口或基础设施服务接口，实现流程编排、聚合查询等工作流操作
- 依赖关系：demo-domain

#### 领域层 (demo-domain，可选)
- 含义：领域层模块
- 职责：负责业务逻辑实现，通常抽象出领域对象，来表达业务概念，承载业务行为和规则。本层非必选，一般DDD使用
- 依赖关系：无

#### 基础设施层 (demo-infrastructure)
- 含义：基础设施层模块
- 职责：负责外部调用，比如数据库的CRUD、搜索引擎、文件系统、分布式服务的RPC等。领域防腐在这里，外部依赖需要通过gateway转义处理后才能app层和Domain层使用
- 依赖关系：demo-domain(如有) + demo-application

## 领域模型规约

### 对象类型定义
- **DTO**(Data transfer object)：数据传输对象，命名细分为：Request(入参)、Response(返回)、DTO(入参/返回封装的对象)，用于RPC或Http定义请求/响应对象
- **BO**(Business Object)：业务对象，由Service层输出的封装业务逻辑的对象
- **DO**(Domain Object)：领域对象，从现实世界中抽象出来的有形或无形的业务实体
- **PO**(Persistent Object)：持久化对象，用于DB、Cache存储
- 【强制】禁止将DO/DTO/BO/VO等类直接命名成xxxPOJO

### 对象传递规约
工程结构中的对象类，通常可分为三类：接口对象、领域对象和数据对象。在各个模块中的定义和使用范围需要严格控制，避免跨层级随意使用。

## 包结构规范

### 各层包结构定义

#### starter模块包结构
- **aop**：切面类，命名xxAspect
- **config**：配置类，命名xxConfig
- **consumer**：消费者，命名xxConsumer(Mafka消息的消费者)
- **controller**：控制器，命名xxController(HTTP接口)
- **service**：服务接口实现，命名xxServiceImpl(MThrift、Pigeon服务接口实现)
- **job**：定时任务，命名xxJob(Crane定时任务)

#### application模块包结构
- **model**：模型，命名xxBO(应用层接口模型中的请求对象和返回对象)
- **param**：请求参数，命名xxQueryParam/xxParam(用于定义方法的参数对象)
- **service**：应用服务，命名xxApplicationService/xxProcessService/xxAggregateService
- **gateway**：资源接口，命名xxGateway(资源库，所有依赖的资源由此接口对本领域暴露)

#### domain模块包结构
- **ability**：领域对象行为，命名xxAbility(领域对象的行为，以类的形式存在)
- **constant**：常量，命名xxConstant
- **enums**：枚举，命名xxEnum
- **event**：领域事件，命名xxDomainEvent(领域实体发生状态变化后，向外发出的事件)
- **model**：模型，命名xxDO(领域层接口模型中的请求对象和返回对象，以及领域对象)
- **service**：领域服务，命名xxDomainService(负责业务逻辑的实现)
- **gateway**：资源接口，命名xxGateway(资源库接口定义)

#### infrastructure模块包结构
- **gateway.impl**：资源接口实现，命名xxGatewayImpl
- **db.mapper**：数据访问层，命名xxMapper(负责对数据库的访问操作)
- **db.po**：数据实体，命名xxPO(持久化对象)
- **adapter**：外部服务代理，命名xxAdapter(负责对外部服务的调用封装)
- **cache**：缓存，命名xxCache(负责对缓存客户端的封装)
- **config**：业务配置，命名xxConfig(负责业务相关的控制、参数等配置信息)
- **producer**：消息发布，命名xxProducer(负责消息的发送)

### 转换器规范
- **package**：convert
- **命名**：xxConverter，xx指最终要转换成的对象
- **存放位置**：converter包就近存放
- **方法**：转换器方法应该是静态方法，业务逻辑不应放在转换方法中，减少Convert方法中访问RPC资源

## 二方库依赖规约

### GAV定义规则
- 【强制】**GroupId**格式：com.{公司}.业务线.[子业务线]，最多4级，子业务线可选
- 【强制】**ArtifactId**格式：产品线名-模块名，语义不重复不遗漏
- 【强制】**Version**：二方库版本号命名方式：主版本号.次版本号.修订号，起始版本号必须为：1.0.0

### 依赖管理规范
- 【强制】线上应用不要依赖SNAPSHOT版本(安全包除外)
- 【强制】二方库的新增或升级，保持除功能点之外的其它jar包仲裁结果不变
- 【强制】禁止在子项目的pom依赖中出现相同的GroupId，相同的ArtifactId，但是不同的Version
- 【强制】二方库里可以定义枚举类型，但是参数、返回值不允许使用枚举类型
- 【建议】所有pom文件中的依赖声明放在<dependencyManagement>语句块中，所有版本仲裁放在<dependencies>语句块中

### 二方库设计原则
- 【建议】为避免应用二方库的依赖冲突问题，二方库发布者应当遵循：
  - 移除一切不必要的API和依赖，只包含Service API、必要的领域模型对象、Utils类、常量、枚举等
  - 每个版本的变化应该被记录，二方库由谁维护，源码在哪里，都需要能方便查到

## 调用关系规约

### 三层架构调用关系
starter → application → infrastructure

### 四层架构调用关系
starter → application → domain
application → infrastructure
infrastructure → domain(可选)

### 调用约束
- 【强制】严格按照分层调用，禁止跨层调用
- 【建议】上层模块可以调用下层模块，下层模块不应该依赖上层模块
- 【建议】同一层级的模块之间应该减少相互依赖
