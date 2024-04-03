from rest_framework import serializers

from backend.feedback.models import Feedback
class FeedbackSerializer(serializers.ModelSerializer):
    class Meta:
            model = Feedback
            fields = ['label', 'description','rating','likes','dislikes','is_active',
                      'user_id','comping_centre_id','product_id','activity_id'
                      ]
