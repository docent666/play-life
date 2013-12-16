function draw(arrHex) {
 var canvas = document.getElementById("canvas");
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
                var binString = arrHex[i][j].toString(2);
                var stringLength = binString.length;
                for (var b = 0; b<stringLength; b++) {
                    var s = 4 * i * w + 4 * (b+53*j);
                    var padding = 53-binString.length;
                    var x = 0;
                    if (b<padding) x = 0; else x = binString.charAt(b-padding);
                    data[s] = 0x77*x;
                    data[s + 1] = 0xCA*x;
                    data[s + 2] = 0xE6*x;
                    data[s + 3] = 255;
                }
            }
      }

      ctx.putImageData(imgData, 0, 0);
  }
}