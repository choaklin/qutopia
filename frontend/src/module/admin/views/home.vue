<template>
    <div>
        <span>{{msg}}</span>
        <button v-on:click="clickButton">You clicked me {{clickCount}} times.</button>
    </div>
</template>

<script>
    export default {
        data: function () {
            return {
                clickCount: 0,
                msg: 'Welcome to Home Page'
            }
        },
        methods: {
            clickButton: function () {
                this.clickCount++;
            }
        }
    }
</script>


