function getLeaveData(callback,userID) {
    var leaveInfoData;
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/leave/"+userID,
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
function getEvectionData(callback,userID) {
    var evectionInfoData;
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/evection/"+userID,
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
function renderLeaveTable(table,userID) {
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
    },userID);
}

// 渲染出差表格
function renderEvectionTable(table,userID) {
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
    });
}

