<template>
    <div>
        <el-tabs v-model="activeName" type="card">
            <el-tab-pane label="基本信息" name="first">
                <el-form ref="setmealEditForm" :model="formData" :rules="rules" label-position="right" label-width="100px">
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="编码" prop="code">
                                <el-input v-model="formData.code" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="名称" prop="name">
                                <el-input v-model="formData.name" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="适用性别">
                                <el-select v-model="formData.sex">
                                    <el-option label="不限" value="0"></el-option>
                                    <el-option label="男" value="1"></el-option>
                                    <el-option label="女" value="2"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="助记码" prop="helpCode">
                                <el-input v-model="formData.helpCode" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="套餐价格">
                                <el-input v-model="formData.price" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="适用年龄">
                                <el-input v-model="formData.age" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="24">
                            <el-form-item label="上传图片">
                                <el-upload class="avatar-uploader" action="/setmeal/uploadSetmealImg.do" :auto-upload="autoUpload" name="imgFile" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                                    <img v-if="imageUrl" :src="imageUrl" class="avatar">
                                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                </el-upload>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="24">
                            <el-form-item label="说明">
                                <el-input v-model="formData.remark" type="textarea"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="24">
                            <el-form-item label="注意事项">
                                <el-input v-model="formData.attention" type="textarea"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </el-tab-pane>
            <el-tab-pane label="检查组信息" name="second">
                <div class="checkScrol">
                    <table class="datatable">
                        <thead>
                            <tr>
                                <th>选择</th>
                                <th>项目编码</th>
                                <th>项目名称</th>
                                <th>项目说明</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="c in tableData">
                                <td>
                                    <input :id="c.id" v-model="checkgroupIds" type="checkbox" :value="c.id">
                                </td>
                                <td><label :for="c.id">{{c.code}}</label></td>
                                <td><label :for="c.id">{{c.name}}</label></td>
                                <td><label :for="c.id">{{c.remark}}</label></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </el-tab-pane>
        </el-tabs>
        <div slot="footer" class="dialog-footer">
            <el-button @click="closeEditForm()">取消</el-button>
            <el-button type="primary" @click="handleEdit()">确定</el-button>
        </div>
    </div>
</template>

<script>
    export default {
        props: {
            currentTableData: {
                type: Array
            }, //表单数据

            currentCheckgroupIds: {
                type: Array
            },
            // currentFormData: {
            //     type: Object
            // }
            formData: {
                type: Object
            }
        },
        data() {
            return {
                // formData: this.currentFormData,
                tableData: this.currentTableData, //添加表单窗口中检查组列表数据
                checkgroupIds: this.currentCheckgroupIds,
                autoUpload: true, //自动上传
                imageUrl: this.currentTableData.imageUrl, //模型数据，用于上传图片完成后图片预览
                activeName: 'first', //添加/编辑窗口Tab标签名称
                rules: { //校验规则
                    code: [{ required: true, message: '项目编码为必填项', trigger: 'blur' }],
                    name: [{ required: true, message: '项目名称为必填项', trigger: 'blur' }],
                    helpCode: [{ required: true, message: '项目名称为必填项', trigger: 'blur' }]
                },
            }
        },
        methods: {
            //文件上传成功后的钩子，response为服务端返回的值，file为当前上传的文件封装成的js对象
            handleAvatarSuccess(response, file) {
                if (response.flag) {
                    this.$message.success(response.data + "上传成功")
                    this.formData.img = response.data;
                } else {
                    this.$message.error("上传失败，请重试")
                }
            },
            //上传图片之前执行
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;
                if (!isJPG) {
                    this.$message.error('上传套餐图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传套餐图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },

            //添加
            handleEdit() {
                this.$http.post("http://127.0.0.1:82/setmeal/add.do?checkgroupIds=" + this.checkgroupIds, this.formData).then((res) => {
                    if (res.data.flag) {
                        //执行成功
                        this.$message({
                            type: 'success',
                            message: res.data.message
                        });
                        //  this.$message.success(res.data.message);
                    } else {
                        //执行失败
                        this.$message.error(res.data.message);
                    }
                }).finally(() => {
                    this.closeAddForm();
                });
            },

            closeEditForm()
            {
                // this.formData = {};
                this.activeName = "first";
                this.checkgroupIds = [];
                this.imageUrl = null;
                this.$refs['setmealEditForm'].resetFields();
                return this.$emit("close");
            },
        }
    }
</script>

<style>

</style>