package com.app.mapper;

import com.app.dataBase.WorkerWIthDepartment;

public interface MyWorkerMapper {
    public WorkerWIthDepartment selectWorkerWithDepartmentByID(int worker_num);
}
