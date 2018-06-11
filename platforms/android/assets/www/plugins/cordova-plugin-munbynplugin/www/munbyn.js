cordova.define("cordova-plugin-munbynplugin.MunbynWrapper", function(require, exports, module) {
var exec = require('cordova/exec');

var BTPrinter = {
   write: function(fnSuccess, fnError){
      exec(fnSuccess, fnError, "MunbynWrapper", "write", ["test"]);
   },
   show: function(fnSuccess, fnError){
      exec(fnSuccess, fnError, 'MunbynWrapper', 'show', []);
   },
   list: function(fnSuccess, fnError){
      exec(fnSuccess, fnError, 'MunbynWrapper', 'list', []);
   },
   connect: function(fnSuccess, fnError, name){
      exec(fnSuccess, fnError, 'MunbynWrapper', 'connect', [name]);
   }
};

module.exports = BTPrinter;
});
