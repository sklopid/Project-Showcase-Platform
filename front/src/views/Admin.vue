<template>
    <div>
        <div class="card" style="margin-bottom: 1px;">
          <el-input clearable @clear="load" style="width: 240px;margin-right: 5px;" v-model="data.name" placeholder="请输入搜索内容" :prefix-icon="Search"></el-input>
          <el-input clearable @clear="load" style="width: 240px;margin-right: 5px;" v-model="data.username" placeholder="请输入账号" :prefix-icon="Search"></el-input>
          <el-button type="primary" @click="load">查询</el-button>
          <el-button type="primary" @click="reset">重置</el-button>
        </div>
        <div class="card" style="margin-bottom: 1px;">
          <el-button type="primary" @click="handleAdd">新增</el-button>
          <el-button type="primary" @click="deleteBatch()">批量删除</el-button>
          <el-button type="primary">批量导出</el-button>
          <el-button type="primary">批量导入</el-button>
        </div>
        <div class="card" style="margin-bottom: 1px;">
          <el-table :data="data.tableData" style="width: 100%" @selection-change="handleSelectChnage" :header-cell-style="{fontWeight:'bold',color:'#333',backgroundColor: 'red'}" row-key="id">
              <el-table-column type="selection" width="55" />
              <el-table-column  label="Avatar" >
                  <template #default="scope">
                    <el-image  v-if="scope.row.avatar" :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]" :preview-teleported="true" style="width:40px; height: 40px;border-radius: 50%;display: block;"/>
                  </template>
              </el-table-column>
              <el-table-column prop="phone" label="Phone" width="180" />
              <el-table-column prop="username" label="Username" width="180" />
              <el-table-column prop="name" label="Name" width="180" />
              <el-table-column prop="email" label="Email" />
              <el-table-column label="Opreation" width="100">
                <template #default="scope">
                  <el-button type="primary" icon="Edit" circle @click="handleEdit(scope.row)"></el-button>
                  <el-button type="danger" icon="Delete" circle @click="del(scope.row.id);"></el-button>
               </template>
              </el-table-column>
          </el-table>
        </div>
        <div class="card" style="margin-bottom: 1px;">
          <el-pagination
            v-model:current-page="data.pageNum"
            v-model:page-size="data.pageSize"
            layout="total, sizes,prev, pager, next"
            :total="data.total"
            :page-sizes="[5,10,20]"
            @size-change="load"
            @current-change="load"
          />
        </div>
        <el-dialog v-model="data.formVisible" title="admin message" width="500" destroy-on-close>
          <el-form ref="formRef" :model="data.form" :rules="data.rules" style="padding: 10px 10px 10px 0;justify-content: center;">
              <el-form-item  prop ="username" label="Account" >
                <el-input v-model="data.form.username" autocomplete="off" />
              </el-form-item>
              <el-form-item prop ="name" label="Name">
                <el-input v-model="data.form.name" autocomplete="off" />
              </el-form-item>
              <el-form-item prop ="password"label="Password">
                <el-input v-model="data.form.password" autocomplete="off" />
              </el-form-item>
              <el-form-item prop="email"label="Email">
                <el-input v-model="data.form.email" autocomplete="off" />
              </el-form-item>
              <el-form-item prop="phone" label="Phone">
                <el-input v-model="data.form.phone" autocomplete="off" />
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
          <template #footer>
            <div class="dialog-footer">
              <el-button @click="data.formVisible = false">Cancel</el-button>
              <el-button type="primary" @click="save">
                Confirm
              </el-button>
            </div>
          </template>
        </el-dialog>
    </div>
</template>

<script setup>
import request from '@/utils/request';
import { Search } from '@element-plus/icons-vue';
import axios from 'axios';
import { ElMessage, ElMessageBox } from 'element-plus';
import { reactive,ref } from 'vue';


const data = reactive({
    user:JSON.parse(localStorage.getItem('code_user')||'{}'),
    name: null,
    username: null,
    pageNum: 1,
    pageSize: 5,
    total: 0,
    tableData: [], 
    formVisible: false,
    form:{},
    rules:{
      username:[
        {required: true,message:'please write',trigger: 'blur'}
      ],
      name:[
        {required: true,message:'please write',trigger: 'blur'}
      ],
      password:[
        {required: true,message:'please write',trigger: 'blur'}
      ],
    },
    rows: []
})

const formRef = ref()

const load = () =>{
  request.get('/admin/selectPage',{
    params: {
      pageNum:data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
      username:data.username
    }
  }).then(res =>{
    if(res.code === '200'){
      data.tableData = res.data.list
      data.total = res.data.total
    } else {
      ElMessage.error(res.msg)
    }
    
  })
}
load()

const reset =() =>{
  data.name = null
  data.username = null 
  load()
}

const handleAdd =() =>{
  data.formVisible=true
  data.form = {}
}

const add =() =>{
  formRef.value.validate((valid) =>{
    if(valid){ //verify pass
      request.post('/admin/add',data.form).then(res=>{
      if(res.code === '200'){
      data.formVisible = false
      ElMessage.success('add successfully')
      load()
      }else {
      ElMessage.error(res.msg)
      }
     })
    }
  })
  
}

const handleEdit =(row) =>{
  data.form = JSON.parse(JSON.stringify(row));
  data.formVisible = true;
}

const update = () =>{
  formRef.value.validate((valid) =>{
    if(valid){ //verify pass
      request.put('/admin/update',data.form).then(res=>{
      if(res.code === '200'){
      data.formVisible = false
      ElMessage.success('update successfully')
      load()
      }else {
      ElMessage.error(res.msg)
      }
     })
    }
  })
}

const save = () =>{
  data.form.id ? update() : add() 
}

const del =(id) => {
  ElMessageBox.confirm('Right?','delete confirm',{type:'warning'}).then(res =>{
    request.delete('/admin/delete/'+id).then(res =>{
      if(res.code === '200') {
        ElMessage.success('delete successfully')
        load()
      }
      else{
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {} )
}

const handleSelectChnage =(rows) =>{
  data.rows = rows
}

const deleteBatch =() =>{
  if(data.rows.length === 0){
    ElMessage.warning('please select')
    return 
  }
  ElMessageBox.confirm('Right?','delete confirm',{type:'warning'}).then(res =>{
    request.delete('/admin/deleteBatch',{data: data.rows}).then(res =>{
      if(res.code === '200') {
        ElMessage.success('delete successfully')
        load()
      }
      else{
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {} )
}

const handleFileSuccess =(res) =>{
    data.form.avatar = res.data;
}

</script>