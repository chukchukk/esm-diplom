const { createProxyMiddleware } = require('http-proxy-middleware')

const proxy = {
    target: 'http://localhost:8080',
    changeOrigin: true
}

module.exports = function (app) {
    app.use('/request/', createProxyMiddleware(proxy))

    app.use('/api/v1/', createProxyMiddleware(proxy))
}
