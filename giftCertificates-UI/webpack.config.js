const { resolve } = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const ExtractTextPlugin = require('extract-text-webpack-plugin');

const PATHS = {
  output: resolve(__dirname, 'dist/'),
  app: resolve(__dirname, 'app/app.module.js'),
  nodeModules: resolve(__dirname, 'node_modules'),
  index: resolve(__dirname, '.index.html'),
};

module.exports = {
  entry: {
    app: PATHS.app,
  },
  output: {
    filename: '[name].bundle.js',
    path: PATHS.output,
    publicPath: './',
  },
  resolve: {
    modules: [
      PATHS.nodeModules,
    ],
  },
  performance: {
    maxEntrypointSize: 512000,
    maxAssetSize: 512000,
  },
  devtool: 'source-map',

  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        loader: 'babel-loader',
      },
      {
        test: /\.html$/,
        loader: 'raw-loader',
      },
      {
        test: /\.css$/,
        loader: 'style-loader!css-loader',
      },
      {
        test: /\.scss$/,
        use: ExtractTextPlugin.extract({
          fallback: 'style-loader',
          use: ['css-loader', 'sass-loader'],
        }),
      },
    ],
  },
  plugins: [
    new CleanWebpackPlugin(['dist']),
    new HtmlWebpackPlugin({
      template: 'index.html',
    }),
    new ExtractTextPlugin('style.css'),
  ],
  devServer:
        {
          port: 6050,
          publicPath: 'http://localhost:6050/',
          watchOptions: {
            ignored: /node_modules/,
          },
          https: false,
          disableHostCheck: true,
        },
};
