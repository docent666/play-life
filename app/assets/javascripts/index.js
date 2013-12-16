$(function() {
    // add a click handler to the button
    $("#getMessageButton").click(function(event) {
        // make an ajax get request to get the message
        jsRoutes.controllers.LifeController.getState().ajax({
            success: function(data) {
                //console.log(data)
                draw(data)
                //$(".well").append($("<h1>").text(data))
            }
        })
    })
})