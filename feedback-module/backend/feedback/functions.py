from httpx import request
from backend.feedback.models import Feedback
from django.core.paginator import Paginator, EmptyPage
import requests
app_user_url="http://localhost:8091/api/users/"
app_activity_url="http://localhost:8091/api/activities/"
app_center_url="http://localhost:8091/api/camping-centers/"
app_product_url="http://localhost:8091/api/products/"
# token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6W3siaWQiOjEsIm5hbWUiOiJST0xFX1NVUEVSX0FETUlOIn1dLCJqdGkiOjEsInN1YiI6ImFkbWluQHRlc3QuY29tIiwiaWF0IjoxNzEwOTY3MDY1LCJleHAiOjE3MTIyNjMwNjV9.js_aTusLlxdpjOYly8hc39P1W2tHtjC-6G_-Ucauzq4"
headers = {
# "Authorization": token,
"Content-Type" : "application/json"
}

def parse_information(res,list_feedbacks):
    # print(res)

    for i in range(0, len(res)):
        res[i].pop('model')
        id = res[i]['pk']
        res[i].pop('pk')
        res[i]['fields']['id'] = id
        # res[i]['fields']['product']=requests.get(app_product_url +str(res[i]['fields']['product_id']),headers=headers ).json() if res[i]['fields']['product_id'] is not None else None
        # res[i]['fields'].pop("product_id")
        # res[i]['fields']['user']=requests.get(app_user_url +str(res[i]['fields']['user_id'] ),headers=headers).json() if res[i]['fields']['user_id'] is not None else None
        # res[i]['fields'].pop("user_id")
        # res[i]['fields']['activity']=requests.get(app_activity_url +str(res[i]['fields']['activity_id']),headers=headers ).json() if res[i]['fields']['activity_id'] is not None else None
        # res[i]['fields'].pop("activity_id") 
        # res[i]['fields']['camping_center']=requests.get(app_center_url +str(res[i]['fields']['comping_centre_id'] ),headers=headers).json() if res[i]['fields']['comping_centre_id'] is not None else None
        # res[i]['fields'].pop("comping_centre_id") 
        
        list_feedbacks.append(res[i]['fields'])
        
    return list_feedbacks
def get_all_feedback(page_number, property, direction):
    feedback_list = Feedback.objects.all()
    if property :
        order_by = property
    else:
        order_by = 'id'

    if direction:
        order_by = order_by

    feedback_list = feedback_list.order_by(order_by)
    # print({"order_by": order_by, "feedback_list": feedback_list})
    paginator = Paginator(feedback_list, 10)
    if page_number is None:
        page_number = 1

    try:
        feedback_page = paginator.page(page_number)
    except EmptyPage:
        feedback_page = paginator.page(paginator.num_pages)

    return feedback_page