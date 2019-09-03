 

[TOC]

姓名：王淑慧

班级 :  软工1704

手机：17342018223

邮件 :   2362468810@qq.com

## 内容提要

|    模块    | 特性             | yes/no | 自我评价（1-5分） | 备注 |
| :--------: | ---------------- | ------ | :---------------: | ---- |
| 数据库设计 | 视图             | yes    |         4         |      |
|            | 存储过程         | yes    |         5         |      |
|            | 触发器           | yes    |         4         |      |
|            | 函数             | yes    |         5         |      |
|            | 自定义类型       | yes    |         5         |      |
|            | 手写SQL代码      | yes    |         5         |      |
| 数据库实现 | SQL 代码lint检查 | yes    |         4         |      |
|  代码管理  | git 工具使用     | yes    |         5         |      |
|  markdown  | typora           | yes    |         4         |      |
|            |                  |        |                   |      |
|  图数据库  |                  | no     |         0         |      |
| 文档数据库 |                  | no     |         0         |      |
|  系统实现  | 实现语言         | yes    |         4         |      |
|            | ORM库            | no     |         0         |      |
|            | Redis            | yes    |         4         |      |
|            | Full text search | yes    |         4         |      |
|            | Message Queue    | yes    |         4         |      |
|            |                  |        |                   |      |

## 数据设计

### （1）数据字典

#### 	1）菜谱

| 名称 |    菜谱信息    |
| :--: | :------------: |
| 别名 |    beanbook    |
| 描述 | 菜谱的详细信息 |
| 定义 |    菜谱信息    |

| 表名 | beanbook |              |             |
| :--: | :------: | :----------: | :---------: |
| 含义 | 菜谱信息 |              |             |
| 序号 |  Field   |     Type     |    Extra    |
|  1   | barcode  | varchar(20)  | PRIMARY KEY |
|  2   | bookname | varchar(200) |             |
|  3   |  pubid   | varchar(20)  | Allow Null  |
|  4   |  price   |    double    |             |
|  5   |  state   | varchar(20)  |             |
|  6   |   step   | varchar(100) | Allow Null  |
|  7   |   need   | varchar(100) | Allow Null  |
|  8   |   eva    | varchar(100) | Allow Null  |
|  9   |  stepid  | varchar(20)  | Allow Null  |
|  10  |   brid   | varchar(20)  | Allow Null  |
|  11  |  evaid   | varchar(20)  | Allow Null  |
|  12  | viewnum  |   int(100)   |             |
|  13  | likenum  |   int(100)   |             |

|      Index      | Fields | Extra |
| :-------------: | :----: | :---: |
|  fk_pubid_idx   | pubid  |       |
| fk_bookstep_idx | stepid |       |
| fk_bookfood_idx |  brid  |       |
| fk_bookeva_idx  | evaid  |       |

#### 	2）菜谱评价

| 名称 |      菜谱评价      |
| :--: | :----------------: |
| 别名 |    beanbookeva     |
| 描述 | 菜谱评价的详细信息 |
| 定义 |      菜谱评价      |

| 表名 | beanbookeva |             |             |
| :--: | :---------: | :---------: | :---------: |
| 含义 |  菜谱评价   |             |             |
| 序号 |    Field    |    Type     |    Extra    |
|  1   |   barcode   | varchar(20) |             |
|  2   |    evaid    | varchar(20) | PRIMARY KEY |
|  3   |   evades    | varchar(50) | Allow Null  |
|  4   |   userid    | varchar(20) |             |
|  5   |   viewtag   | varchar(20) | Allow Null  |
|  6   |   liketag   | varchar(20) | Allow Null  |

|     Index      | Fields  | Extra |
| :------------: | :-----: | :---: |
| fk_menueva_idx | barcode |       |

#### 	3）下单

| 名称 |        下单        |
| :--: | :----------------: |
| 别名 | beanbooklendrecord |
| 描述 | 菜谱下单的详细信息 |
| 定义 |        购买        |

| 表名 | beanbooklendrecord |             |                            |
| :--: | :----------------: | :---------: | :------------------------: |
| 含义 |        下单        |             |                            |
| 序号 |       Field        |    Type     |           Extra            |
|  1   |         id         |   int(11)   | PRIMARY KEY/Auto Increment |
|  2   |   lendOperUserid   | varchar(20) |                            |
|  3   |      readerid      | varchar(20) |                            |
|  4   |      lendDate      |  datetime   |         Allow Null         |
|  5   |     returnDate     |  datetime   |         Allow Null         |
|  6   |  returnOperUserid  | varchar(20) |                            |
|  7   |      penalSum      |   int(11)   |                            |
|  8   |    readernumber    |  int(100)   |                            |
|  9   |    recordstate     | varchar(20) |                            |

|       Index       |      Fields      | Extra |
| :---------------: | :--------------: | :---: |
|   fk_reader_idx   |     readerid     |       |
|  fk_lendOper_idx  |  lendOperUserid  |       |
| fk_returnOper_idx | returnOperUserid |       |

