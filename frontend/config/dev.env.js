'use strict';
const merge = require('webpack-merge');
const prodEnv = require('./prod.env');

module.exports = merge(prodEnv, {
    NODE_ENV: '"development"',
    ADMIN_REQUEST_BASE_URL: '"http://localhost:8080/admin"',
    COMMON_REQUEST_BASE_URL: '"http://localhost:8080/portal"'
});
