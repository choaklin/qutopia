<template>
    <div id="app">
        <transition name="fade" mode="out-in">
            <router-view/>
            <!--<span>content</span>-->
        </transition>
    </div>
</template>

<script>
    export default {
        name: 'App'
    }
</script>

<style>
    #app {
        height: 100%;
        overflow: hidden;
        width: 100%;
    }
</style>
