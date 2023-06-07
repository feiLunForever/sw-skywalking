package com.swcourse.dump;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swcourse.dump.PunchRecordDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 打卡记录表 Mapper  接口
 * @author zhangyuqiang
 * @since 2021-11-26
 */
@Mapper
public interface PunchRecordMapper extends BaseMapper<PunchRecordDO> {

}
