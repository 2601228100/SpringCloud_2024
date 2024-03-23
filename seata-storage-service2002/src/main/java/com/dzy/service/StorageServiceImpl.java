package com.dzy.service;


import com.dzy.mapper.StorageMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageMapper storageMapper;

    /**
     * 扣减库存
     *
     * @param productId
     * @param count
     */
    @Override
    public void decrease(Long productId, Integer count) {
        log.info("------->storage-service中扣减库存开始");
        storageMapper.decrease(productId,count);
        log.info("------->storage-service中扣减库存结束");

    }
}
