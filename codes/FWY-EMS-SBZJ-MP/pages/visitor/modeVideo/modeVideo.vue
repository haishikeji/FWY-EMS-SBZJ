<template>
	<view class="container">
		<u-list @scrolltolower="scrolltolower" class="notice-ul" height="100%">
			<u-list-item v-for="(video, index) in videoList" :key="video.id">
				<view class="video">
					<video :src="playerURL(video.url)" controls :id="video.id" :data-id="video.id" :title="video.name" @play="videoPlay" :type="video.type"></video>
					<view class="info">{{video.name}}</view>
				</view>
			</u-list-item>
			<u-list-item v-if="textShow">
				<view style="text-align: center;margin-bottom: 30rpx;">
					已加载全部
				</view>
			</u-list-item>
		</u-list>
	</view>
</template>

<script>
	import { demoVideo, playerVideo } from '@/api/visitor.js'
	export default {
		data() {
			return {
				page: 1,
				total:0,
				pageSize:10,
				textShow:false,
				videoList: []
			};
		},
		methods: {
			scrolltolower(){
				if(Math.ceil(this.total/this.pageSize)<this.page){
					return;
				}else if(Math.ceil(this.total/this.pageSize)>=this.page){
					this.getDemoVideo(this.page,this.pageSize)
				}
			},
			getDemoVideo(x, y) {
				const that = this
				uni.showLoading({
					icon:"none",
					title:"记载中"
				})
				try{
					demoVideo(x, y).then(res => {
						uni.hideLoading()
						if (res.data.rows) {
							if(that.page===1){
								that.videoList = res.data.rows
							}else{
								that.videoList = [...that.videoList,...res.data.rows]
							}
							that.total = res.data.total
							that.page = that.page +1
							if(Math.ceil(that.total/that.pageSize)<that.page){
								that.textShow = true
							}else{
								that.textShow = false
							}
						}
					})
				}catch(err){
					uni.hideLoading()
				}
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
		onLoad() {
			this.getDemoVideo(1, this.pageSize)
		}
	}
</script>

<style lang="less">
	.video {
		text-align: center;
		// box-shadow: rgba(0, 0, 0, 0.45) 0px 25px 20px -20px;
		box-shadow: rgba(17, 17, 26, 0.1) 0px 1px 0px;
		margin-bottom: 50rpx;
		
		&:last-child {
			margin-bottom: 20rpx;
		}
		.info {
			padding: 10rpx;
		}
	}
</style>