#### 	4）菜谱所需食材

| 名称 |      菜谱所需食材      |
| :--: | :--------------------: |
| 别名 |     beanbookreader     |
| 描述 | 菜谱所需食材的详细信息 |
| 定义 |      菜谱所需食材      |

| 表名 |  beanbookreader  |             |             |
| :--: | :--------------: | :---------: | :---------: |
| 含义 |   菜谱所需食材   |             |             |
| 序号 |      Field       |    Type     |    Extra    |
|  1   |       brid       | varchar(20) | PRIMARY KEY |
|  2   |     barcode      | varchar(20) |             |
|  3   |     readerid     | varchar(10) |             |
|  4   | bookreadernumber |   int(10)   | Allow Null  |

|     Index      |  Fields  | Extra |
| :------------: | :------: | :---: |
| fk_frommenu_id | barcode  |       |
| fk_needfood_id | readerid |       |

#### 	5）菜谱步骤

| 名称 |      菜谱步骤      |
| :--: | :----------------: |
| 别名 |    beanbookstep    |
| 描述 | 菜谱步骤的详细信息 |
| 定义 |      菜谱步骤      |

| 表名 | beanbookstep |              |             |
| :--: | :----------: | :----------: | :---------: |
| 含义 |   菜谱步骤   |              |             |
| 序号 |    Field     |     Type     |    Extra    |
|  1   |    stepid    | varchar(20)  | PRIMARY KEY |
|  2   |   stepdes    | varchar(100) | Allow Null  |
|  3   |   barcode    | varchar(20)  |             |

|       Index        | Fields | Extra |
| :----------------: | :----: | :---: |
|       stepid       | stepid |       |
| fk_menu_idxbarcode |        |       |

#### 	6）菜谱发布者

| 名称 |      菜谱发布者      |
| :--: | :------------------: |
| 别名 |    beanpublisher     |
| 描述 | 菜谱发布者的详细信息 |
| 定义 |      菜谱发布者      |

| 表名 | beanpublisher |              |             |
| :--: | :-----------: | :----------: | :---------: |
| 含义 |  菜谱发布者   |              |             |
| 序号 |     Field     |     Type     |    Extra    |
|  1   |     pubid     | varchar(20)  | PRIMARY KEY |
|  2   | publisherName | varchar(50)  |             |
|  3   |    address    | varchar(200) | Allow Null  |

|        Index         |    Fields     | Extra  |
| :------------------: | :-----------: | :----: |
| publisherName_UNIQUE | publisherName | Unique |

#### 	7）食材

| 名称 |    食材信息    |
| :--: | :------------: |
| 别名 |   beanreader   |
| 描述 | 食材的详细信息 |
| 定义 |    食材信息    |

| 表名 |    beanreader    |             |             |
| :--: | :--------------: | :---------: | :---------: |
| 含义 |     食材信息     |             |             |
| 序号 |      Field       |    Type     |    Extra    |
|  1   |     readerid     | varchar(20) | PRIMARY KEY |
|  2   |    readerName    | varchar(50) |             |
|  3   |   readerTypeId   |   int(11)   |             |
|  4   | lendBookLimitted |   int(11)   |             |
|  5   |    createDate    |  datetime   |             |
|  6   |  creatorUserId   | varchar(20) |             |
|  7   |    removeDate    |  datetime   | Allow Null  |
|  8   |  removerUserId   | varchar(20) | Allow Null  |
|  9   |     stopDate     |  datetime   | Allow Null  |
|  10  |    stopUserId    | varchar(20) | Allow Null  |
|  11  |   readerstate    | varchar(20) |             |
|  12  |       brid       | varchar(20) | Allow Null  |
|  13  |   readerprice    |   int(20)   |             |

|     Index      |    Fields     | Extra |
| :------------: | :-----------: | :---: |
| fk_creator_idx | creatorUserId |       |
| fk_remover_idx | removerUserId |       |
| fk_stopper_idx |  stopUserId   |       |
|  fk_isfood_id  |     brid      |       |

#### 	8）食材类别

| 名称 |      食材类别      |
| :--: | :----------------: |
| 别名 |   beanreadertype   |
| 描述 | 食材类别的详细信息 |
| 定义 |      食材类别      |

| 表名 |  beanreadertype  |             |                            |
| :--: | :--------------: | :---------: | :------------------------: |
| 含义 |     食材类别     |             |                            |
| 序号 |      Field       |    Type     |           Extra            |
|  1   |   readerTypeId   |   int(11)   | PRIMARY KEY/Auto Increment |
|  2   |  readerTypeName  | varchar(50) |                            |
|  3   | lendBookLimitted |   int(11)   |                            |

|         Index         |     Fields     | Extra  |
| :-------------------: | :------------: | :----: |
| readerTypeName_UNIQUE | readerTypeName | Unique |

#### 	9）用户

| 名称 |      食材类别      |
| :--: | :----------------: |
| 别名 |   beanreadertype   |
| 描述 | 食材类别的详细信息 |
| 定义 |      食材类别      |

