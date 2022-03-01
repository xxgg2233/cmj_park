module.exports = {
    transpileDependencies: [
        'vuetify'
    ],
    devServer: {
        open: true,
        host: 'localhost',
        port: 8081
    },
    publicPath: process.env.NODE_ENV === 'production'
        ? '/park_client/' : '/',
    outputDir: 'park_client',
}