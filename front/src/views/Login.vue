<template>
    <div class="bg">
        <div style="width: 350px;height: 400px;background-color:azure; opacity: 0.8; border-radius: 5px;box-shadow: 0 0 10px rgba(0,0,0,0.1);padding: 40px 20px 30px 30px;">
            <el-form ref="formRef" :model="data.form" :rules="data.rules">
                <div style="margin: 20px 0;text-align: center;font-size: 20px;font-weight: bold;">Login</div>
                <el-form-item  prop ="username">
                    <el-input size="large" v-model="data.form.username" autocomplete="off" prefix-icon="user" placeholder="please input username"/>
                </el-form-item>
                <el-form-item  prop ="password">
                    <el-input size="large" show-password v-model="data.form.password" autocomplete="off" prefix-icon="lock" placeholder="please input password"/>
                </el-form-item>
                <el-form-item prop="role">
                    <el-select size='large' style="width: 100%;" v-model="data.form.role">
                        <el-option label="admin" value="ADMIN"></el-option>
                        <el-option label="user" value="USER"></el-option>
                    </el-select>
                </el-form-item>
                <div style="margin-bottom: 20px;">
                    <el-button size="large" style="width: 100%;" type="primary" @click="login">login</el-button>
                </div>
                <div style="text-align: right;">
                    no account? please <a style="color: aqua;" href="/register">register</a>
                </div>
            </el-form>
            
        </div>

    </div>
</template> 

<script setup>
import request from '@/utils/request';
import { ElMessage } from 'element-plus';
import { reactive,ref } from 'vue';
import router from '@/router/index';

const formRef = ref();
const data = reactive({
    form:{role:'USER'},
    rules:{
        username:[
            {required:true,message:'please input username',trigger:'blur'},
            {min:3,message:'least 3',trigger:'blur'}
        ],
        password:[
            {required:true,message:'please input password',trigger:'blur'}
        ]
    }
})

const login = () =>{
    formRef.value.validate((valid)=>{
        if(valid){
            request.post('/login',data.form).then(res=>{
            if(res.code ==='200'){
                //存储用户信息
                localStorage.setItem("code_user",JSON.stringify(res.data||'{}'))
                ElMessage.success('success')
                router.push('/manager')
        } else {
                ElMessage.error(res.msg)
        }
    })
        }
    })
    
}

</script>

<style scoped>
.bg {
    width: 100%;
    height: 100vh;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
    background-image: url("@/assets/images/login.png");
    background-size: cover;
}

</style>