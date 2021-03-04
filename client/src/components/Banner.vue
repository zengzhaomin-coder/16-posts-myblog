<template>
    <!--博客右侧的组件-->
    <div class="banner">
        <div class="hot-tags">
            点击排行
            <p><a href="baidu.com">1、优秀的经典作品</a></p>
            <p><a href="baidu.com">2、优秀的好书推荐</a></p>
            <p><a href="baidu.com">3、经典的优选图书</a></p>
            <p><a href="baidu.com">4、经典的书籍大纲</a></p>
        </div>
        <div class="hot-posts">
            热门标签
            <p><a href="baidu.com">1、近期的热门书籍</a></p>
            <p><a href="baidu.com">2、近期的热门内容</a></p>
            <p><a href="baidu.com">3、近期的热门好段</a></p>
            <p><a href="baidu.com">4、近期的热门好句</a></p>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Banner"
    }
</script>

<style scoped>
    .banner {
        padding: 1em;
    }

    .hot-tags, .hot-posts {
        height: 180px;
        padding: 1em;
        margin: 1em 0;
        box-shadow: 0 0 2px #666;
        border-radius: 5px;
    }

    .hot-tags p a {
        text-decoration: none;
    }

    .hot-posts p a {
        text-decoration: none;
    }
</style>
