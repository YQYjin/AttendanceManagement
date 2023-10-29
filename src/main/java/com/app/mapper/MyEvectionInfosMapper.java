package com.app.mapper;

import com.app.dataBase.EvectionInfosWithName;

import java.util.List;

public interface MyEvectionInfosMapper {
    public List<EvectionInfosWithName> selectAllEvectionWithName();
    public List<EvectionInfosWithName> selectAllEvectionWithNameByID(int worker_num);
    public List<EvectionInfosWithName> selectAllEvectionWithNameByIDProduce(int worker_num);
}
