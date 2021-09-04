package com.auto.mapper;

import com.auto.entity.QueryParam;
import com.auto.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 15:20
 */
@Repository
public interface UserMapper {

    User Sel(int id);

    void insert(User user);


    List<String>selectSlice(QueryParam queryParam);


    List<User> selectSliceById(QueryParam queryParam);


    List<User> selectAll(QueryParam queryParam);
}

