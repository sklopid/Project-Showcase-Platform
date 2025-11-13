<template>
    <div style="display: flex;">
        <div style="width: 240px;background-color: azure;min-height: calc(100vh - 60px);">
            <div>个人中心</div>
            <el-form ref="formRef" :model="data.user" style="padding: 10px 10px 10px 0;justify-content: center;">
                <el-form-item  prop ="username" label="Account" >
                    <el-input v-model="data.user.username" autocomplete="off" />
                </el-form-item>
                <el-form-item prop ="name" label="Name">
                    <el-input v-model="data.user.name" autocomplete="off" />
                </el-form-item>
                <el-form-item prop ="password"label="Password">
                    <el-input v-model="data.user.password" autocomplete="off" />
                </el-form-item>
                <el-form-item prop="email"label="Email">
                    <el-input v-model="data.user.email" autocomplete="off" />
                </el-form-item>
                <el-form-item prop="phone" label="Phone">
                    <el-input v-model="data.user.phone" autocomplete="off" />
                </el-form-item>
                <el-form-item prop="zone"label="Zones">
                    <el-select v-model="data.user.zone" placeholder="Please select a zone">
                        <el-option label="Zone No.1" value="shanghai" />
                        <el-option label="Zone No.2" value="beijing" />
                    </el-select>
                </el-form-item>
                <el-form-item prop="avatar" label="Avatar">
                    <el-upload
                        action="http://localhost:9999/files/upload"
                        :headers="{token: data.user.token}"
                        list-type="picture"
                        :on-success="handleFileSuccess"
                    >
                        <el-button type="primary">submit</el-button>
                    </el-upload>
                </el-form-item>
            </el-form>
            <div style="text-align: center;">
                <el-button type="primary" style="padding: 20px 40px;" @click="update">save</el-button>
            </div>
        </div>
    </div>
</template>

<script setup>

import request from '@/utils/request';
import { ElMessage } from 'element-plus';
import { reactive } from 'vue';

const data = reactive({
    user: JSON.parse(localStorage.getItem('code_user')||'{}')
})

const handleFileSuccess =(res) =>{
    data.user.avatar = res.data;
}

const emit = defineEmits(['updateUser'])

const update =() =>{ 
    let url
    if(data.user.role === 'ADMIN'){
        url='/admin/update'
    }
    if(data.user.role === 'USER') {
        url = '/user/update'
    }
   request.put(url,data.user).then(res =>{
    if(res.code === '200'){
        ElMessage.success('update success')
        localStorage.setItem("code_user",JSON.stringify(data.user))
        emit('updateUser')
    } 
   }) 
}

</script>