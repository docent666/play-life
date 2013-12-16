function draw(arrHex) {
 var canvas = document.getElementById("canvas");
 if (canvas.getContext) {
      var ctx = canvas.getContext("2d");
      var height = arrHex.length;
      var width = arrHex[0].length;

      var h = ctx.canvas.height;
      var w = 50;

      var imgData = ctx.getImageData(0, 0, w, h);
      var data = imgData.data;

      for (var i=0; i < height; i++) {
      var binString = arrHex[i].toString(2);
      for (var j = 0; j<w;j++) {

      var s = 4 * i * w + 4 * j;
      var padding = w-binString.length;
      var x = 0;
      if (j<padding) x = 0; else x = binString.charAt(j-padding);
      data[s] = 0x77*x;
      data[s + 1] = 0xCA*x;
      data[s + 2] = 0xE6*x;
      data[s + 3] = 255;
      }
      }

      ctx.putImageData(imgData, 0, 0);
  }
}