
from rest_framework.decorators import api_view, permission_classes, authentication_classes

from backend.feedback.functions import get_all_feedback, parse_information
from .models import *
from .serializers import *
from django.core import serializers
import json
from django.http import JsonResponse
from django.core import serializers
from django.db.models import Q
from drf_yasg.utils import swagger_auto_schema
# from rest_framework.permissions import 
from drf_yasg import openapi
@swagger_auto_schema(
    method='GET',
    manual_parameters=[
        openapi.Parameter('page', openapi.IN_QUERY, description="Page number", type=openapi.TYPE_INTEGER),
        openapi.Parameter('sort', openapi.IN_QUERY, description="Field to sort by", type=openapi.TYPE_STRING),
        openapi.Parameter('dir', openapi.IN_QUERY, description="Sort direction", type=openapi.TYPE_STRING),
    ]
)
@api_view(['GET'])
@permission_classes([])
def getAllFeedback(request):
    if (request.method == 'GET'):
        data=request.data
        page_number = request.query_params.get('page')
        property_to_sort = request.query_params.get('sort')
        direction = request.query_params.get('dir')
        # print(request.query_params)
        feedbacks=get_all_feedback(page_number, property_to_sort, direction)
        # feedbacks = Feedback.objects.all()
        feedbacksDict = serializers.serialize("json", feedbacks)
        res = json.loads(feedbacksDict)
        list_feedbacks=[]
        list_feedbacks=parse_information(res,list_feedbacks)
    return JsonResponse({"Feedback:": list_feedbacks})
@swagger_auto_schema(
    method='GET',
    
)
@api_view(['GET'])
@permission_classes([])
def getFeedbackById(request, id):
    if request.method == 'GET':
        try:
            feedback = Feedback.objects.get(id=id)
            feedback_data = serializers.serialize("json", [feedback])
            res = json.loads(feedback_data)
            list_feedbacks=[]
            list_feedbacks=parse_information(res,list_feedbacks)
            return JsonResponse({"Feedback": list_feedbacks})
        except Feedback.DoesNotExist:
            return JsonResponse({"error": "Feedback not found"}, status=404)     
@swagger_auto_schema(
    method='GET',
    
)
@api_view(['GET'])
@permission_classes([])
def getFeedbackProduct(request, productId):
    if request.method == 'GET':
        try:
            feedback = Feedback.objects.filter(product_id=productId)
            feedback_data = serializers.serialize("json", feedback)
            res = json.loads(feedback_data)
            list_feedbacks=[]
            list_feedbacks=parse_information(res,list_feedbacks)
            return JsonResponse({"Feedback": list_feedbacks})
        except Feedback.DoesNotExist:
            return JsonResponse({"error": "Feedback not found"}, status=404)     
@swagger_auto_schema(
    method='GET',
    
)
@api_view(['GET'])
@permission_classes([])
def getFeedbackActivity(request, activityId):
    if request.method == 'GET':
        try:
            feedback = Feedback.objects.filter(activity_id=activityId)
            feedback_data = serializers.serialize("json", feedback)
            res = json.loads(feedback_data)
            list_feedbacks=[]
            list_feedbacks=parse_information(res,list_feedbacks)
            return JsonResponse({"Feedback": list_feedbacks})
        except Feedback.DoesNotExist:
            return JsonResponse({"error": "Feedback not found"}, status=404)  
           
@swagger_auto_schema(
    method='GET',
    
)
@api_view(['GET'])
@permission_classes([])
def getFeedbackCenter(request, campingCenterId):
    if request.method == 'GET':
        try:
            feedback = Feedback.objects.filter(comping_centre_id=campingCenterId)
            feedback_data = serializers.serialize("json", feedback)
            res = json.loads(feedback_data)
            list_feedbacks=[]
            list_feedbacks=parse_information(res,list_feedbacks)
            return JsonResponse({"Feedback": list_feedbacks})
        except Feedback.DoesNotExist:
            return JsonResponse({"error": "Feedback not found"}, status=404)     
#####################################
@swagger_auto_schema(
    method='GET',
    
)
@api_view(['GET'])
@permission_classes([])
def getFeedbackUserRating(request, feedbackId,userId):
    if request.method == 'GET':
        try:
            feedback = Feedback.objects.get(Q(id=feedbackId)&Q(user_id=userId))
            feedback_data = serializers.serialize("json", [feedback])
            res = json.loads(feedback_data)
            sum_rating=sum(res[0]['rating'] )
            
            return JsonResponse({"Rating": sum_rating})
        except Feedback.DoesNotExist:
            return JsonResponse({"error": "Feedback not found"}, status=404)   
          
