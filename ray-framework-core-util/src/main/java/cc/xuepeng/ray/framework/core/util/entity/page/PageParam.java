package cc.xuepeng.ray.framework.core.util.entity.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用框架提供的Mybatis提供的BaseDao做分页查询时，需要使用本类传递分页信息。
 * 本类提供了当前页数和每页记录数两个参数，供分页查询时传递分页信息。
 *
 * @author xuepeng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页数。
     */
    private Integer pageNum;
    /**
     * 每页记录数。
     */
    private Integer pageSize;
    /**
     * 排序字段。
     */
    private transient List<OrderInfo> orders = new ArrayList<>();

}