$(function() {
    $("#getStateButton").click(function(event) {
        startRefresh();
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


function startRefresh() {
    setTimeout(startRefresh,50);
    jsRoutes.controllers.LifeController.getState().ajax({
               success: function(data) {
                 //  console.log(data)
                   draw(data)
               }
    })
}