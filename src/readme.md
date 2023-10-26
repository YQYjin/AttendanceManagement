- ```js
    //获取当前界面的userID
    //js使用正则表达式返回的是一个捕获组,若要获取匹配的内容,应获取捕获组中第2个值
    var userID = url.match(/\/user\/(\d+)$/)[1];
    ```
- Spring Boot访问静态资源默认在/resources/static/目录下,如将layui文件夹放在该目录下,则连接路径为:
- Spring Boot默认会阻止所有静态资源访问,因此需要配置MvcConfig类,继承WebMvcConfigurerAdapter,重写addResourceHandlers方法,添加静态资源访问路径

- tool监听的是表格每一列的工具,而toolbar监听的是表格的工具栏,即表格上方的工具栏,注意区别
  table.on('tool(leaveInfoTable)', function (obj) {
- 有时修改了js文件,但浏览器依旧获取到的是旧的js文件,原因是开启了缓存,解决方法f12- 网络-禁用缓存