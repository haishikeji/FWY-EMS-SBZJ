<template>
	<view class="container">
		<view class="assetBox" v-if="assetInfo">
			<view class="imageBox">
				<image class="image" :src="imgSrc(assetInfo.img)" mode="aspectFit"></image>
			</view>
			<view class="infoBox">
				<view class="name">{{assetInfo.name}}</view>
				<scroll-view class="content" scroll-y="true">
					<view class="introduce">
						简介：<br/><text selectable="true">{{assetInfo.introduce}}</text>
					</view>
				</scroll-view>
				
				<!-- <view class="name2">设备详情</view>
				<scroll-view class="content1" scroll-y="true">
					<view v-if="assetInfo.specificationsModel">规格：<text selectable="true">{{assetInfo.specificationsModel}}</text></view>
					<view v-if="assetInfo.purpose">用途：<text selectable="true">{{assetInfo.purpose}}</text></view>
					<view v-if="assetInfo.kind">种类：<text selectable="true">{{assetInfo.kind}}</text></view>
					<view v-if="assetInfo.placeName">位置：<text selectable="true">{{assetInfo.placeName}}</text></view>
					<view v-if="assetInfo.amount">原值：<text selectable="true">{{assetInfo.amount}}</text></view>
					<view v-if="assetInfo.brand">品牌：<text selectable="true">{{assetInfo.brand}}</text></view>
					<view v-if="assetInfo.manufacturer">厂商：<text selectable="true">{{assetInfo.manufacturer}}</text></view>
					<view>编号：<text selectable="true">{{assetInfo.number}}</text></view>
					<view>条形码：<text selectable="true">{{assetInfo.barCode}}</text></view>
				</scroll-view> -->
			</view>
		</view>
		<view class="videoBox" v-if="videoList">
			<view class="title">演示视频</view>
			<view class="video" v-for="(video, index) in videoList" :key="video.id">
				<video :src="playerURL(video.url)" controls :id="video.id" :data-id="video.id" :title="video.name" @play="videoPlay" :type="video.type"></video>
				<view class="info">{{video.name}}</view>
			</view>
		</view>
	</view>
</template>

<script>
	import { assetDetail, getImage, playerVideo } from '@/api/visitor.js'
	export default {
		name: 'Asset-Detail',
		data() {
			return {
				barCode: null,
				assetInfo: null,
				videoList: [],
				videoOptions: null
			};
		},
		methods: {
			initData() {
				assetDetail(this.barCode).then(res => {
					console.log('详情页数据', res);
					this.assetInfo = res.tbAsset
					this.videoList = res.demoVideos
				})
			},
			imgSrc(src) {
				return getImage(src)
			},
			playerURL(url) {
				return playerVideo(url)
			},
			videoPlay(e) {
				// 获取当前视频id
				let currentId = e.currentTarget.dataset.id;
				// uni.createVideoContext获取视频上下文对象
				this.videoContent = uni.createVideoContext(currentId);
				// 获取json对象并遍历, 停止非当前视频
				let videoList = this.videoList;
				for (let i = 0; i < videoList.length; i++) {
					let temp = videoList[i].id;
					if (temp !== currentId) {
						uni.createVideoContext(temp).pause();
					}
				}
			}
		},
		onLoad(option) {
			this.barCode = option.barCode
			this.initData()
		}
	}
</script>

<style lang="less">
page{
	background-color: #f5f6fa;
}
.container{
	padding: 20rpx 24rpx;
	
	.assetBox {
		background-color: #fff;
		border-radius: 20rpx;
		box-shadow: rgba(0, 0, 0, 0.05) 0px 0px 0px 1px;
		.imageBox{
			border-radius: 20rpx;
			box-shadow: rgba(0, 0, 0, 0.05) 0px 0px 0px 1px, rgb(209, 213, 219) 0px 0px 0px 1px inset;
			.image{
				height: 350rpx;
			}
		}
		.infoBox {
			padding: 30rpx 20rpx;
			.name{
				font-size: 40rpx;
				margin-bottom: 10rpx;
			}
			.content{
				height: 350rpx;
				line-height: 68rpx;
				// width: 100%;
				// overflow: auto;
				.introduce{
					color: #696969;
				}
				
				/* 设置滚动条样式 */
				::-webkit-scrollbar {
					width: 80rpx; /* 滚动条宽度 */
				}

				/* 设置滚动条轨道背景颜色 */
				::-webkit-scrollbar-track {
					background-color: #f1f1f1;
				}
				
				/* 设置滚动条滑块颜色 */
				::-webkit-scrollbar-thumb {
					background-color: #888;
					border-radius: 4rpx; /* 可选，设置滑块的圆角 */
				}
			}
			.name2{
				margin-top: 20rpx;
				font-size: 40rpx;
				margin-bottom: 10rpx;
			}
			.content1{
				color: #696969;
				height: 205rpx;
				line-height: 68rpx;
				width: 100%;
				overflow-y: auto;
			}
		}
	}
	
	.videoBox {
		margin-top: 20rpx;
		background-color: #fff;
		border-radius: 20rpx;
		box-shadow: rgba(0, 0, 0, 0.05) 0px 0px 0px 1px;
		padding: 10rpx 20rpx;
		.title{
			font-size: 40rpx;
			margin-bottom: 20rpx;
		}
		.video {
			text-align: center;
			box-shadow: rgba(0, 0, 0, 0.45) 0px 25px 20px -20px;
			margin-bottom: 50rpx;
			
			&:last-child {
				margin-bottom: 20rpx;
			}
			.info {
				padding: 10rpx;
			}
		}
	}
}

</style>
