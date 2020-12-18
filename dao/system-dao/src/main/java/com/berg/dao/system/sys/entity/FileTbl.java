package com.berg.dao.system.sys.entity;

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
 * 系统文件表
 * </p>
 *
 * @author 
 * @since 2020-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_file_tbl")
public class FileTbl implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统文件表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 业务编码
     */
    private String code;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件全路径
     */
    private String fullPath;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建用户
     */
    private String createUser;

    /**
     * 更新时间
     */
    private LocalDateTime modifyTime;

    /**
     * 修改用户
     */
    private String modifyUser;

    /**
     * 删除时间
     */
    private LocalDateTime delTime;

    /**
     * 删除用户
     */
    private String delUser;

    /**
     * 是否删除(0 否,1 是)
     */
    private Integer isdel;

    /**
     * 类型(0 模板文件 1 其他)
     */
    private Integer type;


}
