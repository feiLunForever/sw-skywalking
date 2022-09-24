package com.swcourse.dump;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 打卡记录表
 * @author zhangyuqiang
 * @since 2021-11-26
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("punch_record")
public class PunchRecordDO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Date time;

    private String openId;

    private String nickName;

    private String name;
}
