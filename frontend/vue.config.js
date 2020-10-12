const TerserPlugin = require('terser-webpack-plugin')
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin

module.exports = {
    devServer: {
      proxy: 'http://localhost:8080'
    },
    configureWebpack: {
      plugins: [
        // new UglifyJsPlugin(),
        new TerserPlugin(),
        new BundleAnalyzerPlugin()
      ]
    }
  }