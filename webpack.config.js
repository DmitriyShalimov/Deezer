const path = require('path');
const webpack = require('webpack');
const ROOT = path.resolve(__dirname, 'src/main/webapp');
const SRC = path.resolve(ROOT, 'WEB-INF/assets/js');
const DEST = path.resolve(__dirname, 'src/main/webapp/dist');

module.exports = {
    devtool: 'source-map',
    entry: {
        app: SRC + '/index.jsx',
    },
    mode: 'development',
    output: {
        path: DEST,
        filename: 'bundle.js',
        publicPath: '/dist/'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            },
            {
                test: /\.css$/,
                use: [ 'style-loader', 'css-loader' ]
            }
        ]
    }
};