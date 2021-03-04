// 创建 vue.config.js 文件
module.exports = {
    outputDir: "../server/web",
    publicPath: "/myblog",
    devServer: {
        port: 8080,
        proxy: {
            "/myblog": {
                target: "http://localhost:9999",
                changeOrigin: true
            }
        }
    }
}