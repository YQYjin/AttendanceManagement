function getLeaveData(callback) {
    var leaveInfoData;
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/leave/all",
        success: function (response) {
            //console.log(JSON.stringify(response));
            leaveInfoData = response;
            // 根据isPass修改
            leaveInfoData.forEach(element => {
                if (element.isPass == 0) {
                    element.isPass = "未审批";
                } else if (element.isPass == 1) {
                    element.isPass = "已通过";
                } else {
                    element.isPass = "未通过";
                }
            });
            callback(response);
        },
        error: function (error) {
            console.log("Error:" + error);
        }
    });
    return leaveInfoData;
}
// 获取出差信息
function getEvectionData(callback) {
    var evectionInfoData;
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/evection/all",
        success: function (response) {
            //console.log(JSON.stringify(response));
            evectionInfoData = response;
            // 根据isPass修改
            evectionInfoData.forEach(element => {
                if (element.isPass == 0) {
                    element.isPass = "未审批";
                } else if (element.isPass == 1) {
                    element.isPass = "已通过";
                } else {
                    element.isPass = "未通过";
                }
            });
            callback(response);
        },
        error: function (error) {
            console.log("Error:" + error);
        }
    });

}

// 渲染请假表格
function renderLeaveTable(table) {
    // 获取请假信息
    var leaveInfoData;
    // 由于ajax是异步请求,因此需要在回调函数中获取数据,并渲染表格
    getLeaveData(function (response) {
        leaveInfoData = response;
        // 渲染表格
        //  var table = layui.table;
        table.render({
            elem: '#leaveInfoTable',
            data: leaveInfoData,
            toolbar: '#toolbar',
            defaultToolbar: ['filter', 'exports'],
            cols: [[
                {field: 'leaveNum', width: 100, title: '请假编号'},
                {field: 'workerNum', width: 100, title: '员工编号'},
                {field: 'workerName', width: 100, title: '员工名称'},
                {field: 'startTime', width: 120, title: '开始时间'},
                {field: 'endTime', width: 120, title: '结束时间'},
                {field: 'reason', title: '原因'},
                {field: 'type', width: 100, title: '类型'},
                {field: 'isPass', width: 100, title: '状态'},
                {fixed: 'right', width: 200, align: 'center', toolbar: '#bar', title: '操作'}
            ]],
            page: true
        });
        // tool监听的是表格每一列的工具,而toolbar监听的是表格的工具栏,即表格上方的工具栏,注意区别
        table.on('tool(leaveInfoTable)', function (obj) {

            if (obj.event == 'approve') {
                // 同意请假操作
                var selectedData = obj.data;
                console.log("同意请假", selectedData);

                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/leave/" + selectedData.leaveNum + "/1",
                    contentType: "application/json;charset=utf-8",
                    success: function (response) {
                        console.log("Success" + response);
                        if (response == 'success') {
                            //重新获取数据并渲染表格
                            renderLeaveTable(table);
                        } else {
                            console.log(("审批失败"));
                        }
                    },
                    error: function (error) {
                        console.log("Error:" + error);
                    }
                });

                // 执行同意请假的逻辑，可以向后台发送请求等
            } else if (obj.event == 'cancel') {
                var selectedData = obj.data;
                console.log("不同意请假", selectedData);

                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/leave/" + selectedData.leaveNum + "/2",
                    contentType: "application/json;charset=utf-8",
                    success: function (response) {
                        console.log("Success" + response);
                        if (response == 'success') {
                            //重新获取数据并渲染表格
                            renderLeaveTable(table);
                        } else {
                            console.log(("审批失败"));
                        }
                    },
                    error: function (error) {
                        console.log("Error:" + error);
                    }
                });
            }
        });
    });
}

// 渲染出差表格
function renderEvectionTable(table) {
    // 获取出差信息
    var evectionInfoData;
    // 由于ajax是异步请求,因此需要在回调函数中获取数据,并渲染表格
    getEvectionData(function (response) {
        evectionInfoData = response;
        //console.log(JSON.stringify(evectionInfoData))
        // 渲染表格
        // var table = layui.table;
        table.render({
            elem: '#evectionInfoTable',
            data: evectionInfoData,
            toolbar: '#toolbar',
            defaultToolbar: ['filter', 'exports'],
            cols: [[
                {field: 'evectionNum', width: 100, title: '出差编号'},
                {field: 'workerNum', width: 100, title: '员工编号'},
                {field: 'workerName', width: 100, title: '员工名称'},
                {field: 'startTime', width: 120, title: '开始时间'},
                {field: 'endTime', width: 120, title: '结束时间'},
                {field: 'reason', title: '原因'},
                {field: 'isPass', width: 100, title: '状态'},
                {fixed: 'right', width: 200, align: 'center', toolbar: '#bar', title: '操作'}
            ]],
            page: true
        });
        table.on('tool(evectionInfoTable)', function (obj) {
            if (obj.event === 'approve') {
                // 同意请假操作
                var selectedData = obj.data;
                // 执行同意请假的逻辑，可以向后台发送请求等

                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/evection/" + selectedData.evectionNum + "/1",
                    contentType: "application/json;charset=utf-8",
                    success: function (response) {
                        console.log("Success" + response);
                        if (response == 'success') {
                            //重新获取数据并渲染表格
                            renderEvectionTable(table);
                        } else {
                            console.log(("审批失败"));
                        }
                    },
                    error: function (error) {
                        console.log("Error:" + error);
                    }
                });

                console.log('同意出差的数据：', selectedData);
            } else if (obj.event === 'cancel') {
                // 取消请假操作
                var selectedData = obj.data;
                // 执行取消请假的逻辑，可以向后台发送请求等
                console.log('取消出差的数据：', selectedData);

                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/evection/" + selectedData.evectionNum + "/2",
                    contentType: "application/json;charset=utf-8",
                    success: function (response) {
                        console.log("Success" + response);
                        if (response == 'success') {
                            //重新获取数据并渲染表格
                            renderEvectionTable(table);
                        } else {
                            console.log(("审批失败"));
                        }
                    },
                    error: function (error) {
                        console.log("Error:" + error);
                    }
                });

            }
        });
    });
}

