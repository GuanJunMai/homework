package com.lagou.edu.service;

import com.lagou.edu.annotation.Transactional;

/**
 * @author 应癫
 */
public interface TransferService {

    @Transactional
    void transfer(String fromCardNo,String toCardNo,int money) throws Exception;
}
