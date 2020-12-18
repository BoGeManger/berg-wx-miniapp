package com.berg.dao.system.ma.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 小程序模板消息记录表
 * </p>
 *
 * @author 
 * @since 2020-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ma_msg_record_tbl")
public class MsgRecordTbl implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 小程序模板消息记录表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 微信小程序appId
     */
    private String appId;

    /**
     * 微信小程序openId
     */
    private String openId;

    /**
     * 模板消息内容
     */
    private String data;

    /**
     * 模板消息id
     */
    private String templateId;

    /**
     * 模板消息跳转页面
     */
    private String page;

    /**
     * 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版
     */
    private String miniappState;

    /**
     * 语言类型
     */
    private String lang;

    /**
     * 描述
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
