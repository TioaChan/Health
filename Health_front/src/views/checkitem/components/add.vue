<template>
    <div>
        <el-form ref="dataAddForm" :model="formData" :rules="rules" label-position="right" label-width="100px">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="项目编码" prop="code">
                        <el-input v-model="formData.code" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="项目名称" prop="name">
                        <el-input v-model="formData.name" />
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="性别">
                        <el-select v-model="formData.sex">
                            <el-option label="不限" value="0"></el-option>
                            <el-option label="男" value="1"></el-option>
                            <el-option label="女" value="2"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="适用年龄">
                        <el-input v-model="formData.age" />
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="类型">
                        <el-select v-model="formData.type">
                            <el-option label="检查" value="1"></el-option>
                            <el-option label="检验" value="2"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="价格">
                        <el-input v-model="formData.price" />
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="项目说明">
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
        <div slot="footer" class="dialog-footer">
            <el-button @click="closeForm()">取消</el-button>
            <el-button type="primary" @click="handleAdd()">确定</el-button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "addForm",
        data() {
            return {
                formData: {}, //表单数据
                rules: { //校验规则
                    code: [{ required: true, message: '项目编码为必填项', trigger: 'blur' }],
                    name: [{ required: true, message: '项目名称为必填项', trigger: 'blur' }]
                }
            }
        },
        methods: {
            closeForm() {
                this.$refs['dataAddForm'].resetFields();
                this.formData = {}
                return this.$emit("close");
            },
            handleAdd() { //添加
                this.$refs['dataAddForm'].validate(result => {
                    if (result) {
                        this.$http.post("http://127.0.0.1:82/checkitem/add.do", this.formData).then(resp => {
                            if (resp.data.flag) {
                                this.$message.success("success")
                            } else {
                                this.$message.error("error")
                            }
                        }).catch(error => {
                            this.$message.error(error)
                        }).finally(() => {
                            this.closeForm()
                        })
                    } else {
                        this.$message.error("表单校验失败")
                        return false;
                    }
                })
            },
        }
    }
</script>

<style>

</style>