<template>
    <div class="card" style="width: 50%;">
        <el-form status-icon ref="formRef" :model="data.user" :rules="data.rules">
            <div style="margin: 20px 0;text-align: center;font-size: 20px;font-weight: bold;">修改密码</div>
            <el-form-item  prop ="password" label="password">
                <el-input size="large" show-password v-model="data.user.password" autocomplete="off" prefix-icon="lock" placeholder="please input old password"/>
            </el-form-item>
            <el-form-item  prop ="newPassword" label="newPassword">
                <el-input size="large" show-password v-model="data.user.newPassword" autocomplete="off" prefix-icon="lock" placeholder="please input new password again"/>
            </el-form-item>
            <el-form-item  prop ="newPassword2" label="confirmPassword">
                <el-input size="large" show-password v-model="data.user.newPassword2" autocomplete="off" prefix-icon="lock" placeholder="please input new password again"/>
            </el-form-item>
            <div style="margin-bottom: 20px;text-align: center;">
                <el-button size="large" style="width: 100%;" type="primary" @click="updatePassword">save</el-button>
            </div>
        </el-form>
    </div>
</template>

<script setup>
import { ElMessage } from 'element-plus';
import router from '@/router/index.js';
import { reactive,ref } from 'vue';
import request from '@/utils/request';

const data = reactive({
    user: JSON.parse(localStorage.getItem('code_user')||'{}'),
    rules:{
        password:[
        {required: true,message:'please write',trigger: 'blur'},
        { min: 5, message: '密码长度不能少于6位', trigger: 'blur' }
      ],
      newPassword:[
        { required: true, message: '请确认密码', trigger: 'blur' },
        { min: 5, message: '密码长度不能少于6位', trigger: 'blur' }
      ],
      newPassword2:[
        { required: true, message: '请确认密码', trigger: 'blur' },
      ],
    }
})

const formRef = ref()

const updatePassword =() =>{
    formRef.value.validate(valid =>{
        if(valid){
            request.post('/updatePassword',data.user).then(res=>{
        if(res.code === '200'){
            ElMessage.success("update password success")
            setInterval(()=>{
                localStorage.removeItem('code_user')
                location.href='/login'
            },500)
        }else{
            ElMessage.error(res.msg)
        }
    })
    }
    })
    
}

</script>