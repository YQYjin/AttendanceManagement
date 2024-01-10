function getLeaveData(callback) {
    var leaveInfoData;
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/admin/leave/all",
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
        url: "http://localhost:8080/admin/evection/all",
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
// 统计考勤信息
function analyzeAttendanceData() {
    inputDate = $("#date").val();
    nowDate = new Date();
    //判断输入年月是否大于当前年月

    if (inputDate >= nowDate.getFullYear() + "-" + (nowDate.getMonth() + 1)) {
        window.alert("只能统计本月之前的考勤信息,请重新输入年月");
        return;
    }

    let monthAndYear = {
        date: $("#date").val()
    };
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/admin/analyze/all",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(monthAndYear),
        success: function (response) {
            if (response == "success") {
                window.alert("统计成功");
            } else {
                window.alert("统计失败");
            }
        },
        error: function (error) {
            console.log("Error:" + error);
        }
    });
}
// 获取考勤信息
function getAttendanceData(table) {
    let monthAndYear = {
        date: $("#date").val()
    };
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/admin/query/all",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(monthAndYear),
        success: function (response) {

            let attendanceData = response;

            console.log(attendanceData);
            table.render({
                elem: '#allAttendanceTable',
                data: attendanceData,
                toolbar: '#toolbar',
                defaultToolbar: ['filter', 'exports'],
                cols: [[
                    { field: 'workerNum', title: '员工编号' },
                    { field: 'workerName', title: '员工姓名' },
                    { field: 'year', title: '年份' },
                    { field: 'month', title: '月份' },
                    { field: 'isFullAttendance', title: '是否全勤' },
                    { field: 'absenceTimes', title: '缺勤天数' },
                    { field: 'sickTimes', title: '病假天数' },
                    { field: 'personalTimes', title: '事假天数' },
                    { field: 'evectionTimes', title: '出差天数' },
                    { field: 'overtimeHours', title: '加班时长' }
                ]],
                page: true
            });

        },
        error: function (error) {
            console.log("Error:" + error);
        }
    });
}
function getOneAttendanceData(table) {
    inputDate = $("#date2").val();
    nowDate = new Date();
    //判断输入年月是否大于当前年月

    if (inputDate >= nowDate.getFullYear() + "-" + (nowDate.getMonth() + 1)) {
        window.alert("只能统计本月之前的考勤信息,请重新输入年月");
        return;
    }
    let workerName = $("#workerName").val();
    if (workerName == "") {
        window.alert("请输入员工姓名");
        return;
    }
    let monthAndYear = {
        date: inputDate,
        workerName: workerName
    };
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/admin/query/one",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(monthAndYear),
        success: function (response) {

            let attendanceData = response;
            if (response == "" || response == null) {
                window.alert("未查询到该员工");
                return;
            }
            console.log(attendanceData);
            table.render({
                elem: '#oneAttendanceTable',
                data: attendanceData,
                toolbar: '#toolbar',
                defaultToolbar: ['filter', 'exports'],
                cols: [[
                    { field: 'workerNum', title: '员工编号' },
                    { field: 'workerName', title: '员工姓名' },
                    { field: 'year', title: '年份' },
                    { field: 'month', title: '月份' },
                    { field: 'isFullAttendance', title: '是否全勤' },
                    { field: 'absenceTimes', title: '缺勤天数' },
                    { field: 'sickTimes', title: '病假天数' },
                    { field: 'personalTimes', title: '事假天数' },
                    { field: 'evectionTimes', title: '出差天数' },
                    { field: 'overtimeHours', title: '加班时长' }
                ]],
                page: true
            });

        },
        error: function (error) {
            console.log("Error:" + error);
        }
    });
}
// 渲染请假表格
function renderLeaveTable(table, layer) {
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
                { field: 'leaveNum', width: 100, title: '请假编号' },
                { field: 'workerNum', width: 100, title: '员工编号' },
                { field: 'workerName', width: 100, title: '员工名称' },
                { field: 'startTime', width: 120, title: '开始时间' },
                { field: 'endTime', width: 120, title: '结束时间' },
                { field: 'reason', title: '原因' },
                { field: 'type', width: 100, title: '类型' },
                { field: 'isPass', width: 100, title: '状态' },
                { fixed: 'right', width: 200, align: 'center', toolbar: '#bar', title: '操作' }
            ]],
            page: true
        });
        //tool监听的是表格每一列的工具,而toolbar监听的是表格的工具栏,即表格上方的工具栏,注意区别
        // table.on('tool(leaveInfoTable)', function (obj) {
        //     if (obj.event == 'approve') {
        //         // 同意请假操作
        //         var selectedData = obj.data;
        //         console.log("同意请假", selectedData);
        //         $.ajax({
        //             type: "POST",
        //             url: "http://localhost:8080/admin/leave/" + selectedData.leaveNum + "/1",
        //             contentType: "application/json;charset=utf-8",
        //             success: function (response) {
        //                 console.log("Success" + response);
        //                 if (response == 'success') {
        //                     //重新获取数据并渲染表格
        //                     renderLeaveTable(table);
        //                 } else {
        //                     console.log(("审批失败"));
        //                 }
        //             },
        //             error: function (error) {
        //                 console.log("Error:" + error);
        //             }
        //         });

        //         // 执行同意请假的逻辑，可以向后台发送请求等
        //     } else if (obj.event == 'cancel') {
        //         var selectedData = obj.data;
        //         console.log("不同意请假", selectedData);

        //         $.ajax({
        //             type: "POST",
        //             url: "http://localhost:8080/admin/leave/" + selectedData.leaveNum + "/2",
        //             contentType: "application/json;charset=utf-8",
        //             success: function (response) {
        //                 console.log("Success" + response);
        //                 if (response == 'success') {
        //                     //重新获取数据并渲染表格
        //                     renderLeaveTable(table);
        //                 } else {
        //                     console.log(("审批失败"));
        //                 }
        //             },
        //             error: function (error) {
        //                 console.log("Error:" + error);
        //             }
        //         });
        //     }
        // });

        //tool监听的是表格每一列的工具,而toolbar监听的是表格的工具栏,即表格上方的工具栏,注意区别
        table.on('tool(leaveInfoTable)', function (obj) {
            if (obj.event == 'approve') {
                // 同意请假操作
                var selectedData = obj.data;
                console.log("同意请假", selectedData);

                layer.open({
                    type: 1,
                    title: '审批意见',
                    area: ['400px', '200px'],
                    content: $('#approvePopup'),
                    success: function (layero, index) {
                        // 清空输入框
                        $("input[name='approveOpinion']").val('');

                    }
                });
                $("#okBtn").on("click", function () {
                    let opinion = $("#opinion").val();
                    console.log('审批意见：', opinion);
                    layer.closeAll();
                    $("#okBtn").off("click");
                    $("#cancelBtn").off("click");
                    let formData = {
                        opinion: opinion
                    };
                    $.ajax({
                        type: "POST",
                        url: "http://localhost:8080/admin/leave/" + selectedData.leaveNum + "/1",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(formData),
                        success: function (response) {
                            console.log("Success" + response);
                            if (response == 'success') {
                                //重新获取数据并渲染表格
                                renderLeaveTable(table, layer);
                            } else {
                                console.log(("审批失败"));
                            }
                        },
                        error: function (error) {
                            console.log("Error:" + error);
                        }
                    });
                });
                $("#cancelBtn").on("click", function () {
                    $("#okBtn").off("click");
                    $("#cancelBtn").off("click");
                    layer.closeAll();
                });
                // 执行同意请假的逻辑，可以向后台发送请求等
            } else if (obj.event == 'cancel') {
                var selectedData = obj.data;
                console.log("不同意请假", selectedData);

                layer.open({
                    type: 1,
                    title: '审批意见',
                    area: ['400px', '200px'],
                    content: $('#approvePopup'),
                    success: function (layero, index) {
                        // 清空输入框
                        $("input[name='approveOpinion']").val('');

                    }
                });


                $("#okBtn").on("click", function () {
                    let opinion = $("#opinion").val();
                    console.log('审批意见：', opinion);
                    layer.closeAll();
                    let formData = {
                        opinion: opinion
                    };
                    $.ajax({
                        type: "POST",
                        url: "http://localhost:8080/admin/leave/" + selectedData.leaveNum + "/2",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(formData),
                        success: function (response) {
                            console.log("Success" + response);
                            if (response == 'success') {
                                //重新获取数据并渲染表格
                                renderLeaveTable(table, layer);
                            } else {
                                console.log(("审批失败"));
                            }
                        },
                        error: function (error) {
                            console.log("Error:" + error);
                        }
                    });
                });
                $("#cancelBtn").on("click", function () {
                    layer.closeAll();
                });
            }
        });
    });
}

