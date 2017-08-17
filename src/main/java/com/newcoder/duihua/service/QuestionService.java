package com.newcoder.duihua.service;

import com.newcoder.duihua.dao.QuestionDao;
import com.newcoder.duihua.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangdong on 2017/8/3.
 */
@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    public List<Question> getLatestQuestions(int userId,int offset,int limit) {
        return questionDao.selectLatestQuestions(userId, offset,limit);
    }
}
