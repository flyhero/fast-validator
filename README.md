# fast-validator
验证器

```text

   __                 _                              _   _       _           _                  
  / _|               | |                            | | (_)     | |         | |                 
 | |_    __ _   ___  | |_   ______  __   __   __ _  | |  _    __| |   __ _  | |_    ___    _ __ 
 |  _|  / _` | / __| | __| |______| \ \ / /  / _` | | | | |  / _` |  / _` | | __|  / _ \  | '__|
 | |   | (_| | \__ \ | |_            \ V /  | (_| | | | | | | (_| | | (_| | | |_  | (_) | | |   
 |_|    \__,_| |___/  \__|            \_/    \__,_| |_| |_|  \__,_|  \__,_|  \__|  \___/  |_|   
                                                                                                
                                                                                                
```

## 为什么叫 fast 

就想简单些，快速些。 

## 如何引用

1. 首先你必须使用的是以spring boot 2 为基础的框架

2. pom文件加入依赖

```xml
<dependency>
	<groupId>cn.iflyapi.validator</groupId>
	<artifactId>fast-validator</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
```
3. 启动类配置（如果你需要在方法上注解）

```java
@ComponentScan("cn.iflyapi.validator")
```
                   
## 支持验证方式

- 基于属性注解验证

- 直接传入参数验证
                    