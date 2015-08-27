package com.beko.beans.dao;

import com.beko.beans.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ankovalenko on 4/30/2015.
 */
@Transactional
@Repository
public interface UserDAO extends BaseDAO<User>{
}
