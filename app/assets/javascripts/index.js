$(function() {
    $("#getStateButton").click(function(event) {
        jsRoutes.controllers.LifeController.getState().ajax({
            success: function(data) {
                draw(data)
            }
        })
    })
})