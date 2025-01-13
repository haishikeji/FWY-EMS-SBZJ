<template>
	<view class="home">
		<view class="detail-area">
			<layout-row rowTitle="资产编号" :rowValue="assetObj.number"></layout-row>
			<layout-row rowTitle="资产名称" :rowValue="assetObj.name"></layout-row>
			<layout-row rowTitle="资产种类" :rowValue="assetObj.kind"></layout-row>
			<layout-row rowTitle="资产分类" :rowValue="assetObj.categoryNumber"></layout-row>
			<layout-row rowTitle="使用部门" :rowValue="assetObj.deptName"></layout-row>
			<layout-row rowTitle="资产原值(元)" :rowValue="assetObj.amount"></layout-row>
			<layout-row rowTitle="数量" :rowValue="assetObj.quantity"></layout-row>
			<layout-row rowTitle="存放地点" :rowValue="assetObj.placeName"></layout-row>
			<layout-row rowTitle="设备状态" :rowValue="assetObj.borrowStatus"></layout-row>
			<layout-row rowTitle="登记人" :rowValue="assetObj.registrant"></layout-row>
			<layout-row rowTitle="取得日期" :rowValue="assetObj.buildDate"></layout-row>
			<layout-row rowTitle="资产用途" :rowValue="assetObj.purpose"></layout-row>
			<layout-row rowTitle="规格型号" :rowValue="assetObj.specificationsModel"></layout-row>
		</view>
		<view v-if="show">
			<view class="borrow-date" v-if="assetObj.borrowStatus?assetObj.borrowStatus=='可借出'?true:false:false">
				<u--form
					labelPosition="left"
					:model="form"
					ref="Form1"
					:labelWidth="110"
					labelAlign="left">
					<u-form-item
						label="借用地点"
						prop="borrowPlaceName"
						borderBottom>
						<text @click="showPlace" style="font-size: 30rpx;width: 100%;height: 100%;">{{form.borrowPlaceName}}</text>
						<u-picker
						:show="placeShow" 
						:columns="placeList"
						title="请选择地点"
						keyName="name"
						@cancel="placeShow = false"
						@confirm="selectPlace"></u-picker>
					</u-form-item>
					<u-form-item
						label="联系电话"
						prop="tel"
						borderBottom>
						<u--input
						v-model="form.tel"
						border="none" placeholder="请输入联系电话">
						</u--input>
					</u-form-item>
					<u-form-item
						label="借用日期"
						prop="borrowDate"
						borderBottom>
						<text @click="showBor" style="font-size: 30rpx;">{{form.borrowDate}}</text>
						<u-datetime-picker
						:show="borrowShow"
						v-model="borrowValue"
						mode="datetime"
						@confirm="changeDate"
						@cancel="borrowShow = false"
						></u-datetime-picker>
					</u-form-item>
					<u-form-item
						label="归还日期"
						prop="returnDate"
						borderBottom>
						<text 
						@click="showRet" 
						style="font-size: 30rpx;">
						{{form.returnDate}}
						</text>
						<u-datetime-picker
						:show="returnShow"
						v-model="returnValue"
						mode="datetime"
						@confirm="returnChange"
						@cancel="returnShow = false"
						></u-datetime-picker>
					</u-form-item>
					<u-form-item
						label="备注"
						prop="remark"
						borderBottom>
						<u--input
						v-model="form.remark"
						border="none"
						placeholder="请输入备注"
						>
						</u--input>
					</u-form-item>
				</u--form>
			</view>
			<view class="borrow-date" v-else-if="assetObj.borrowStatus?assetObj.borrowStatus=='可借出'?false:true:true">
				<u--form
					labelPosition="left"
					:model="assetObj"
					ref="resF"
					:labelWidth="110"
					labelAlign="left">
					<u-form-item
						label="借用人工号"
						prop="borrowUserName"
						borderBottom>
						<text 
						style="font-size: 30rpx;">
						{{assetObj.borrowUserName?assetObj.borrowUserName:""}}
						</text>
					</u-form-item>
					<u-form-item
						label="借用地点"
						prop="borrowPlaceName"
						borderBottom>
						<text 
						style="font-size: 30rpx;">
						{{assetObj.placeName?assetObj.placeName:""}}
						</text>
					</u-form-item>
					<u-form-item
						label="联系电话"
						prop="tel"
						borderBottom>
						<text 
						style="font-size: 30rpx">
						{{assetObj.tel?assetObj.tel:""}}
						</text>
					</u-form-item>
					<u-form-item
						label="借用日期"
						prop="borrowDate"
						borderBottom>
						<text 
						style="font-size: 30rpx;">
						{{assetObj.borrowDate?assetObj.borrowDate:""}}
						</text>
					</u-form-item>
					<u-form-item
						label="归还日期"
						prop="returnDate"
						borderBottom>
						<text 
						style="font-size: 30rpx;">
						{{assetObj.returnDate?assetObj.returnDate:""}}
						</text>
					</u-form-item>
					<u-form-item
						label="备注"
						prop="remark"
						borderBottom>
						<text style="font-size: 30rpx;">
						{{assetObj.remark?assetObj.remark:"无"}}
						</text>
						</u--input>
					</u-form-item>
				</u--form>
			</view>
			<text style="font-size: 24rpx;">归还时间以实际归还时间为准</text>
			<view class="btn-area">
				<u-button text="借用" color="#67C23A" v-if="assetObj.borrowStatus?assetObj.borrowStatus=='可借出'?true:false:false" @click="toBorrowAsset">借用</u-button>
				<u-button text="归还" color="#67C23A" v-else-if="assetObj.borrowStatus?assetObj.borrowStatus=='可借出'?false:true:true" @click="toReturnAsset">归还</u-button>
			</view>
			<view class="repair-btn" @click="toRepeat">
				<i class="iconfont icon-a-shezhi2"></i>
			</view>
		</view>
		<u-toast ref="uToast"></u-toast>
	</view>
