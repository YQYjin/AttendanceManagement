package com.app.mapper;

import com.app.dataBase.EvectionInfosWithName;

import java.util.List;

public interface MyEvectionInfosMapper {
    public List<EvectionInfosWithName> selectAllEvectionWithName();
}
