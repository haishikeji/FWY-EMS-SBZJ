<template>
	<view class="content">
		<image class="logo" :src="imgSrc('/profile/logo/newLogo.png')"></image>
		<view class="text-area">
			<!-- <text class="title">{{title}}</text> -->
			<view class="text-area-login" @click="toLogin">
				教师访问
			</view>
			<!-- <text class="title">{{title}}</text> -->
			<view class="text-area-visitor" @click="toVisitor">
				学生访问
			</view>
			<view class="text-area-content">
				<text class="text1">**医科大学</text>
				<br/>
				<text class="text2">实验室管理系统</text>
			</view>
		</view>
	</view>
</template>

<script>
	import { getImage } from '@/api/visitor.js'
	export default {
		data() {
			return {
				title: 'Hello'
			}
		},
		onLoad() {

		},
		methods: {
			toLogin(){
				uni.redirectTo({
					url: '/pages/login/login'
				});
			},
			imgSrc(src) {
				return getImage(src)
			},
			toVisitor() {
				uni.redirectTo({
					url: '/pages/visitor/index'
				});
			}
		}
	}
</script>

<style lang="scss">
	page{
		background-color: #f3f3f3;
	}
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 300rpx;
		width: 300rpx;
		position: fixed;
		border-radius: 50%;
		top: 30%;
		left:50%;
		transform: translate(-50%,-50%);
	}

	.text-area {
		display: flex;
		justify-content: center;
		flex-direction: column;
		position: fixed;
		top: 55%;
		text-align: center;
	}
	.text-area-login{
		font-size: $uni-font-size-lg;
		width: 400rpx;
		height: 100rpx;
		line-height: 100rpx;
		text-align: center;
		
		background-color: #fff;
		border: 1px solid #d5d9d9;
		border-radius: 8px;
		box-shadow: rgba(213, 217, 217, .5) 0 2px 5px 0;
		box-sizing: border-box;
		color: #0f1111;
		cursor: pointer;
		display: inline-block;
		padding: 0 10px 0 11px;
		position: relative;
		text-align: center;
		text-decoration: none;
		user-select: none;
		-webkit-user-select: none;
		touch-action: manipulation;
		vertical-align: middle;
		//width: 100px;
		
		&:focus {
			border-color: #008296;
			box-shadow: rgba(213, 217, 217, .5) 0 2px 5px 0;
			outline: 0;
		}
		
		&:hover {
			background-color: #f7fafa;
		}
	}
	.text-area-visitor{	
		margin-top: 40rpx;
		font-size: $uni-font-size-lg;
		width: 400rpx;
		height: 100rpx;
		line-height: 100rpx;
		text-align: center;
		
		background-color: #fff;
		border: 1px solid #d5d9d9;
		border-radius: 8px;
		box-shadow: rgba(213, 217, 217, .5) 0 2px 5px 0;
		box-sizing: border-box;
		color: #0f1111;
		cursor: pointer;
		display: inline-block;
		padding: 0 10px 0 11px;
		position: relative;
		text-align: center;
		text-decoration: none;
		user-select: none;
		-webkit-user-select: none;
		touch-action: manipulation;
		vertical-align: middle;
		//width: 100px;
		
		&:focus {
			border-color: #008296;
			box-shadow: rgba(213, 217, 217, .5) 0 2px 5px 0;
			outline: 0;
		}
		
		&:hover {
			background-color: #f7fafa;
		}
	}
	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
	.text-area-content{
		position: relative;
		bottom: -180rpx;
		.text1{
			color: $uni-text-color;
			font-size: 46rpx;
			line-height: 150%;
		}
		.text2{
			color: rgba(128, 128, 128, 1);
			font-size: 30rpx;
			line-height: 150%;
		}
		.line{
			width: 354rpx;
			height: 2rpx;
			margin: auto;
			color: $uni-text-color;
			background-color: $uni-text-color-grey;
			font-size: 32rpx;
			line-height: 150%;
			text-align: center;
		}
	}
	
</style>
