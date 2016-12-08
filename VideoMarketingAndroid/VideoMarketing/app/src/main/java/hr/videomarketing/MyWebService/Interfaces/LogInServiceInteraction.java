package hr.videomarketing.MyWebService.Interfaces;

import hr.videomarketing.Models.BaseModel.User;

/**
 * Created by bagy on 30.11.2016..
 */

public interface LogInServiceInteraction{
    void onLogInUnsuccessful(String message);
    void onLogInSuccessful(User user);
}
