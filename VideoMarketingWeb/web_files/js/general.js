var App = {
    devMode : true,
    isEmpty : function(control){
        var controlName = control[0].nodeName;
        switch(controlName.toLowerCase()){
            case "input": return this.isInputEmpty(control);
            default: alert("Something went wrong."); this.logAction("ERROR: Unknown control name: "+controlName); 
        }
    },
    isInputEmpty : function(control){
        return $(control).val() === "";
    },
    logIt : function(message, objects){
        if(this.devMode){
            console.log(this.printTimeNow()+" : "+message);
            if(objects !== null){
                console.log("-------------"+this.printTimeNow()+"-------------");
                if(objects.length == 0) console.log(objects);
                else for(var i = 0; i<objects.length; i++){
                   console.log(objects[i]);   
                }           
                console.log("-------------------------------------------------");
            }
        }
    },
    logAction : function(message){
      this.logIt(message, null);  
    },
    logActionWithObjects : function(message, objects){
      this.logIt(message, objects);
    },
    printTimeNow : function(){
        return new Date().toLocaleString();
    },
    ajaxFunction : function(url, data){
        App.logAction("Running ajaxFunction for url: " + url);
        var prefixPath = "../ajax_scripts/";
        if(data === null) data = {};
        console.log(data);
        return $.ajax({
            url: prefixPath+url,
            dataType: "json",
            contentType: "application/json",
            type: "post",
            data: data            
        });
    }
};

var Error = {
    formErrors : null,
    errorArray : new Array(),
    initialize : function(formErrors){
      App.logAction("Initializing Error object.");
      this.formErrors = formErrors;
      App.logActionWithObjects("Added reference of form: #"+$(this.formErrors).attr("id"),[$(this.formErrors)]);
      App.logAction("Returning object itself");
      return this;
    },
    printErrors : function(){
        App.logAction("Printing errors");
        this.clearErrors();
        $(this.formErrors).addClass("alert-danger");
        var html = this.buildErrorString();
        App.logAction("Made string: "+html);
        $(this.formErrors).html(html);
        App.logAction("Printing errors finished.");
    },
    printSuccess : function(message){
        App.logAction("Printing success");
        this.clearErrors();
        $(this.formErrors).addClass("alert-success");
        $(this.formErrors).html(message);
        App.logAction("Printing success finished. Message: "+message);
    },
    clearErrors : function (){
        App.logAction("Clearing errors...");
        $(this.formErrors).removeClass("alert-danger");
        $(this.formErrors).html("");
        App.logAction("Clearing errors finished.");
    }, 
    buildErrorString : function(){
        App.logAction("Building error string...");
        App.logActionWithObjects("Error array:", [this.errorArray]);
        var html = "<h4>Errors:</h4>";
        html += "<ul>";
        for(var i = 0; i < this.errorArray.length; i++){
            var error = this.errorArray[i];
            html += "<li>"+error+"</li>";
        }
        html += "</ul>";
        App.logAction("Building error string finished.");
        App.logAction("Returning error string: "+html);
        return html;
    },
    addMessageToErrors : function(message){
        App.logAction("Adding string to errors...");
        this.errorArray.push(message);
        App.logActionWithObjects("Current state of error array:", [this.errorArray]);
        App.logAction("Adding string to errors finished.");
    },
    emptyErrorArray : function(){
        App.logAction("Emptying error array...");
        this.clearErrors();
        this.errorArray.length = 0;
        App.logActionWithObjects("Current state of error array: ", [this.errorArray]);
        App.logAction("Emptying error array finished.");
    }
};