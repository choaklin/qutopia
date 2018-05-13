'use strict'
const chalk = require('chalk')
const glob = require('glob')

// 获取deviceList
const deviceSrcArray = glob.sync('./src/module/*')
let deviceList = []
for(let x in deviceSrcArray){
    deviceList.push(deviceSrcArray[x].split('/')[3])
}

// 检测是否在输入的参数是否在允许的list中
let checkDevice = function () {
    let device = process.env.DEVICE_ENV
    let result = false
    // 检查deviceList是否有重复
    let hash = {}
    let repeatList = []
    for(let l = 0;l < deviceList.length; l++){
        if(hash[deviceList[l]]){
            repeatList.push(deviceList[l])
        }
        hash[deviceList[l]] = true
    }
    if(repeatList.length > 0){
        console.log(chalk.bgRed('deviceList 有重复：'))
        console.log(chalk.bgRed(repeatList.toString()))
        return result
    }
    for(let i in deviceList){
        if(device === deviceList[i]){
            result = device
            break
        }
    }
    if(result === false){
        console.log(chalk.bgRed('参数错误，允许的参数为：'))
        console.log(chalk.bgRed(deviceList.toString()))
    }
    return result
}

exports.deviceList = deviceList
exports.checkDevice = checkDevice
// 其他依赖 'babel-polyfill'
exports.polyfills = []
