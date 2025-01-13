package cn.px.asset.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.px.common.annotation.Excel;
import cn.px.common.core.domain.BaseEntity;

/**
 * 设备视频对象 tb_asset_video
 *
 * @author 品讯科技
 * @date 2024-08
 */
@TableName("tb_asset_video")
public class TbAssetVideo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    @TableId("id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /** 设备条形码 */
    @Excel(name = "设备条形码")
    @TableField("asset_bar_code")
    private String assetBarCode;

    /** 演示视频编号 */
    @Excel(name = "演示视频编号")
    @TableField("demo_video_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long demoVideoId;

    public TbAssetVideo() {
    }

    public TbAssetVideo(String assetBarCode, Long demoVideoId) {
        this.assetBarCode = assetBarCode;
        this.demoVideoId = demoVideoId;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setAssetBarCode(String assetBarCode)
    {
        this.assetBarCode = assetBarCode;
    }

    public String getAssetBarCode()
    {
        return assetBarCode;
    }
    public void setDemoVideoId(Long demoVideoId)
    {
        this.demoVideoId = demoVideoId;
    }

    public Long getDemoVideoId()
    {
        return demoVideoId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("assetBarCode", getAssetBarCode())
            .append("demoVideoId", getDemoVideoId())
            .toString();
    }
}
