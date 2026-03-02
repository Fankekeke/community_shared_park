package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 满意度评价
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EvaluateInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 评价编号
     */
    private String code;

    /**
     * 所属订单
     */
    private Integer orderId;

    /**
     * 车牌号
     */
    private String vehicleNo;

    /**
     * 所属用户
     */
    private Integer userId;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 评价图
     */
    private String images;

    /**
     * 评价分数
     */
    private Integer score;


}
