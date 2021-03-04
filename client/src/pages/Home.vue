<template>
    <!--    博客的主界面-->
    <div class=".top">
        <!--        导航菜单-->
        <el-header>
            <el-row align="middle" style="height: 60px" type="flex">
                <el-col :span="6">
                    <h2 style="color: #bc8f8f">My name is Blog</h2>
                </el-col>
                <el-col :span="12">
                    <el-menu mode="horizontal">
                        <el-submenu index="1">
                            <template slot="title">分类</template>
                            <el-menu-item index="2-1">哲学</el-menu-item>
                            <el-menu-item index="2-2">游戏</el-menu-item>
                            <el-menu-item index="2-3">学习</el-menu-item>
                            <el-menu-item index="2-4">生活</el-menu-item>
                        </el-submenu>
                        <el-menu-item index="2">标签</el-menu-item>
                        <el-menu-item index="2">动态</el-menu-item>
                        <el-menu-item index="2">其它</el-menu-item>
                        <el-menu-item index="2">生活</el-menu-item>
                        <el-menu-item index="3">关于我们</el-menu-item>
                    </el-menu>
                </el-col>
                <el-col :span="8">
                    <el-button @click="dialogFormVisible = true">发表博客</el-button>
                    <el-button @click="one">登录</el-button>
                    <el-button @click="two">注册</el-button>
                </el-col>
            </el-row>
            <hr>
        </el-header>
        <el-container>

            <!-- 显示所有博客文章 -->
            <el-main class="posts">
                <div v-for="post in posts" :key="post.id">
                    <div class="post">
                        <h3 class="post-title"
                            @click="$router.push('/post/' + post.id)">{{post.title}}</h3>
                        <el-row :gutter="40">
                            <el-col :span="18" class="post-content">
                                {{post.content}}
                            </el-col>
                            <el-col :span="6" class="post-cover">
                                <el-image
                                        :src="'/myblog/' + post.cover"
                                        fit="contain"
                                        class="img-thumbnail"/>
                            </el-col>
                        </el-row>
                        <div class="post-footer">
                            <p class="post-author">作者：{{post.author}}</p>
                            <p class="post-time">发布于： <i>{{post.created}}</i></p>
                            <div>
                                <span class="post-like">点赞 (<i class="abc">{{post.likes}}</i>) </span>
                                <span class="post-comment">评论 (<i>48</i>) </span>
                                <span class="post-share">分享 (<i>26</i>) </span>
                            </div>
                        </div>
                    </div>
                    <hr>
                </div>

                <!-- 发表博客 -->
                <el-dialog title="发表博客" :visible.sync="dialogFormVisible">
                    <el-row :gutter="60">
                        <el-col :span="16">
                            <el-form :model="form">
                                <el-form-item label="标题：" :label-width="formLabelWidth">
                                    <el-input v-model="form.title" autocomplete="off"></el-input>
                                </el-form-item>
                                <el-form-item label="作者：" :label-width="formLabelWidth">
                                    <el-input v-model="form.author" autocomplete="off"></el-input>
                                </el-form-item>
                                <el-form-item label="内容：" :label-width="formLabelWidth">
                                    <el-input
                                            type="textarea"
                                            placeholder="请输入内容"
                                            v-model="form.content"
                                            maxlength="1000"
                                            utocomplete="off"
                                            show-word-limit>
                                    </el-input>
                                </el-form-item>
                                <div class="form-group" style="display: none">
                                    <input type="file" ref="x" @change="filePick">
                                    <input type="hidden" v-model="form.author">
                                </div>
                            </el-form>
                        </el-col>
                        <el-col :span="8">
                            <img @click="$refs.x.click()" class="preview-img" :src="previewSrc">
                            <p> 请点击选择图片 </p>
                        </el-col>
                    </el-row>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="dialogFormVisible = false">取 消</el-button>
                        <el-button type="primary" @click="savePost">发 表</el-button>
                    </div>
                </el-dialog>

                <!-- 分页导航栏 -->
                <p>分页导航栏</p>
                <el-pagination
                        background
                        layout="prev, pager, next"
                        @current-change="handlePageChange"
                        :current-page="page.current"
                        :page-size="page.size"
                        :total="page.total">
                </el-pagination>
            </el-main>

            <!-- 右边的组件 Banner -->
            <el-aside width="310px" style="margin-top: 50px">
                <Banner></Banner>
            </el-aside>
        </el-container>

        <!--        右边的组件 Footer-->
        <el-footer>
            <Footer></Footer>
        </el-footer>

    </div>
