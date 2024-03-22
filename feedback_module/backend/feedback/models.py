from django.utils import timezone
from django.db import models

# Create your models here.
class Feedback(models.Model):
    label=models.CharField(max_length=200,null=True, blank=True)
    description=models.CharField(max_length=200,null=True, blank=True)
    rating=models.IntegerField(null=True, blank=True)
    likes=models.IntegerField(null=True, blank=True)
    dislikes=models.IntegerField(null=True, blank=True)
    is_active=models.BooleanField(default=False)
    user_id=models.IntegerField(null=True, blank=True)
    comping_centre_id=models.IntegerField(null=True, blank=True)
    product_id=models.IntegerField(null=True, blank=True)
    activity_id=models.IntegerField(null=True, blank=True)

    # Created and updated timestamps
    created_at = models.DateTimeField(default=timezone.now, editable=False)
    updated_at = models.DateTimeField(default=timezone.now,editable=False)

    def save(self, *args, **kwargs):
        if not self.id:
            self.created_at = timezone.now()
        self.updated_at = timezone.now()
        super(Feedback, self).save(*args, **kwargs)

    class Meta:
        db_table = 'feedback'

    # @ManyToOne(fetch = FetchType.EAGER)
    # private User user;
    # @ManyToOne(fetch = FetchType.EAGER)
    # private CampingCenter campingCenter;
    # @ManyToOne(fetch = FetchType.EAGER)
    # private Product product;
    # @ManyToOne(fetch = FetchType.EAGER)
    # private Activity activity;