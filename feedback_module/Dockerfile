FROM python:3.9

WORKDIR D:/esprit/2eme-annee/microservice/feedback_module/

COPY requirements.txt  D:/esprit/2eme-annee/microservice/feedback_module/
RUN pip install --no-cache-dir -r requirements.txt

COPY . D:/esprit/2eme-annee/microservice/feedback_module/

EXPOSE 8000

CMD ["python", "manage.py", "runserver", "0.0.0.0:8000"]
