#import "MunbynWrapper.h"
#import <Cordova/CDVPlugin.h>
#import "PrinterSDK.h"

@implementation MunbynWrapper

- (void)write:(CDVInvokedUrlCommand*)command
{
    NSLog(@"Hello World");
    CDVPluginResult* pluginResult = nil;
    NSString* message = [command.arguments objectAtIndex:0];
    NSMutableArray* _printerArray;
    if (message != nil && [message length] > 0) {
        Printer* printer = nil;
        [[PrinterSDK defaultPrinterSDK] connectBT:printer];
//      [[PrinterSDK defaultPrinterSDK] scanPrintersWithCompletion:^(Printer *printer)
//        {
//            __block NSMutableArray *_printerArray = nil;
//            if(nil == _printerArray)
//            {
//                _printerArray = [[NSMutableArray alloc] initWithCapacity:1];
//            }
//            [_printerArray addObject:printer];
//        }];
//        [[PrinterSDK defaultPrinterSDK] printText:@"testing iOS"];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:message];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end
