<template>
    <div id="app">
        <!-- <template> -->
        <el-tabs v-model="activeName" type="card">
            <el-tab-pane label="基本信息" name="first">
                <!--基本信息form-->
                <el-form ref="checkGroupAddForm" :model="formData" :rules="rules" label-position="right" label-width="100px">
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
            <el-tab-pane label="检查项信息" name="second">
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
                                    <input :id="c.id" v-model="checkitemIds" type="checkbox" :value="c.id">
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
        <!-- </template> -->
        <div slot="footer" class="dialog-footer">
            <el-button @click="closeForm()">取消</el-button>
            <el-button type="primary" @click="handleAdd()">确定</el-button>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                activeName: 'first', //添加/编辑窗口Tab标签名称
                formData: {}, //表单数据
                // tableData: [], //新增和编辑表单中对应的检查项列表数据
                checkitemIds: [], //新增和编辑表单中检查项对应的复选框，基于双向绑定可以进行回显和数据提交
                rules: { //校验规则
                    code: [{ required: true, message: '项目编码为必填项', trigger: 'blur' }],
                    name: [{ required: true, message: '项目名称为必填项', trigger: 'blur' }],
                    helpCode: [{ required: true, message: '项目名称为必填项', trigger: 'blur' }]
                }
            }
        },
        props: {
            tableData: {
                type: Array
            }, //表单数据
        },
        methods: {
            closeForm()
            {
                this.formData = {};
                this.activeName = "first";
                this.checkitemIds = [];
                this.$refs['checkGroupAddForm'].resetFields();
                return this.$emit("close");
            },
            //添加
            handleAdd() {
                // console.log(this.formData);
                // console.log(this.checkitemIds);

                this.$refs["checkGroupAddForm"].validate(result => {
                    if (result) {
                        this.$http.post("http://127.0.0.1:82/checkgroup/add.do?checkitemIds=" + this.checkitemIds, this.formData).then(resp => {
                            // console.log(resp);
                            if (resp.data.flag) {
                                this.$message.success(resp.data.message);
                            } else {
                                this.$message.error(resp.data.message);
                            }
                        }).catch(error => {
                            this.$message.error("新增检查组时出现错误，请稍后重试");
                        }).finally(() => {
                            this.closeForm();
                        });
                    } else {
                        this.$message.error("valid form failed");
                        return false;
                    }
                });
            },
        }
    }
</script>

<style>

</style>