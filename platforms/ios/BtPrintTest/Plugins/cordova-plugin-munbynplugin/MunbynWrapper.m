#import "MunbynWrapper.h"
#import <Cordova/CDVPlugin.h>
#import "PrinterSDK.h"

@implementation MunbynWrapper

- (void)write:(CDVInvokedUrlCommand*)command
{
    NSArray * arguments = [command arguments];
    NSString *printerName = [arguments objectAtIndex:0];
    NSString *printString = [arguments objectAtIndex:1];
    
    CDVPluginResult* pluginResult = nil;
    NSString* message = [command.arguments objectAtIndex:0];
    __block NSMutableArray* _printerArray;
    if (printString != nil && printerName != nil) {
        [[PrinterSDK defaultPrinterSDK] scanPrintersWithCompletion:^(Printer* printer)
         {
             NSLog(@"win");
             if (nil == _printerArray)
             {
                 _printerArray = [[NSMutableArray alloc] initWithCapacity:1];
             }
             
             [_printerArray addObject:printer];
         }];
        [[PrinterSDK defaultPrinterSDK] scanPrintersWithCompletion:^(Printer *printer)
          {
              if([printer.name isEqualToString:@"BlueTooth Printer"])
              {
                  [[PrinterSDK defaultPrinterSDK] connectBT:printer];
                  [[PrinterSDK defaultPrinterSDK] printText:@"testing iOS"];
              }
          }];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:message];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end
