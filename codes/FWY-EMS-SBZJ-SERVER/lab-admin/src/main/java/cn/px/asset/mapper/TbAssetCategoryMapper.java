package cn.px.asset.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.px.asset.domain.TbAssetCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资产分类Mapper接口
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Mapper
public interface TbAssetCategoryMapper extends BaseMapper<TbAssetCategory>
{
    /**
     * 查询资产分类
     *
     * @param id 资产分类主键
     * @return 资产分类
     */
    public TbAssetCategory selectTbAssetCategoryById(Long id);

    /**
     * 查询资产分类列表
     *
     * @param tbAssetCategory 资产分类
     * @return 资产分类集合
     */
    public List<TbAssetCategory> selectTbAssetCategoryList(TbAssetCategory tbAssetCategory);

    /**
     * 新增资产分类
     *
     * @param tbAssetCategory 资产分类
     * @return 结果
     */
    public int insertTbAssetCategory(TbAssetCategory tbAssetCategory);

    /**
     * 修改资产分类
     *
     * @param tbAssetCategory 资产分类
     * @return 结果
     */
    public int updateTbAssetCategory(TbAssetCategory tbAssetCategory);

    /**
     * 删除资产分类
     *
     * @param id 资产分类主键
     * @return 结果
     */
    public int deleteTbAssetCategoryById(Long id);

    /**
     * 批量删除资产分类
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbAssetCategoryByIds(Long[] ids);
}
