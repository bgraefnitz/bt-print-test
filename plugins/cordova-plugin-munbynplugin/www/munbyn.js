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
   }
};

module.exports = BTPrinter;