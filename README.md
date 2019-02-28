# 模拟自动贩卖机demo

本demo模拟与自动贩卖机配合, 完成自动贩卖的功能. 

接收贩卖机提供的各种信号, 包括:  投币, 选择商品, 确认, 取消. 

## 数据采用内存数据库H2

初始化表结构sql:classpath:db/schema.sql

初始化数据目录:classpath:db/data.sql

## 工程结构介绍:

| 包路径名            | 含义                                         |
| ------------------- | -------------------------------------------- |
| common              | 公共包: 包含util, 常量类等公共使用的包路径   |
| config              | 配置信息类: 包含各种基于JavaConfig的配置类   |
| controller          | 对外提供接口, rest风格                       |
| dao.mapper          | mybatis访问H2数据库mapper                      |
| dao.repository      | mongodb访问数据库repository                  |
| event               | 事件                                         |
| listener            | 监听                                         |
| model.domain        | mongodb实体                                  |
| model.entity        | mysql实体                                    |
| model.vo            | 接口返回结构                                 |
| model.dto           | 数据传输结构                                 |
| service             | 服务结构                                     |
| service.impl        | 服务实现                                     |
| task                | 定时任务                                     |
| sql                 | mybatis的sql文件                             |
| classpath:db        | h2内存数据库初始化sql文件存放路径            |
| classpath:config    | spring配置文件路径  基于yml配置文件          |
| classpath:static    | 静态文件, 一般存放js,css等文件, 本demo中没有 |
| classpath:templates | 页面存放的路径                               |


## 测试方式:
运行DemoVendingMachineApplicationTests, 此类中的方法.
  - test1: 10美分, 25美分, 选择巧克力, 确认, 无找零(已实现)
  - test2: 选择巧克力, 1美元, 确认, 找零65美分(已实现)
  - test3: 1美元, 选择腰果, 确认, 找零45美分(已实现)
  - test4: 1美元, 选择巧克力,取消, 返回100美分(未实现) 
  
## task:
  - 定时提交record数据到远程服务, 提交成功后, 删除本地数据.(未实现)
  - 定时修改5分钟以前的status为0的数据为取消.记录有钱不吐. (未实现)

  