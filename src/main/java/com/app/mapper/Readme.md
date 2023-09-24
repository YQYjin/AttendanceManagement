- mapper包用于存放mybatis的mapper接口，以连接数据库并执行相应操作
- 接口继承BaseMapper,BaseMapper中已经实现了基本的增删改查方法,不需要再自己写
- 命名规范: 表名+mapper
- 太傻逼了,xml映射改了一下午,结果问题是<select>中的resultType写错了,没有加限定性类名,导致找不到类,应该写为"com.app.dataBase.LeaveInfoWithName",结果写成了"LeaveInfoWithName",导致找不到类,真是太傻逼了
- 写xml文件时一定加上xml文档类型的说明,就可以让IDE自动查找错误,如
- ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  ```