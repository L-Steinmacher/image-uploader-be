# image-uploader-be
just an image uploading backend

Introduction
This is a database Scheme with user and images.  This Java REST API application will provide endpoints for clents to read various data sets contained in
 the applications's data.
 
 Using the provided seed data, as successful application will return data based on the given endpoint.
 
 ## Eniroment Variables must be set up to authenticate!
 
 All Four variables must be set up for use of this app and testing
 
     For OAUTH
     OAUTHCLIENTID
     OAUTHCLIENTSECRET
     
     For AWS S3 bucket you must have an AWS account of your own and creat an S3 bucket then generate your own keys.
     ACCESSKEYID
     ACCESSKEYSECRET
     
     
     
     
     
### Make sure you have the right configuration file in your home directory.
  touch .zprofile
### Open the .zprofile file for editing
  open .zprofile
### This opens the .zprofile file in your default text editor. Go to the bottom of the file and add the following lines.
  export OAUTHCLIENTID=client-name  {replace client-name}
  export OAUTHCLIENTSECRET=client-secret {replace client-secret}
  export ACCESSKEYID=AWS-client-id  {replace AWS-client-id for S3 bucket}
  export ACCESSKEYSECRET=AWS-client-secret  {replace AWS-client-secret from S3 bucket}
  
  #### Save the .zprofile file
  #### RESTART YOUR COMPUTER.
 
 
 ## Entities
 ###1. Roles
  We have three types of roles:
    Admin
    Data science
    User

###2. Users
  The backend stores User entity primarily to serve frontend about the role so that they can use it for protected routing on the frontend.
  User is authenticated with Oauth2.
  
###3. Images
  Images will be stored in a S3 bucket on a AWS server
  Has a Userid associated with the Image.
