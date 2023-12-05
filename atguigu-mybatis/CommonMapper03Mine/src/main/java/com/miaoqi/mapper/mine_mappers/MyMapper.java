package com.miaoqi.mapper.mine_mappers;

import tk.mybatis.mapper.common.base.select.SelectAllMapper;
import tk.mybatis.mapper.common.example.SelectByExampleMapper;

public interface MyMapper<T>
        extends SelectAllMapper<T>, SelectByExampleMapper<T> {

}
