# BQRCodeApp

- QRcode Maker run under Java6 SE environment
  - using older version of zxing library `core-java6-3.2.0.jar` and `javase-java6-3.2.0.jar` needed
- Press "open file.." button to make its content to QR code
- large file may not be converted, so file size is restricted less than 2000 bytes
- base64 encoded
- If you want to decode it, try `echo XXXX== | base64 --decode` in your unix shell.
