package com.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.app.dataBase.Leave_infos;

import java.util.List;

public interface LeaveInfosMapper extends BaseMapper<Leave_infos> {
    public List<Leave_infos> selectMonthLeaveInfos(int worker_num, int year, int month);
}
