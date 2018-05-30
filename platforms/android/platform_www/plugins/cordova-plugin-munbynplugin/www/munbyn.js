cordova.define("cordova-plugin-munbynplugin.munbynPlugin", function(require, exports, module) {
var exec = require('cordova/exec');

var BTPrinter = {
   write: function(fnSuccess, fnError){
      exec(fnSuccess, fnError, "BluetoothService", "write", ["test"]);
   }
};

module.exports = BTPrinter;
});