</template>

<script>
    // 引入外部组件
    import Footer from '../components/Footer';
    import Banner from '../components/Banner';

    export default {
        name: "Home",   // 主界面
        components: {
            Footer, Banner  // 外部的组件
        },
        data() {
            return {
                page: {     // 分页的设置
                    current: 1, // 第一页
                    size: 8,    // 显示 8 篇博客
                    total: 0
                },
                posts: [],  // 显示所有博客列表
                dialogFormVisible: false,   // 取消按钮的操作
                formLabelWidth: '80px',     // 发表博客的样式
                form: {
                    // author: '易烊千玺',      // 作者默认
                    author: '',              // 作者
                    title: '',              // 标题
                    content: '',            // 内容
                    cover: null             // 图片
                },
                previewSrc: require("../assets/img/1.jpg") // 点击选择图片
            }
        },
        methods: {
            // 显示所有博客
            loadPosts() {
                this.axios({
                    url: "/post/list",  // 显示所有文章（对应 PostListServlet）
                    params: {
                        curr: this.page.current,    // 在 ResultVO 中定义的方法
                        size: this.page.size
                    }
                }).then(r => {
                    this.page.total = r.data.page.total;
                    this.posts = r.data.data;
                    window.scrollTo(0, 0);  // 滚动到顶部
                });
            },
            // 添加博客
            savePost() {
                this.dialogFormVisible = false; // 取消的操作
                let formData = new FormData();  // 声明 forData
                formData.append("author", this.form.author);
                formData.append("title", this.form.title);
                formData.append("content", this.form.content);
                formData.append("cover", this.form.cover);
                this.axios({
                    url: '/post/add',   // 添加文章（对应 PostAddServlet）
                    method: 'post',
                    data: formData
                }).then(r => {
                    this.loadPosts();
                    this.$refs.x.value = '';
                    // this.form = {author: '易烊千玺'};
                    this.form = {};
                    this.$message("恭喜你，添加成功！");
                });
            },
            // 发表博客的内容
            filePick() {
                this.previewSrc = URL.createObjectURL(this.$refs.x.files[0]);
                this.form.cover = this.$refs.x.files[0];
            },
            handlePageChange(v) {
                this.page.current = v;
                this.loadPosts();
            },
            one() {    // 登录的按钮
                // alert(this.$refs.x);
                alert("此功能暂未开放！");
            },
            two() {     // 注册的按钮
                alert("您不能使用此功能？")
            }
        },
        created() {     // 声明 loadPosts
            this.loadPosts();
        }
    }
</script>

<!--<style>-->
<!--    .el-menu&#45;&#45;horizontal {-->
<!--        border-bottom: 0;-->
<!--    }-->
<!--    .el-footer {-->
<!--        padding: 0;-->
<!--    }-->
<!--    .el-container {-->
<!--        margin: 1em;-->
<!--    }-->
<!--    .el-main {-->
<!--        margin: 0 1em;-->
<!--    }-->
<!--    .post {-->
<!--        margin-bottom: 3em;-->
<!--        padding: 1em;-->
<!--    }-->
<!--    .post-title {-->
<!--        cursor: pointer;-->
<!--    }-->
<!--    .post-footer {-->
<!--        display: flex;-->
<!--        justify-content: space-between;-->
<!--        align-items: center;-->
<!--        margin-top: 1em;-->
<!--    }-->
<!--    .img-thumbnail {-->
<!--        height: 150px;-->
<!--        width: 150px;-->
<!--    }-->
<!--    .preview-img {-->
<!--        width: 120px;-->
<!--        height: auto;-->
<!--    }-->
<!--    .el-pagination {-->
<!--        text-align: center;-->
<!--        margin-top: 2em;-->
<!--    }-->
<!--</style>-->

<style scoped>
    .but {
        margin-left: 42%;
        margin-top: 40px;
    }

    .nav-login {
        padding-top: 20px;
    }

    .el-menu--horizontal {
        border-bottom: 0;
    }

    .el-footer {
        padding: 0;
    }

    .el-container {
        margin: 1em;
    }

    .el-main {
        margin: 0 1em;
    }

    .post {
        margin-bottom: 3em;
        padding: 10px;
    }

    .post-title {
        cursor: pointer;
    }

    .post-footer {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 1em;
    }

    .img-thumbnail {
        height: 170px;
        width: 170px;
    }

    .preview-img {
        width: 140px;
        height: auto;
    }
</style>