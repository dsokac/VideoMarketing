var App = App || {};
var Error = Error || {};
var AdminVideo = {
    titleInput : $("#InputAdminVideo_title"),
    videoUrl : $("#InputAdminVideo_url"),
    addAdminVideoBtn : $("#add_adminVideo"),
    formErrors : $("#videoAdminForm_errors"),
    errorObject : null,
    initialize : function(){
        App.logAction("Initializing AdminVideo object");
        this.initializeEvents();
        this.errorObject = Error.initialize($(this.formErrors));
        App.logAction("Initializing AdminVideo object finished.");
    }, 
    initializeEvents : function(){
        App.logAction("Initializing events");
        var adminVideoObject = this;
        $(this.addAdminVideoBtn).click(function(){
            adminVideoObject.addNewVideo();
        });
        App.logAction("Added on click event to the button for adding new video.");
        App.logAction("Initializing events finished.");
    },
    addNewVideo : function(){
        App.logAction("Adding new video");
        var output = this.areFieldsCorrect();
        if(output){
            App.logAction("Adding new video started");
            var videoTitle = $(this.titleInput).val();
            var videoUrl = $(this.videoUrl).val();
            var adminVideoObject = this;
            $.when(App.ajaxFunction("admin_addNewVideo.ajax.php",{"title": videoTitle, "url": videoUrl}))
                    .done(function(data){
                        if(data.success == 1){
                            adminVideoObject.errorObject.printSuccess("Video is added successfully.");   
                            $(adminVideoObject.titleInput).val("");
                            $(adminVideoObject.videoUrl).val("");
                            App.logAction("Adding new video finished.");
                        } else {
                            adminVideoObject.errorObject.addMessageToErrors("Video is not added successfully.");
                            adminVideoObject.errorObject.printErrors();
                            App.logAction("Adding new video finished.");
                        }
                    })
                    .fail(function(error){
                        alert("Something went wrong");
                        App.logActionWithObjects("ERROR: at adding new video, error message: ", [error]);
                    });
        }
    },
    areFieldsCorrect : function (){
        App.logAction("Checking if all fields are correct");
        var output = true;
        this.errorObject.emptyErrorArray();
        var output1 = this.isFieldCorrect($(this.titleInput));
        var output2 = this.isFieldCorrect($(this.videoUrl));
        if(!output1 || !output2){
            this.errorObject.printErrors();
            output = false;
        }
        App.logAction(output ? "All fields are correct." : "Some or all fields are incorrect.");
        App.logAction("Checking if all fields are correct finished.");
        return output;
    },
    isFieldCorrect : function(control){
        App.logAction("Checking if field is correct");
        var output = true;
        if(App.isEmpty($(control))){
            var message = "The field '"+$(control)[0].placeholder+"' is required and cannot be empty.";
            this.errorObject.addMessageToErrors(message);
            output = false;
        }
        App.logAction(output ? "The field '"+$(control)[0].placeholder+"' is correct" : "The field '"+$(control)[0].placeholder+"' is incorrect");
        App.logAction("Checking if field is correct finished");
        return output;
    }
    
};

AdminVideo.initialize();