| 表名 | beanreadertype |              |             |
| :--: | :------------: | :----------: | :---------: |
| 含义 |    食材类别    |              |             |
| 序号 |     Field      |     Type     |    Extra    |
|  1   |     userid     | varchar(20)  | PRIMARY KEY |
|  2   |    username    | varchar(50)  |             |
|  3   |      pwd       | varchar(32)  |             |
|  4   |    usertype    | varchar(20)  |             |
|  5   |   createDate   |   datetime   |             |
|  6   |   removeDate   |   datetime   | Allow Null  |
|  7   |    usersex     | varchar(20)  | Allow Null  |
|  8   |   userphone    | varchar(20)  | Allow Null  |
|  9   |   useremail    | varchar(50)  | Allow Null  |
|  10  |  useraddress   | varchar(100) | Allow Null  |
|  11  |     evaid      | varchar(20)  | Allow Null  |

|        Index        | Fields | Extra |
| :-----------------: | :----: | :---: |
| fk_systemusereva_id | evaid  |       |

### （2）ER图

![](D:\WSH short\图\kitchenER图.png)

 [kitchen.cdm](ER\kitchen.cdm) 

 [kitchen.pdm](ER\kitchen.pdm) 

### （3）体系结构图

![](D:\WSH short\图\kitchen体系结构图.png)

 [体系结构图.ddd](图\体系结构图.ddd) 

### （4）系统流程图

![](D:\WSH short\图\kitchen流程图.png)

 [流程图.vsd](图\流程图.vsd) 

### （5）功能分解图

![](D:\WSH short\图\kitchen功能分解图.png)

 [功能分解图.ddd](图\功能分解图.ddd) 

## 系统实现

### （1）管理员功能

#### 	1）注册

​			在注册页面输入账号姓名并选择权限，密码默认为账号一致。

​			同时跳出添加菜谱发布者的添加框。

#### 	2）登录

​			输入用户账号与密码即可登录。

#### 	3）用户管理

​			管理员可以在用户管理窗口 添加用户、重置用户密码、删除用户

#### 	4）食材类别管理

​			管理员可以在食材类别管理窗口 添加食材类别、修改食材类别、删除食材类别

#### 	5）食材管理

​			管理员可以在食材管理窗口 添加食材、修改食材、删除食材，并修改食材状态

#### 	6）菜谱发布者管理

​			管理员可以在菜谱发布者管理窗口 添加菜谱发布者、修改菜谱发布者、删除菜谱发布者

#### 	7）菜谱管理

​			管理员可以在菜谱管理窗口 添加菜谱、修改菜谱、删除菜谱、查看菜谱详细信息并修改

#### 	8）订单管理

​		管理员可以在订单管理窗口 添加订单、修改订单、删除订单、查看订单详细信息并修改

#### 	9）其他功能

​		普通用户的所有功能

### （2）普通用户功能

#### 	1）下单

​		用户可以在下单窗口 查询食材情况并选择需要食材输入数量 即可下单

#### 	2）个人信息

​		用户可以在个人信息窗口 修改个人信息、重置密码、注销账号

#### 	3）查询

​		用户可以在查询窗口 查询各个菜谱和食材

#### 	4）浏览

​		用户可以点击查看按钮查看所选菜谱的详细信息，并可评论该菜谱

#### 	5）收藏

​		-用户可以点击收藏按钮收藏所选菜谱

#### 	6）创建菜谱

​		用户可以发布自创的菜谱，并管理自己的菜谱

## 个人小结

​	通过本次课程，对java有了更深的理解，同时也对数据库的操作，以及将现实中的事务转化成数据模型有了更深的理解，同时也对这一方面掌握的更加好了。同样的，在本次课程中，从ER图设计，到软件的开发，学习到了很多课堂之外的知识，比如说利用一些插件来帮助软件代码的实现等等，这些在课堂上可能不会接触到，但是在本次时间中，我学会了如何应用。

​	但是在写代码的过程中，也遇到了一些问题，比如说命名的规范，数据库与Java的映射等等，实践出真知，可以说在本次的课程中，通过实践使我的理论知识水平掌握的更好。

​	设计的时候遇到最大的问题就是关系复杂，并且表多容易弄混，但是只要一步一步理清逻辑，也能非常快的做出来。

​	在本课程设计中，我明白了理论与实际应用相结合的重要性，并提高了自己设计表及编写大型项目的能力。这次课程设计提高了我的综合运用所学知识的能力，上机实践是对学生全面综合素质进行训练的一种最基本的方法，是与课堂听讲、自学和练习相辅相成的、必不可少的一个教学环节。上机实习一方面能使书本上的知识变“活”，起到深化理解和灵活掌握教学内容的目的;另一方面，上机实习是对学生软件设计的综合能力的训练，包括问题分析，总体结构设计，程序设计基本技能和技巧的训练。

​	在这短短半个月的学习中，受益匪浅。在对数据库进行操作的时候，我对于数据库中外键，主键，以及约束条件的设定也有了更深的认识





