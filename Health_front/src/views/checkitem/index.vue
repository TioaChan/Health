<template>
    <div>
        <div class="content-header">
            <h1>预约管理<small>检查项管理</small></h1>
            <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>预约管理</el-breadcrumb-item>
                <el-breadcrumb-item>检查项管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="app-container">
            <div class="box">
                <div class="filter-container">
                    <el-input placeholder="项目编码/项目名称" v-model="pagination.queryString" style="width: 200px;" class="filter-item"></el-input>
                    <el-button @click="findPage()" class="dalfBut">查询</el-button>
                    <el-button type="primary" class="butT" @click="handleCreate()">新建</el-button>
                </div>
                <el-table size="small" current-row-key="id" :data="dataList" stripe highlight-current-row>
                    <el-table-column type="index" align="center" label="序号"></el-table-column>
                    <el-table-column prop="code" label="项目编码" align="center"></el-table-column>
                    <el-table-column prop="name" label="项目名称" align="center"></el-table-column>
                    <el-table-column label="适用性别" align="center">
                        <template slot-scope="scope">
                            <span>{{ scope.row.sex == '0' ? '不限' : scope.row.sex == '1' ? '男' : '女'}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="age" label="适用年龄" align="center"></el-table-column>
                    <el-table-column prop="remark" label="项目说明" align="center"></el-table-column>
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
                <div id="checkitem-add-form" class="add-form">
                    <el-dialog title="新增检查项" :visible.sync="dialogFormVisible">
                        <addForm @close="closeAddForm"></addForm>
                    </el-dialog>
                </div>
                <!-- 编辑标签弹层 -->
                <div id="checkitem-ediet-form" class="add-form">
                    <el-dialog title="编辑检查项" :visible.sync="dialogFormVisible4Edit">
                        <editForm @close="closeEditForm" v-bind:formData="formData"></editForm>
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
                pagination: { //分页相关模型数据
                    currentPage: 1, //当前页码
                    pageSize: 10, //每页显示的记录数
                    total: 0, //总记录数
                    queryString: null //查询条件
                },
                dataList: [], //当前页要展示的分页列表数据
                dialogFormVisible: false, //增加表单是否可见
                dialogFormVisible4Edit: false, //编辑表单是否可见
                formData: {}, //表单数据
            }
        },
        //钩子函数，VUE对象初始化完成后自动执行
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
                this.$http.post("http://127.0.0.1:82/checkitem/findPage.do", param).then((res) => {
                    //解析Controller响应回的数据，为模型数据赋值
                    this.pagination.total = res.data.total;
                    this.dataList = res.data.rows;
                });
            },
            //切换页码
            handleCurrentChange(currentPage) {
                let param = {
                    currentPage: currentPage,
                    pageSize: this.pagination.pageSize,
                    queryString: this.pagination.queryString
                };
                this.$http.post("http://127.0.0.1:82/checkitem/findPage.do", param).then((res) => {
                    //解析Controller响应回的数据，为模型数据赋值
                    this.pagination.total = res.data.total;
                    this.dataList = res.data.rows;
                    this.pagination.currentPage = currentPage;
                });
            },
            closeAddForm() {
                this.dialogFormVisible = false;
                this.findPage();
            },
            closeEditForm() {
                this.dialogFormVisible4Edit = false;
                this.findPage();
            },
            // 弹出添加窗口
            handleCreate() {
                this.dialogFormVisible = true;
            },
            // 弹出编辑窗口
            handleUpdate(row) {
                this.formData = JSON.parse(JSON.stringify(row));
                this.dialogFormVisible4Edit = true;
            },
            // 删除
            handleDelete(row) {
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$http.get("http://127.0.0.1:82/checkitem/delete.do?itemId=" + row.id).then((resp) => {
                        if (resp.data.flag) {
                            this.$message.success(resp.data.message)
                        } else {
                            this.$message.error(resp.data.message)
                        }
                    }).catch(error => {
                        this.$message.error("删除失败，请稍后重试。")
                    }).finally(() => {
                        this.findPage();
                    });
                }).catch(() => {
                    this.$message.info('已取消删除');
                });
            }
        }
    }
</script>

<style>

</style>