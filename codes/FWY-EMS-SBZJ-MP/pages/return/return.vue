<template>
	<view class="home">
		<view class="detail-area">
			<layout-row rowTitle="条形码" :rowValue="form.assetBarCode"></layout-row>
			<layout-row rowTitle="资产编号" :rowValue="tbAsset.number"></layout-row>
			<layout-row rowTitle="资产名称" :rowValue="tbAsset.name"></layout-row>
			<layout-row rowTitle="借用人" :rowValue="form.userId"></layout-row>
			<layout-row rowTitle="借用人电话" :rowValue="form.tel"></layout-row>
			<layout-row rowTitle="借用状态" :rowValue="form.isreturn==='1'?'已归还':''"></layout-row>
			<layout-row rowTitle="借用地点" :rowValue="form.borrowPlaceName"></layout-row>
			<layout-row rowTitle="借用时间" :rowValue="form.borrowDate"></layout-row>
			<layout-row rowTitle="归还时间" :rowValue="form.actualReturnDate"></layout-row>
			<layout-row rowTitle="备注" :rowValue="form.remark==''?'无':form.remark"></layout-row>
		</view>
	</view>
</template>

<script>
	import { getReturnDetail } from '@/api/asset.js'
	import LayoutRow from '@/component/LayoutRow.vue'
	export default {
		data() {
			return {
				id:"",
				form:{},
				tbAsset:{}
			};
		},
		methods:{
			init(id){
				const that = this
				getReturnDetail(id).then(res=>{
					if(res){
						that.form = res
						if(that.form.tbAsset){
							that.tbAsset = that.form.tbAsset
						}
					}
					
				})
			}
		},
		onLoad:function(option){
			this.id = option.id
			this.init(this.id)
		},
		components:{
			LayoutRow
		}
	}
</script>

<style lang="scss">
.home{
	padding: 20rpx;
	display: flex;
	flex-direction: column;
	// height: 100vh;
	box-sizing: border-box;
	.detail-area{
		// border: 2rpx solid #000;
		padding: 0 20rpx;
		box-shadow: 0 2rpx 8rpx 0 rgba(0, 0, 0, 0.2);
	}
}
</style>
