const PROXY_CONFIG = [{
  context : [
    "/hello",
    "/people",
    "/goodbye"
  ],
  target : "http://localhost:8080",
  secure : false,
  logLevel : "debug",
  pathRewrite : {
    "^/(.*)": "/${rootArtifactId}/$1"
  }
}];

module.exports = PROXY_CONFIG;