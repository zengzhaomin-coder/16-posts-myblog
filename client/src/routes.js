import VueRouter from "vue-router";
import Home from "./pages/Home";
import Post from "./pages/Post";
import Error from "./pages/Error";

// 路由规则（创建一个 router.js 文件）
// 返回路由对象
export default new VueRouter({
    routes: [
        {
            path: "/", redirect: "/home"
        },
        {
            path: "/home", component: Home
        },
        {
            path: "/post/:id", component: Post
        },
        {
            path: "*", component: Error
        }
    ],
    mode: 'hash'
});