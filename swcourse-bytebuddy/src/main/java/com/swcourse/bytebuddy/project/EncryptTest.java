package com.swcourse.bytebuddy.project;

import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swcourse.bytebuddy.mybatis.PunchRecordDO;
import com.swcourse.bytebuddy.mybatis.PunchRecordMapper;
import com.swcourse.bytebuddy.ByteBuddyApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @description：
 * @author: zhanglao
 * @date: 2022/5/18 3:22 下午
 */
@Slf4j
@SpringBootTest(classes = {ByteBuddyApplication.class})
@RunWith(SpringRunner.class)
public class EncryptTest {

    @Autowired
    private PunchRecordMapper punchRecordMapper;

    /**
     * 根据非加密字段查询
     */
    @Test
    public void insertTest() {
        PunchRecordDO punchRecordDO = PunchRecordDO.builder().name("xiaoan").nickName("xiaoanNick")
                .time(new Date()).openId("39393").build();
        punchRecordMapper.insert(punchRecordDO);
    }

    /**
     * 根据非加密字段查询
     */
    @Test
    public void selectTest() {
        PunchRecordDO punchRecordDO = PunchRecordDO.builder().name("xiaoan").build();
        log.info("{}",punchRecordMapper.selectOne(new QueryWrapper(punchRecordDO)));
    }
}
