var doRefresh = false;

$(function() {
    $("#autoRefreshButton").click(function(event) {
        if (doRefresh == false) {
             doRefresh = true;
             refresh();
        }
        else {
            doRefresh = false;
        }
    })
})

$(function() {
    $("#singleRefreshButton").click(function(event) {
             doRefresh = false;
             refresh();
    })
})

$(function() {
    $("#resetButton").click(function(event) {
        jsRoutes.controllers.LifeController.reset().ajax({
               success: function(data) {
                 draw(data)
               }
            })
    })
})


function refresh() {
    if (doRefresh == true)
        setTimeout(refresh,50);
    jsRoutes.controllers.LifeController.getState().ajax({
               success: function(data) {
                 //  console.log(data)
                   draw(data)
               }
    })
}