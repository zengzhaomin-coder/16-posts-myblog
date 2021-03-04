<template>
    <!--    评论列表的界面-->
    <div>
        <!--        评论界面的导航栏-->
        <el-page-header @back="$router.push('/')" :content="post.title"/>
        <el-container>
            <!--            显示博客文章-->
            <el-main>
                <div class="post">
                    <h1 class="post-title"
                        @click="$router.push('/post/' + post.id)">{{post.title}}</h1>
                    <el-row :gutter="40">
                        <el-col :span="18" class="post-content">
                            {{post.content}}
                        </el-col>
                        <el-col :span="6" class="post-cover">
                            <el-image
                                    :src="post.cover ? '/myblog/' + post.cover : ''"
                                    fit="contain"
                                    class="img-thumbnail"/>
                        </el-col>
                    </el-row>
                    <div class="post-footer">
                        <p class="post-author">作者：{{post.author}}</p>
                        <p class="post-time">发布于： <i>{{post.created}}</i></p>
                        <div>
                            <span class="post-like" @click="likePost">
                                <i :class="likeIt"></i>
                                (<i>{{post.likes}}</i>) </span>
                            <span class="post-edit">
                                <i class="el-icon-edit"></i>
                                (<i>18</i>)
                            </span>
                            <span class="post-share">
                                <i class="el-icon-share"></i>
                                (<i>20</i>)
                            </span>
                        </div>
                    </div>
                </div>

                <!--                添加评论-->
                <div class="comment-form">
                    <h3>进行评论</h3>
                    <el-input
                            v-model="comment.author"
                            placeholder="请输入您的名字"
                            autocomplete="off"/>
                    <el-input
                            type="textarea"
                            placeholder="请输入您的评论"
                            v-model="comment.content"
                            maxlength="1000"
                            autocomplete="off"
                            show-word-limit/>
                    <div>
                        <el-button><i class="el-icon-refresh-right"/></el-button>
                        <el-button @click="saveComment"><i class="el-icon-s-promotion"/></el-button>
                    </div>
                </div>

                <!--                显示评论列表-->
                <div class="comment-list" v-infinite-scroll="loadCommentsByPage">
                    <h3>评论列表</h3>
                    <div class="comment" v-for="(comment, index) in comments" :key="comment.id">
                        <el-row>
                            <el-col :span="2">
                                <el-image
                                        :src="require('../assets/img/17.jpg')"
                                        fit="contain"
                                        class="img-head"/>
                            </el-col>
                            <el-col :span="22" class="comment-head">
                                <div>第 {{index + 1}} 楼</div>
                                <div>{{comment.author}}</div>
                                <div>{{comment.created}}</div>
                            </el-col>
                        </el-row>
                        <p>
                            {{comment.content}}
                        </p>
                        <footer>
                            回复 点赞 <span @click="deleteComment(comment.id)">删除</span>
                        </footer>
                    </div>
                </div>
            </el-main>

            <!--            外部的组件-->
            <el-aside>
                <Banner></Banner>
            </el-aside>
        </el-container>

        <!--        外部的组件-->
        <el-footer>
            <Footer></Footer>
        </el-footer>
    </div>
</template>

<script>
    import Footer from '../components/Footer';
    import Banner from '../components/Banner';

    export default {
        name: "Post",   // 声明方法
        components: {
            Footer, Banner  // 外部的组件
        },
        data() {
            return {
                page: {     // 评论栏分页显示的功能
                    current: 1,     // 显示为 1
                    size: 3         // 长度为 3
                },
                post: {
                    id: null        // 编号不显示，为 null
                },
                comment: {
                    author: "",
                    content: ""
                },
                comments: [],
                likeIt: 'el-icon-star-off'
            }
        },
        methods: {
            // 显示所有评论
            loadPost(id) {
                this.$axios({
                    url: "/post?id=" + id
                }).then(r => {
                    this.post = r.data.data;
                });
            },
            // 查看评论列表
            loadCommentsByPage() {
                this.$message("loading " + this.page.current + " page");
                this.$axios({
                    url: "/comments",
                    params: {
                        curr: this.page.current,
                        size: this.page.size,
                        postid: this.post.id
                    }
                }).then(r => {
                    this.page.current++;
                    this.comments = this.comments.concat(r.data.data);
                });
            },
            reloadComments() {
                this.page.current = 1;
                this.comments = [];
                this.loadCommentsByPage();
            },
            // 点赞功能
            likePost() {
                if (this.likeIt === 'el-icon-star-off') {
                    this.$axios({
                        url: "/post/like?id=" + this.post.id
                    }).then(_ => {
                        this.likeIt = 'el-icon-star-on';
                        this.loadPost(this.post.id);
                    });
                } else {
                    this.$message("不能重复点赞");
                }
            },
            // 添加评论
            saveComment() {
                if (!this.comment.author || !this.comment.content) {
                    this.$alert("内容不能为空！");
                    return;
                }
                let data = new URLSearchParams();
                data.append("postid", this.post.id);
                data.append("author", this.comment.author);
                data.append("content", this.comment.content);
                this.$axios({
                    method: "post",
                    url: "/comment/add",
                    data: data
                }).then(r => {
                    this.$message("评论发表成功！");
                    this.reloadComments();
                    this.comment.author = "";
                    this.comment.content = "";
                });
            },
            // 删除评论
            deleteComment(id) {
                this.$confirm("确定要删除?")
                    .then(() => {
                        this.$axios({
                            url: '/comment/del?id=' + id
                        }).then(r => {
                            this.$message("删除成功!");
                            this.reloadComments();
                        });
                    });
            }
        },
        created() {     // 声明方法
            this.post.id = this.$route.params["id"];
            this.loadPost(this.post.id);
        }
    }
</script>

<style scoped>
    i {
        font-style: normal;
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

    .comment-list {
        overflow-y: auto;
        height: 300px;
    }

    .comment {
        margin-bottom: 2em;
    }

    .post-footer {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-right: 2em;
    }

    .post-like {
        cursor: pointer;
        margin-right: 8px;
    }

    .comment-head {
        display: flex;
    }

    .comment-head div {
        margin-left: 1em;
    }

    .el-input, .el-textarea {
        margin-bottom: 1em;
    }

    .comment-form, .comment-list {
        margin-top: 2em;
    }

    .img-thumbnail {
        height: 150px;
        width: 150px;
    }

    ::-webkit-scrollbar {
        width: 5px;
    }
</style>

<style>
    .el-page-header {
        background: black;
        padding: 1em;
    }

    .el-page-header > div {
        color: white;
    }
</style>