"""feedback_module URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import include, path
from drf_yasg.views import get_schema_view
from drf_yasg import openapi
from rest_framework import permissions
schema_view = get_schema_view(
    openapi.Info(
        title="My API",
        default_version='v1',
        description="My API description",
        terms_of_service="https://www.example.com/terms/",
        contact=openapi.Contact(email="contact@example.com"),
        license=openapi.License(name="Awesome License"),
    ),
    public=True,
    permission_classes=(permissions.AllowAny,),
)
from backend.feedback import views
urlpatterns = [
    # path('admin/', admin.site.urls),
    # path('',include('backend.feedback.urls')),
    path('getAllFeedback', views.getAllFeedback, name="getAllFeedback"),
    path('getFeedbackById/<int:id>', views.getFeedbackById, name="getFeedbackById"),
    path('addFeedback', views.addFeedback, name="addFeedback"),
    path('updateFeedback/<int:id>', views.updateFeedback, name="updateFeedback"),
    path('deleteFeedback/<int:id>', views.deleteFeedback, name="deleteFeedback"),
    ################################################################
    path('feedback/product/<int:productId>', views.getFeedbackProduct, name="getFeedbackByProduct"),
    path('feedback/activity/<int:activityId>', views.getFeedbackActivity, name="getFeedbackByActivity"),
    path('feedback/campingCenter/<int:campingCenterId>', views.getFeedbackCenter, name="getFeedbackByActivity"),
    path('feedback/rating/<int:feedbackId>/<int:userId>', views.getFeedbackUserRating, name="getFeedbackUserRating"),
    path('feedback/rating/activity/<int:activityId>', views.getFeedbackActivityRating, name="getFeedbackActivityRating"),
    path('feedback/rating/product/<int:productId>', views.getFeedbackProductRating, name="getFeedbackProductRating"),
    path('feedback/rating/campingcenter/<int:comping_centre_id>', views.getFeedbackCenterRating, name="getFeedbackCenterRating"),
    path('swagger/', schema_view.with_ui('swagger', cache_timeout=0), name='schema-swagger-ui'),
]
