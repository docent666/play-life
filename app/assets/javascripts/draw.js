function padString(binString, targetWidth) {
    var padding = targetWidth-binString.length;
    var pad = "";
    for (var i=0; i<padding; i++) {
        pad+="0";
    }
    return pad+binString;
}

function putPixel(data, coord, value){
    data[coord] =     0x77*value;
    data[coord + 1] = 0xCA*value;
    data[coord + 2] = 0xE6*value;
    data[coord + 3] = 255;
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

