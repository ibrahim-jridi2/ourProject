from django.urls import path
from . import views

urlpatterns = [
    path('feedback', views.getAllFeedback, name="feedback"),
    path('feedback/<int:id>', views.getFeedbackById, name="feedback"),
    path('feedback', views.addFeedback, name="feedback"),
    path('feedback/<int:id>', views.updateFeedback, name="feedback"),
    path('feedback/<int:id>', views.deleteFeedback, name="deleteFeedback"),
    ################################################################
    path('feedback/product/<int:productId>', views.getFeedbackProduct, name="getFeedbackByProduct"),
    path('feedback/activity/<int:activityId>', views.getFeedbackActivity, name="getFeedbackByActivity"),
    path('feedback/campingCenter/<int:campingCenterId>', views.getFeedbackCenter, name="getFeedbackByActivity"),
    path('feedback/rating/<int:feedbackId>/<int:userId>', views.getFeedbackUserRating, name="getFeedbackUserRating"),
    path('feedback/rating/activity/<int:activityId>', views.getFeedbackActivityRating, name="getFeedbackActivityRating"),
    path('feedback/rating/product/<int:productId>', views.getFeedbackProductRating, name="getFeedbackProductRating"),
    path('feedback/rating/campingcenter/<int:comping_centre_id>', views.getFeedbackCenterRating, name="getFeedbackCenterRating"),
    
    
  
    
    
    
    

]
