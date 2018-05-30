var exec = require('cordova/exec');

var BTPrinter = {
   write: function(fnSuccess, fnError){
      exec(fnSuccess, fnError, "BluetoothService", "write", ["test"]);
   }
};

module.exports = BTPrinter;