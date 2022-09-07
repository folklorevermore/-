module.exports = {
    // webpack-dev-server 相关配置
    devServer: {
      port:8081,
      proxy: {
        "/api": {
          target: "http://localhost:8080",
          ws: true,
          changeOrigin: true,
          pathRewrite: {
            "^/api": "", //通过pathRewrite重写地址，将前缀/api转为/
          },
        },
      },
    },
  };
