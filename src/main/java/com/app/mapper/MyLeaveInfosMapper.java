package com.app.mapper;

import com.app.dataBase.LeaveInfoWithName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface MyLeaveInfosMapper{
    public List<LeaveInfoWithName> selectAllLeaveWithName();
}