@swagger_auto_schema(
    method='GET',
    
)
@api_view(['GET'])
@permission_classes([])
def getFeedbackActivityRating(request, activityId):
    if request.method == 'GET':
        try:
            feedback = Feedback.objects.filter(activity_id=activityId)
            feedback_data = serializers.serialize("json", feedback)
            res = json.loads(feedback_data)
            sum_rating=sum(res[i]['rating'] for i in range(len(res)))
            return JsonResponse({"Rating": sum_rating})
        except Feedback.DoesNotExist:
            return JsonResponse({"error": "Feedback not found"}, status=404)     
        
@swagger_auto_schema(
    method='GET',
    
)
@api_view(['GET'])
@permission_classes([])
def getFeedbackProductRating(request, productId):
    if request.method == 'GET':
        try:
            feedback = Feedback.objects.filter(product_id=productId)
            feedback_data = serializers.serialize("json", feedback)
            res = json.loads(feedback_data)
            sum_rating=sum(res[i]['rating'] for i in range(len(res)))
            return JsonResponse({"Rating": sum_rating})
        except Feedback.DoesNotExist:
            return JsonResponse({"error": "Feedback not found"}, status=404)           
        
        
@swagger_auto_schema(
    method='GET',
    
)
@api_view(['GET'])
@permission_classes([])
def getFeedbackCenterRating(request, campingCentreId):
    if request.method == 'GET':
        try:
            feedback = Feedback.objects.filter(comping_centre_id=campingCentreId)
            feedback_data = serializers.serialize("json", feedback)
            res = json.loads(feedback_data)
            sum_rating=sum(res[i]['rating'] for i in range(len(res)))
            return JsonResponse({"Rating": sum_rating})
        except Feedback.DoesNotExist:
            return JsonResponse({"error": "Feedback not found"}, status=404)                      
##############        

@swagger_auto_schema(
    method='POST',
    request_body=FeedbackSerializer,
    responses={200: 'Created', 400: 'Bad Request'},
    operation_summary="API TO ADD Feedback",
    operation_description="This API add Feedback with their caracteristique in database",
)
@api_view(['POST'])
@authentication_classes([])
def addFeedback(request):
    if (request.method == 'POST'):
        data = request.data
        feedbackSerializer = FeedbackSerializer(data=data)
        if feedbackSerializer.is_valid():
                feedbackSerializer.save()
                msg="Feedback saved Successfully!"
                status=201
        else:
            msg=feedbackSerializer.errors
            status=400
       
    return JsonResponse({"msg:": msg},status=status)   
@swagger_auto_schema(
    method='PUT',
    request_body=FeedbackSerializer,
    responses={200: 'Created', 400: 'Bad Request'},
    operation_summary="API TO UPDATE Feedback",
    operation_description="This API update Feedback with their caracteristique in database",
)
@api_view(['PUT'])
@authentication_classes([])
def updateFeedback(request,id):
    if (request.method == 'PUT'):
        data = request.data
        if Feedback.objects.filter(id=id).exists():
            feedback_object=Feedback.objects.get(id=id)
            feedbackSerializer = FeedbackSerializer(feedback_object,data=data)
            if feedbackSerializer.is_valid():
                    feedbackSerializer.save()
                    msg="Feedback saved Successfully!"
                    status=200
            else:
                msg=feedbackSerializer.errors
                status=400
        else:
            msg="Feedback not found!"
            status=404
    return JsonResponse({"msg:": msg},status=status)        
@swagger_auto_schema(
    method='DELETE',
    responses={200: 'Created', 400: 'Bad Request'},
    operation_summary="API DELETE FEEDBACK",
    operation_description="This API delete feedback by id ",
)
@api_view(['DELETE'])
@authentication_classes([])
def deleteFeedback(request,id):
    if (request.method == 'DELETE'):
        if Feedback.objects.filter(id=id).exists():
            feedback_object=Feedback.objects.get(id=id)
            feedback_object.delete()
            msg="Feedback deleted successfully!"
            status=200
        else:
            msg="Feedback not found!"
            status=404
    return JsonResponse({"msg:": msg},status=status)     
