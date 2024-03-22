# Use the official Python base image
FROM python:3.10

# Set the working directory
WORKDIR C:/Users/a.bouhrira/OneDrive - NUMERYX/Bureau/microserviceProjet/feedback_module

# Install dependencies
# COPY C:/Users/a.bouhrira/OneDrive - NUMERYX/Bureau/microserviceProjet/feedback_module/requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt


# Copy the application code
COPY . .

# Expose the port the app will run on
EXPOSE 8000