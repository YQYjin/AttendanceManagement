package com.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.app.dataBase.Evection_infos;

import java.util.List;

public interface EvectionInfosMapper extends BaseMapper<Evection_infos> {
    public List<Evection_infos> selectMonthEvectionInfos(int worker_num, int year, int month);
}
