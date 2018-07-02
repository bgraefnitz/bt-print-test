var exec = require('cordova/exec');

var BTPrinter = {
   write: function(fnSuccess, fnError, deviceName, message){
      exec(fnSuccess, fnError, "MunbynWrapper", "write", [deviceName,message]);
   },
   list: function(fnSuccess, fnError){
      exec(fnSuccess, fnError, 'MunbynWrapper', 'list', []);
   }
};

module.exports = BTPrinter;