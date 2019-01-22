const path = require('path');
const webpack = require('webpack');
const ROOT = path.resolve(__dirname, 'src/main/webapp');
const SRC = path.resolve(ROOT, 'WEB-INF/assets/js');
const DEST = path.resolve(__dirname, 'src/main/webapp/dist');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const UglifyJsPlugin = require('uglifyjs-webpack-plugin');

module.exports = {
    mode: 'production',
    entry: {
        app: SRC + '/index.jsx'
    },
    output: {
        path: DEST,
        filename: 'bundle.js',
        publicPath: '/dist/'
    },
    plugins: [
        new CleanWebpackPlugin(['dist']),
        new HtmlWebpackPlugin({
            title: 'Production'
        })
    ],
    optimization: {
        minimizer: [
            new UglifyJsPlugin({
                cache: true,
                parallel: true,
                uglifyOptions: {
                    compress: false,
                    ecma: 6,
                    mangle: true
                },
                sourceMap: true
            })
        ]
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react", {
                            'plugins': ['@babel/plugin-proposal-class-properties']
                        }]
                    }
                }]
            },
            {
                test: /\.(s*)css$/,
                use: ['style-loader', 'css-loader', "sass-loader"]
            }
        ]
    }
};