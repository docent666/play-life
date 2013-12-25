$(function() {
    $("#getStateButton").click(function(event) {
        startRefresh();
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