---
ruleType: Model Request
description: Java编程基础规约，包含命名、代码风格、OOP等核心编程规范
globs:
---
rule编写规则: https://km.sankuai.com/collabpage/2710344014

# Java编程基础规约

## 命名风格规范

### 基本要求
- 【强制】代码和注释禁止使用歧视性或侮辱性词语
- 【强制】标识符只能由ASCII字符中的字母、数字、下划线(_)或美元符号($)组成
- 【强制】禁止拼音与英文混用，不建议使用拼音
- 【强制】禁止不规范缩写，应使用完整的单词组合来表达其意

### 命名规范
- 【强制】包名使用小写字母和数字，单数形式
- 【强制】类名使用UpperCamelCase风格，如MarcoPolo、UserDO、XmlService
- 【强制】方法名、参数名、成员变量、局部变量使用lowerCamelCase风格
- 【强制】常量名称使用UPPER_SNAKE_CASE风格
- 【建议】枚举类名带上Enum后缀，枚举成员名称使用UPPER_SNAKE_CASE风格

## 代码风格规范

### 缩进和空格
- 【强制】禁止使用Tab制表符缩进，采用4个空格缩进
- 【强制】关键字与括号之间、二目三目运算符左右两边都要加一个空格
- 【强制】逗号、冒号、分号与同一行右侧相邻字符之间添加一个空格

### 大括号和换行
- 【强制】if/else/for/while/do语句中必须使用大括号
- 【强制】左大括号前不换行，左大括号后换行，右大括号前换行
- 【强制】单行字符数限制不超过120个，超出需换行

### 其他风格要求
- 【强制】源文件采用UTF-8编码格式，换行符使用Unix格式(LF)
- 【建议】单个方法的总行数不超过80行
- 【强制】禁止使用通配符import，明确指定所需的类

## OOP规约

### 访问控制
- 【建议】类成员与方法访问控制从严，按需设置private/protected/public
- 【强制】所有覆写方法必须加@Override注解
- 【强制】禁止通过对象引用访问静态变量或静态方法

### 数据类型使用
- 【强制】正确使用数据类型，尽可能用基本类型而不是包装类型
- 【强制】浮点数精确计算场景避免使用float和double
- 【强制】整型包装类对象之间值的比较全部使用equals方法
- 【强制】浮点数等值比较不能用==，应使用BigDecimal.compareTo()

### 对象操作规范
- 【强制】避免用equals方法时抛出NPE，推荐使用非空对象调用equals
- 【强制】关于toString()方法，应返回对象中所有重要字段信息
- 【强制】POJO类中禁止同时存在isXxx()和getXxx()方法
- 【强制】避免用Apache BeanUtils进行属性copy

## 集合处理规约

### 基本操作
- 【强制】equals和hashCode的处理，重写equals时必须重写hashCode
- 【建议】判断集合是否为空使用isEmpty()方法而非size()==0
- 【强制】使用Collectors.toMap()时要避免key冲突和value为null的异常

### 集合使用注意事项
- 【强制】Arrays.asList()返回固定大小列表，不能使用add/remove/clear方法
- 【强制】集合转数组时使用toArray(T[] array)，传入类型完全一致的数组
- 【强制】避免使用索引进行for循环，优先考虑增强for循环或forEach
- 【建议】合理利用集合的有序性和稳定性

## 控制语句规约

- 【强制】switch语句每个case必须以break、return或抛出异常终止
- 【强制】switch括号内变量可能为null时必须先进行null判断
- 【强制】表达异常的分支少用if-else方式，if-else嵌套请勿超过3层
- 【强制】循环体中的语句要考量性能，避免在循环体内定义对象、获取连接等
- 【建议】循环体内字符串连接使用StringBuilder而非+操作符
