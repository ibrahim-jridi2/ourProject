# Generated by Django 4.1.5 on 2024-03-20 13:15

from django.db import migrations, models
import django.utils.timezone


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Feedback',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('label', models.CharField(blank=True, max_length=200, null=True)),
                ('description', models.CharField(blank=True, max_length=200, null=True)),
                ('rating', models.IntegerField(blank=True, null=True)),
                ('likes', models.IntegerField(blank=True, null=True)),
                ('dislikes', models.IntegerField(blank=True, null=True)),
                ('is_active', models.BooleanField(default=False)),
                ('user_id', models.IntegerField()),
                ('comping_centre_id', models.IntegerField()),
                ('product_id', models.IntegerField()),
                ('activity_id', models.IntegerField()),
                ('created_at', models.DateTimeField(default=django.utils.timezone.now, editable=False)),
                ('updated_at', models.DateTimeField(default=django.utils.timezone.now, editable=False)),
            ],
            options={
                'db_table': 'feedback',
            },
        ),
    ]
