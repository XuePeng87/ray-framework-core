package cc.xuepeng.ray.framework.core.util.entity.page;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 使用框架提供的Mybatis提供的BaseDao做分页查询时，需要使用本类保存查询结果。
 * 本类保存的查询结果信息有，当前页数、每页显示的条数、总条数、总页数和当前页数据的List集合。
 * 本类采用此方式定义数据结果，希望能满足大部分调用者的DataGrid展示分页信息与数据。
 *
 * @param <T> 实现本接口的泛型类（Model）。
 * @author xuepeng
 */
@Data
@NoArgsConstructor
@ToString
public class PageResult<T> {

    /**
     * 构造函数。
     *
     * @param currentPage 当前页数。
     * @param pageSize    每页显示行数。
     * @param totalCount  总页数。
     * @param record      本页的数据列表。
     */
    public PageResult(long currentPage, long pageSize, long totalCount, List<T> record) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        // 计算总页码
        pageCount = (totalCount + pageSize - 1) / pageSize;
        this.record = record;
    }

    /**
     * 当前页数。
     */
    private long currentPage;
    /**
     * 每页显示多少条。
     */
    private long pageSize;
    /**
     * 总记录数。
     */
    private long totalCount;
    /**
     * 总页数。
     */
    private long pageCount;
    /**
     * 本页的数据列表。
     */
    private List<T> record;

}