// 渲染出差表格
function renderEvectionTable(table, layer) {
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
                { field: 'evectionNum', width: 100, title: '出差编号' },
                { field: 'workerNum', width: 100, title: '员工编号' },
                { field: 'workerName', width: 100, title: '员工名称' },
                { field: 'startTime', width: 120, title: '开始时间' },
                { field: 'endTime', width: 120, title: '结束时间' },
                { field: 'reason', title: '原因' },
                { field: 'isPass', width: 100, title: '状态' },
                { fixed: 'right', width: 200, align: 'center', toolbar: '#bar', title: '操作' }
            ]],
            page: true
        });
        table.on('tool(evectionInfoTable)', function (obj) {
            if (obj.event === 'approve') {
                // 同意请假操作
                var selectedData = obj.data;
                // 执行同意请假的逻辑，可以向后台发送请求等

                layer.open({
                    type: 1,
                    title: '审批意见',
                    area: ['400px', '200px'],
                    content: $('#approvePopup'),
                    success: function (layero, index) {
                        // 清空输入框
                        $("input[name='approveOpinion']").val('');

                    }
                });

                $("#okBtn").on("click", function () {
                    let opinion = $("#opinion").val();
                    console.log('审批意见：', opinion);
                    layer.closeAll();
                    $("#okBtn").off("click");
                    $("#cancelBtn").off("click");
                    let formData = {
                        opinion: opinion
                    };

                    $.ajax({
                        type: "POST",
                        url: "http://localhost:8080/admin/evection/" + selectedData.evectionNum + "/1",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(formData),
                        success: function (response) {
                            console.log("Success" + response);
                            if (response == 'success') {
                                //重新获取数据并渲染表格
                                renderEvectionTable(table, layer);
                            } else {
                                console.log(("审批失败"));
                            }
                        },
                        error: function (error) {
                            console.log("Error:" + error);
                        }
                    });
                });
                $("#cancelBtn").on("click", function () {
                    $("#okBtn").off("click");
                    $("#cancelBtn").off("click");
                    layer.closeAll();
                });
                console.log('同意出差的数据：', selectedData);
            } else if (obj.event === 'cancel') {
                // 取消请假操作
                var selectedData = obj.data;
                // 执行取消请假的逻辑，可以向后台发送请求等
                console.log('取消出差的数据：', selectedData);

                layer.open({
                    type: 1,
                    title: '审批意见',
                    area: ['400px', '200px'],
                    content: $('#approvePopup'),
                    success: function (layero, index) {
                        // 清空输入框
                        $("input[name='approveOpinion']").val('');

                    }
                });

                $("#okBtn").on("click", function () {
                    let opinion = $("#opinion").val();
                    console.log('审批意见：', opinion);
                    layer.closeAll();
                    $("#okBtn").off("click");
                    $("#cancelBtn").off("click");
                    let formData = {
                        opinion: opinion
                    };
                    $.ajax({
                        type: "POST",
                        url: "http://localhost:8080/admin/evection/" + selectedData.evectionNum + "/2",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(formData),
                        success: function (response) {
                            console.log("Success" + response);
                            if (response == 'success') {
                                //重新获取数据并渲染表格
                                renderEvectionTable(table, layer);
                            } else {
                                console.log(("审批失败"));
                            }
                        },
                        error: function (error) {
                            console.log("Error:" + error);
                        }
                    });
                });
                $("#cancelBtn").on("click", function () {
                    $("#okBtn").off("click");
                    $("#cancelBtn").off("click");
                    layer.closeAll();
                });
            }
        });
    });
}