</template>

<script>
	import { searchAsset,getAllPlace,borrowAsset,returnAsset } from "@/api/asset.js"
	import cache from "@/utils/storage.js"
	import { dateToString } from "@/common/js/core.js"
	import LayoutRow from '@/component/LayoutRow.vue'
	export default {
		data() {
			return {
				borrowShow:false,
				borrowValue:Number(new Date()),
				returnShow:false,
				returnValue:Number(new Date()),
				assetObj:{},
				form:{
					borrowDate:dateToString(new Date()),
					returnDate:dateToString(new Date()),
					remark:"",
					tel:cache.session.getJSON("phone"),
					borrowPlaceName:"",
					assetBarCode:""
				},
				placeShow: false,
				placeList: [],
				show:false
			};
		},
		components:{
			LayoutRow
		},
		onLoad:function(option){
			this.form.assetBarCode = option.number
			// this.initAssets(option.number)
			this.init()
		},
		onShow() {
			this.initAssets(this.form.assetBarCode,0,"加载中")
		},
		methods:{
			showBor(){
				this.borrowShow = true
			},
			showRet(){
				this.returnShow = true
			},
			handleDate(fDate,rDate){
				let f = new Date(fDate)
				let r = new Date(rDate)
				if (f < r) {
					return true
				} else {
					uni.showToast({
						icon:"error",
						title:"借用日期应小于归还日期"
					})
					return false
				}
			},
			changeDate(val){
				let date = dateToString(val.value);
				this.form.borrowDate = date
				this.borrowShow = false
			},
			returnChange(val){
				let date = dateToString(val.value);
				if(this.handleDate(this.form.borrowDate,date)){
					this.form.returnDate = date
					this.returnShow = false
				}
			},
			init(){
				this.placeList = []
				// uni.showLoading({
				// 	title: "加载中",
				// 	mask: true,
				// });
				getAllPlace().then(res=>{
					if(res.length!==0){
						this.placeList.push(res)
						this.form.borrowPlaceName = res[0].name?res[0].name:"请选择地点"
					}
				})
				this.form.borrowDate = dateToString(new Date(new Date().getTime() + 1000*60*10))
				this.form.returnDate = dateToString(new Date(new Date().getTime() + 1000*60*15))
				this.form.tel = cache.session.getJSON("phone")
			},
			initAssets(data,num,str){
				uni.showLoading({
					title:str,
					icon:'none',
					mask:true
				})
				try{
					searchAsset({barCode: data}).then(res=>{
						if(res.barCode===null){
							uni.hideLoading()
							this.$refs.uToast.show({
								type: 'error',
								icon: false,
								message: "该设备不存在，即将返回上一页",
								duration:1500
							})
							setTimeout(()=>{
								uni.navigateBack({
									delta:1
								})
							},1400)
						}
						if(res.meBorrow || res.borrowStatus ==="可借出"){
							this.show = true;
						}else{
							this.show = false;
						}
						uni.hideLoading()
						let msg = ""
						let iconName = ""
						if(num===1){
							msg = "借用成功！"
							iconName = 'success'
						}else if(num===2){
							msg = "归还成功！"
							iconName = 'success'
						}else if(num===0){
							msg = "加载成功！"
							iconName = 'none'
						}
						uni.showToast({
							title: msg,
							icon:iconName
						});
						this.assetObj = res
					})
				}catch(err){
					uni.hideLoading()
				}
				
			},
			showPlace(){
				this.placeShow = true
			},
			selectPlace(e){
				this.form.borrowPlaceName = e.value[0].name 
				this.placeShow = false
			},
			//点去维修
			toRepeat(){
				// console.log("111233")
				uni.navigateTo({
					url:'/pages/fault/fault-report/fault-report?id='+this.form.assetBarCode
				})
			},
			toBorrowAsset(){
				if(this.form.tel===""){
					uni.showToast({
						icon:"error",
						title:"请输入正确的手机号",
						mask:true
					})
					return;
				}
			  	if(!this.handleDate(this.form.borrowDate,this.form.returnDate)){
					// uni.hideLoading()
					return;
				}
				uni.showLoading({
					title: "正在借用中",
					mask: true,
				});
				let data = {
					"tel": this.form.tel,
					"assetBarCode": this.assetObj.barCode,
					"borrowPlaceName": this.form.borrowPlaceName,
					"remark": this.form.remark
				}
				// console.log(data,1)
				let returnDate = new Date(this.form.returnDate).getTime()
				let borrowDate = new Date(this.form.borrowDate).getTime()
				try{
					borrowAsset(borrowDate,returnDate,data).then(res=>{
						uni.hideLoading()
						// uni.showToast({
						// 	icon:"none",
						// 	title:"借用成功！"
						// })
						// uni.$u.toast('借用成功！')
						this.initAssets(res.assetBarCode,1,"正在借用中")
					})
				}catch(err){
					uni.hideLoading()
				}
				
			},
			toReturnAsset(){
				// uni.showLoading({
				// 	title: "正在归还中",
				// 	mask: true,
				// });
				returnAsset(this.assetObj.barCode).then(res=>{
					if(res.data.code===200 && res.data.msg==="归还成功"){
						uni.showToast({
							icon:"success",
							title:"归还成功！",
							mask:true
						})
						this.initAssets(this.assetObj.barCode,2,"正在归还中")
					}
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
page{
	background-color: #f5f6fa;
}
.home{
	padding: 24rpx 30rpx;
	display: flex;
	flex-direction: column;
	// height: 100vh;
	box-sizing: border-box;
}
.detail-area{
	height: 72%;
	// border: 1px solid $uni-border-black;
	border-radius: 15rpx;
	box-shadow: 0 2rpx 8rpx 0 rgba(0, 0, 0, 0.2);
	padding: 0 20rpx;
	overflow-y: scroll;
}
.borrow-date{
	// border: 1px solid $uni-border-black;
	border-radius: 15rpx;
	box-shadow: 0 2rpx 8rpx 0 rgba(0, 0, 0, 0.2);
	padding: 0 20rpx;
	margin-top: 20rpx;
	/deep/.u-picker__view__column__item{
		text-align: center;
	}
	/deep/.u-form-item__body__right__content__slot{
		height: 100%;
	}
}
.layout-row{
	border-top: 1rpx solid #d6d7d9;
	padding: 20rpx 0 ;
}
.demo-layout {
	height: 25px;
}
.btn-area{
	margin-top: 20rpx;
	padding: 0 250rpx;
}
.repair-btn{
	width: 80rpx;
	height: 80rpx;
	color: rgba(80, 80, 80, 1);
	border-radius: 50%;
	line-height: 150%;
	box-shadow: 0px 1px 1px 0px rgba(0, 0, 0, 0.5);
	display: flex;
	align-items: center;
	justify-content: center;
	text-align: center;
	flex-direction: column;
	font-size: 36rpx;
	position: fixed;
	bottom: 20rpx;
	right: 20rpx;
	background: rgb(69,140,214);
	color: white;
	z-index:100;
	.iconfont{
		font-size: 42rpx;
	}
}
</style>
