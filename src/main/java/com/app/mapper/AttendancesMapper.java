package com.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.app.dataBase.Attendances;

import java.util.List;

public interface AttendancesMapper extends BaseMapper<Attendances> {
    public List<Attendances> selectMonthAttendance(int workerNum, int year, int month);
}
