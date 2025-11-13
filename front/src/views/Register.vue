<template>
    <div class="bg">
        <div style="width: 350px;height: 400px;background-color:azure; opacity: 0.8; border-radius: 5px;box-shadow: 0 0 10px rgba(0,0,0,0.1);padding: 40px 20px 30px 30px;">
            <el-form status-icon ref="formRef" :model="data.form" :rules="data.rules">
                <div style="margin: 20px 0;text-align: center;font-size: 20px;font-weight: bold;">Register</div>
                <el-form-item  prop ="username">
                    <el-input size="large" v-model="data.form.username" autocomplete="off" prefix-icon="user" placeholder="please input username"/>
                </el-form-item>
                <el-form-item  prop ="password">
                    <el-input size="large" show-password v-model="data.form.password" autocomplete="off" prefix-icon="lock" placeholder="please input password"/>
                </el-form-item>
                <el-form-item  prop ="confirm">
                    <el-input size="large" show-password v-model="data.form.confirm" autocomplete="off" prefix-icon="lock" placeholder="please input password again"/>
                </el-form-item>
                <div style="margin-bottom: 20px;">
                    <el-button size="large" style="width: 100%;" type="primary" @click="register">register</el-button>
                </div>
                <div style="text-align: right;">
                    have an account? please <a style="color: aqua;" href="/login">login</a>
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

const validatePass = (rule, value, callback) => {

   if (value !== data.form.password) {
    callback(new Error("Two inputs don't match!"))
  } else {
    callback()
  }
}

const data = reactive({
    form:{},
    rules:{
        username:[
            {required:true,message:'please input username',trigger:'blur'},
            {min:3,message:'least 3',trigger:'blur'}
        ],
        password:[
            {required:true,message:'please input password',trigger:'blur'}
        ],
        confirm:[
            {required:true,message:'please input password again',trigger:'blur'},
            {validater:validatePass,message:'twice not same',trigger:'blur'}
        ]
    }
})



const register = () =>{
    formRef.value.validate((valid)=>{
        if(valid){
            request.post('/register',data.form).then(res=>{
                if(res.code ==='200'){
                    ElMessage.success('success')
                    router.push('/login')
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
    background-image: url("@/assets/images/register.png");
    background-size: cover;
}

</style>