package com.bjpowernode.money.mapper;

import com.bjpowernode.money.vo.MyBidInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tzsang
 * @create 2022-06-14 20:38
 */
@SpringBootTest
class BidInfoMapperTest {
    @Autowired
    private BidInfoMapper bidInfoMapper;
    @Test
    void selectDescByUid() {
        List<MyBidInfo> myBidInfos = bidInfoMapper.selectDescByUid(27, 1, 4);
        myBidInfos.forEach(System.out::println);
    }
}