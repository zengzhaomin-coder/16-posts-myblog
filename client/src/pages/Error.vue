<!--错误时返回的组件-->
<template>
    <div>
        抱歉，出错了，请返回
        <router-link to="/">首页</router-link>
    </div>
</template>

<script>
    export default {
        name: "Error"
    }
</script>

<style scoped>

</style>