<template>
    <div>
        <!--头部区域-->
        <div style="height: 60px;background-color:beige;display: flex;border-bottom: 1px solid bisque;">
            <div style="display: flex;align-items: center;">
                <img style="width: 40px;height: 40px;border-radius: 50%;padding-left: 1px;"src="@/assets/images/logo.png"></img>
                <span style="font-size: medium;font-weight:bold ;color: #689;margin-left: 5px;">展示</span>
            </div>
            <div style="flex: 1 ;display: flex;align-items: center;">
                <span style ="margin-right: 5px;margin-left: 5px; cursor: pointer;" @click="router.push('/manager/home')">主页</span> / <span style="margin-left: 5px; cursor: pointer;">{{ router.currentRoute.value.meta.name}}</span>
            </div>
                <div style="width: fit-content;display: flex;align-items: center;padding-right: 20px;">
                     <el-dropdown>
                            <div style="display: flex;align-items: center;">
                                <img v-if="data.user?.avatar" style="width: 40px;height: 40px;border-radius: 50%;" :src="data.user?.avatar"/>
                                <img v-else style="width: 40px;height: 40px;border-radius: 50%;" src="https://www.keaitupian.cn/cjpic/frombd/1/253/648790068/2555599992.jpg" alt=""></img>
                                <span style="margin-left: 5px;">{{ data.user?.username}}</span>
                            </div> 
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item @click="router.push('/manager/person')">个人信息</el-dropdown-item>
                                <el-dropdown-item>我的项目</el-dropdown-item>
                                <el-dropdown-item>新建项目</el-dropdown-item>
                                <el-dropdown-item @click="router.push('/manager/updatePassword')">修改密码</el-dropdown-item>
                                <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                    
                </div>
            <div></div>
        </div>
        <!-- 头部区域结束-->
        <div style="display: flex;">
            <!--菜单区域开始-->
            <div style="width: 240px;background-color: #3a456b;">
                <el-menu router :default-openeds="['1']"  :default-active="router.currentRoute.value.path" style="min-height: calc(100vh - 60px);" >
                    <el-menu-item index="/manager/home">
                        <el-icon><House /></el-icon>
                        <span>首页</span>
                    </el-menu-item>
                    <el-sub-menu index="1">
                        <template #title>
                            <el-icon><User /></el-icon>
                            <span>manage user</span>
                        </template>
                        <el-menu-item index="/manager/admin">admin message</el-menu-item>
                        <el-menu-item index="/manager/user">user message</el-menu-item>
                    </el-sub-menu>
                </el-menu>
            </div>
            <!--菜单区域结束-->
            <!--数据渲染区域开始-->
            <div style="flex : 1;width: 0;margin: 10px;background-color: #689;">
                <RouterView @updateUser="updateUser"/>
            </div>
            <!--数据渲染区域结束-->

        </div>
        
        <!-- 下方区域开始-->

        <!-- 下方区域结束-->
        <!--<RouterView />-->
    </div>
</template>

<script setup>
import { House ,Location} from '@element-plus/icons-vue';
import router from '@/router/index.js';
import { reactive } from 'vue';

const data = reactive({
    user: JSON.parse(localStorage.getItem('code_user')||'{}')
})

const logout =() =>{
    localStorage.removeItem('code_user')
    location.href='/login'
}

const updateUser =() =>{
    data.user = JSON.parse(localStorage.getItem("code_user"))
}

/**
if(!data.user?.id){
    location.href='/login'
}
*/
</script>

<style>
    .el-menu {
        background-color: burlywood;
        border-color: rgb(135, 222, 164);
        box-shadow: 0 0 8px rgba(0, 0, 0, .12);
    }
    .el-sub-menu__title {
        background-color: burlywood;
        color: aquamarine;
    }
    .el-menu-item {
        color: rgb(130, 80, 255);
        height: 50px;
    }
    .el-menu .is-active {
        background-color: rgb(159, 30, 210);
        color: aliceblue;
    }
    .el-sub-menu__title:hover {
        background-color: burlywood;
    }
    .el-menu--inline .el-menu-item{
        padding-left: 50px !important;
    }
    .el-menu-item:not(.is-active):hover {
        background-color: antiquewhite;
        color: red;
    }
    .el-dropdown {
        cursor: pointer;
        outline: none;
    }
    .el-tooltip.trigger {
        outline: none;
    }
</style>