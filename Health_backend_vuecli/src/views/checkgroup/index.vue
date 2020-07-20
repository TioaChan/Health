<template>
    <div id="app">
        <div class="content-header">
            <h1>预约管理<small>检查组管理</small></h1>
            <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>预约管理</el-breadcrumb-item>
                <el-breadcrumb-item>检查组管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="app-container">
            <div class="box">
                <div class="filter-container">
                    <el-input placeholder="编码/名称/助记码" v-model="pagination.queryString" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"></el-input>
                    <el-button @click="findPage()" class="dalfBut">查询</el-button>
                    <el-button type="primary" class="butT" @click="handleCreate()">新建</el-button>
                </div>
                <el-table size="small" current-row-key="id" :data="dataList" stripe highlight-current-row>
                    <el-table-column type="index" align="center" label="序号"></el-table-column>
                    <el-table-column prop="code" label="检查组编码" align="center"></el-table-column>
                    <el-table-column prop="name" label="检查组名称" align="center"></el-table-column>
                    <el-table-column label="适用性别" align="center">
                        <template slot-scope="scope">
                            <span>{{ scope.row.sex == '0' ? '不限' : scope.row.sex == '1' ? '男' : '女'}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="helpCode" label="助记码" align="center"></el-table-column>
                    <el-table-column prop="remark" label="说明" align="center"></el-table-column>
                    <el-table-column label="操作" align="center">
                        <template slot-scope="scope">
                            <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
                            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pagination-container">
                    <el-pagination class="pagiantion" @current-change="handleCurrentChange" :current-page="pagination.currentPage" :page-size="pagination.pageSize" layout="total, prev, pager, next, jumper" :total="pagination.total">
                    </el-pagination>
                </div>
                <!-- 新增标签弹层 -->
                <div class="add-form">
                    <el-dialog title="新增检查组" :visible.sync="dialogFormVisible">
                        <addForm @close="closeAddForm" v-bind:tableData="tableData"></addForm>
                    </el-dialog>
                </div>

                <!-- 编辑标签弹层 -->
                <div class="add-form">
                    <el-dialog title="编辑检查组" :visible.sync="dialogFormVisible4Edit">
                        <editForm @close="closeEditForm" v-bind:tableData="tableData" v-bind:formData="formData" v-bind:checkitemIds="checkitemIds"></editForm>
                    </el-dialog>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import addForm from "./components/add.vue";
    import editForm from "./components/edit.vue";
    export default {
        components: {
            addForm,
            editForm
        },
        data() {
            return {
                pagination: { //分页相关属性
                    currentPage: 1,
                    pageSize: 10,
                    total: 0,
                    queryString: null,
                },
                dataList: [], //列表数据
                dialogFormVisible: false, //控制添加窗口显示/隐藏
                dialogFormVisible4Edit: false, //控制编辑窗口显示/隐藏
                checkitemIds: [], //新增和编辑表单中检查项对应的复选框，基于双向绑定可以进行回显和数据提交
                tableData: [], //新增和编辑表单中对应的检查项列表数据
                formData: {}
            }
        },
        created() {
            this.findPage();
        },
        methods: {
            //分页查询
            findPage() {
                var param = {
                    currentPage: this.pagination.currentPage,
                    pageSize: this.pagination.pageSize,
                    queryString: this.pagination.queryString
                };
                this.$http.post("http://127.0.0.1:82/checkgroup/findPage.do", param).then((res) => {
                    //解析Controller响应回的数据，为模型数据赋值
                    this.pagination.total = res.data.total;
                    this.dataList = res.data.rows;
                });
            },
            // 重置表单
            resetForm() {
                this.formData = {};
                this.checkitemIds = [];
            },
            closeAddForm() {
                this.resetForm();
                // this.$refs['checkGroupAddForm'].resetFields();
                this.dialogFormVisible = false;
                this.findPage();
            },
            closeEditForm() {
                this.resetForm();
                // this.$refs['checkGroupEditForm'].resetFields();
                this.dialogFormVisible4Edit = false;
                this.findPage();
            },
            // 弹出添加窗口
            handleCreate() {
                this.$http.get("http://127.0.0.1:82/checkitem/getAll.do").then(resp => {
                    // console.log(resp)
                    if (resp.data.flag) {
                        this.tableData = resp.data.data;
                    }
                }).catch(error => {
                    this.closeForm();
                    this.$message.error("获取检查项出现错误，请稍后重试。")
                })
                this.dialogFormVisible = true;
            },
            // 弹出编辑窗口
            handleUpdate(row) {
                this.formData = JSON.parse(JSON.stringify(row));
                this.$http.get("http://127.0.0.1:82/checkitem/getAll.do").then(resp => {
                    if (resp.data.flag) {
                        this.tableData = resp.data.data;
                        // console.log("1====================");
                        this.$http.get("http://127.0.0.1:82/checkitem/getIdsByCheckGroup.do?id=" + row.id).then(resp => {
                            // console.log("=============");
                            if (resp.data.flag) {
                                this.checkitemIds = resp.data.data;
                            }
                            this.dialogFormVisible4Edit = true;
                        }).catch(error => {
                            this.closeEditForm();
                            this.$message.error("获取关联检查项时出现错误，请稍后重试。")
                            return false;
                        });
                    } else {
                        this.closeEditForm();
                        this.$message.error("获取检查项出现错误，请稍后重试。")
                        return false;
                    }
                }).catch(error => {
                    this.closeEditForm();
                    this.$message.error("出现错误，请稍后重试。")
                    return false;
                });
            },
            //切换页码
            handleCurrentChange(currentPage) {
                // console.log(currentPage);
                let param = {
                    currentPage: currentPage,
                    pageSize: this.pagination.pageSize,
                    queryString: this.pagination.queryString
                };
                this.$http.post("http://127.0.0.1:82/checkgroup/findPage.do", param).then((res) => {
                    //解析Controller响应回的数据，为模型数据赋值
                    this.pagination.currentPage = currentPage;
                    this.pagination.total = res.data.total;
                    this.dataList = res.data.rows;
                }).catch(error => {
                    this.findPage();
                    this.$message.error("获取数据时出现错误，请稍后重试。")
                    return false;
                });
            },
            // 删除
            handleDelete(row) {
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$http.get("http://127.0.0.1:82//checkgroup/delete.do?groupId=" + row.id).then((resp) => {
                        if (resp.data.flag) {
                            this.$message.success(resp.data.message)
                        } else {
                            this.$message.error(resp.data.message)
                        }
                        this.findPage();
                    }).catch(error => {
                        this.$message.error("删除失败，请稍后重试。")
                        this.findPage();
                    });
                }).catch(() => {
                    this.$message.info('已取消删除');
                });
            }
        }
    }
</script>

<style scope>
    .datatable {
        position: relative;
        box-sizing: border-box;
        -webkit-box-flex: 1;
        width: 100%;
        max-width: 100%;
        font-size: 14px;
        color: rgb(96, 98, 102);
        overflow: hidden;
        flex: 1 1 0%;
    }

    .datatable td,
    .datatable th {
        padding: 12px 0;
        min-width: 0;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        text-overflow: ellipsis;
        vertical-align: middle;
        position: relative;
        text-align: left;
    }
</style>