package cn.px.asset.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.px.common.annotation.Log;
import cn.px.common.core.controller.BaseController;
import cn.px.common.core.domain.AjaxResult;
import cn.px.common.enums.BusinessType;
import cn.px.asset.domain.TbAssetStatusRecord;
import cn.px.asset.service.ITbAssetStatusRecordService;
import cn.px.common.utils.poi.ExcelUtil;
import cn.px.common.core.page.TableDataInfo;

/**
 * 资产设备状态记录Controller
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Api(tags = "资产设备状态记录")
@RestController
@RequestMapping("/asset/record")
public class TbAssetStatusRecordController extends BaseController
{
    @Autowired
    private ITbAssetStatusRecordService tbAssetStatusRecordService;

    /**
     * 查询资产设备状态记录列表
     */
    @ApiOperation("查询资产设备状态记录列表")
    @PreAuthorize("@ss.hasPermi('asset:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbAssetStatusRecord tbAssetStatusRecord)
    {
        startPage();
        List<TbAssetStatusRecord> list = tbAssetStatusRecordService.selectTbAssetStatusRecordList(tbAssetStatusRecord);
        return getDataTable(list);
    }

    /**
     * 导出资产设备状态记录列表
     */
    @ApiOperation("导出资产设备状态记录列表")
    @PreAuthorize("@ss.hasPermi('asset:record:export')")
    @Log(title = "资产设备状态记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbAssetStatusRecord tbAssetStatusRecord)
    {
        List<TbAssetStatusRecord> list = tbAssetStatusRecordService.selectTbAssetStatusRecordList(tbAssetStatusRecord);
        ExcelUtil<TbAssetStatusRecord> util = new ExcelUtil<TbAssetStatusRecord>(TbAssetStatusRecord.class);
        util.exportExcel(response, list, "资产设备状态记录数据");
    }

    /**
     * 获取资产设备状态记录详细信息
     */
    @ApiOperation("获取资产设备状态记录详细信息")
    @PreAuthorize("@ss.hasPermi('asset:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbAssetStatusRecordService.selectTbAssetStatusRecordById(id));
    }

    /**
     * 新增资产设备状态记录
     */
    @ApiOperation("新增资产设备状态记录")
    @PreAuthorize("@ss.hasPermi('asset:record:add')")
    @Log(title = "资产设备状态记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbAssetStatusRecord tbAssetStatusRecord)
    {
        return toAjax(tbAssetStatusRecordService.insertTbAssetStatusRecord(tbAssetStatusRecord));
    }

    /**
     * 修改资产设备状态记录
     */
    @ApiOperation("修改资产设备状态记录")
    @PreAuthorize("@ss.hasPermi('asset:record:edit')")
    @Log(title = "资产设备状态记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbAssetStatusRecord tbAssetStatusRecord)
    {
        return toAjax(tbAssetStatusRecordService.updateTbAssetStatusRecord(tbAssetStatusRecord));
    }

    /**
     * 删除资产设备状态记录
     */
    @ApiOperation("删除资产设备状态记录")
    @PreAuthorize("@ss.hasPermi('asset:record:remove')")
    @Log(title = "资产设备状态记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbAssetStatusRecordService.deleteTbAssetStatusRecordByIds(ids));
    }
}
