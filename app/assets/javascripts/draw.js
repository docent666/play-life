function padString(binString, targetWidth) {
    var len = targetWidth-binString.length;
    if (len>0)
        return Array(targetWidth-binString.length+1).join("0")+binString;
    return binString;
}

function putPixel(data, coord, value){
if (value == 0) {
    data[coord] =     0x77;
    data[coord + 1] = 0xCA;
    data[coord + 2] = 0xE6;
    data[coord + 3] = 255;
 } else {
    if (data[coord] == 0x77 && data[coord +3] == 255) data[coord +3] = 180;
    else if (data[coord] == 0x77 && data[coord +3] == 180) data[coord +3] = 140;
    else if (data[coord] == 0x77 && data[coord +3] == 140) data[coord +3] = 100;
    else if (data[coord] == 0x77 && data[coord +3] == 100) data[coord +3] = 50;
    else {
        data[coord] =     0;
        data[coord + 1] = 0;
        data[coord + 2] = 0;
        data[coord + 3] = 255;
    }
 }
}

function draw(arrHex) {
 var canvas = document.getElementById("canvas");
 var UNIT_WIDTH=53;
 if (canvas.getContext) {
      var ctx = canvas.getContext("2d");
      var height = arrHex.length;
      var width = arrHex[0].length;

      var h = ctx.canvas.height;
      var w = ctx.canvas.width;

      var imgData = ctx.getImageData(0, 0, w, h);
      var data = imgData.data;

      for (var i=0; i < height; i++) {
            for (var j = 0; j<width;j++) {
                var paddedString = padString(arrHex[i][j].toString(2), UNIT_WIDTH);
                for (var b = 0; b<UNIT_WIDTH; b++) {
                    putPixel(data, 4 * ( i * w + b+UNIT_WIDTH*j), paddedString.charAt(b))
                }
            }
      }
      ctx.putImageData(imgData, 0, 0);
  }
}

