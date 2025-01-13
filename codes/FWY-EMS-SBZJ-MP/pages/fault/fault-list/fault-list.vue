<template>
	<view class="home">
		<!-- <view class="search-area" style="margin-bottom: 20rpx;">
			<u-search 
			placeholder="搜索设备" 
			v-model="keyword" 
			shape="square" 
			:clearabled="true"
			:show-action="true" 
			action-text="搜索" 
			:animation="true"
			search-icon-color="#000"
			color="#000"
			placeholder-color="#5c5b5b"
			></u-search>
			
		</view> -->
		<u-empty
			:show="dataList.length===0?true:false"
			mode="list"
			:icon="require('@/static/images/list.png')"
		>
		<!-- http://cdn.uviewui.com/uview/empty/list.png -->
		</u-empty>
		<view v-show="dataList.length===0?false:true">
			<u-cell-group :border="true">
				<u-cell  
				title="设备" 
				value="状态"
				></u-cell>
				<u-cell 
				v-for="item in dataList" 
				:key="item.id" 
				:title="item.assetBarCode"
				:titleStyle="{ color:`${item.processState==='0'?'#ca2121':'#000'}` }"
				:value="item.processState==='0'?'待处理':'已处理'"
				:label="`上报时间：${item.reportDate}`"
				@click="toHandle(item.id)"
				></u-cell>
			</u-cell-group>
		</view>
	</view>
</template>

<script>
	import { getFaultReport } from '@/api/asset.js'
	export default {
		data() {
			return {
				pageNum:1,
				pageSize:10,
				dataList:[],
				keyword:"",
				color:""
			};
		},
		methods:{
			init(){
				getFaultReport().then(res=>{
					this.dataList = res.data.rows
				})
			},
			toHandle(id){
				uni.navigateTo({
					url:'/pages/fault/fault-handle/fault-handle?id='+id
				})
			}
		},
		onShow() {
			this.init()
		}
	}
</script>

<style lang="scss">
.home{
	padding: 20rpx 20rpx 30rpx;
	display: flex;
	flex-direction: column;
	box-sizing: border-box;
}
/deep/ .u-cell-group__wrapper{
	border-left: 1rpx solid #d6d7d9;
	border-right: 1rpx solid #d6d7d9;
}
/deep/ .u-cell__label{
	color: #000!important;
}
</style>
