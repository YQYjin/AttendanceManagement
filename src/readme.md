- ```js
    //获取当前界面的userID
    //js使用正则表达式返回的是一个捕获组,若要获取匹配的内容,应获取捕获组中第2个值
    var userID = url.match(/\/user\/(\d+)$/)[1];
    ```
- Spring Boot访问静态资源默认在/resources/static/目录下,如将layui文件夹放在该目录下,则连接路径为: