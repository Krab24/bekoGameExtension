package com.beko.beans.dao;

import com.beko.beans.entity.ChatMessage;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ankovalenko on 5/20/2015.
 */
@Transactional
@Repository
public interface ChatDAO extends BaseDAO<ChatMessage>{
}
