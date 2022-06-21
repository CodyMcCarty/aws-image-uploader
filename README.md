# AWS image uploader

Uses an amazon S3 bucket to store pics. 
This is a demo app/ me toying around with pics in Spring Boot and AWS.

super basic front end:
![before](./Data/uploading.png)
![after](./Data/uploaded.png)  
Me trying to debug this thing
![code snip](./Data/Debug.PNG)

## Dependencies
[Open JDK 11](https://adoptopenjdk.net/releases.html) or
[JDK 11](https://www.oracle.com/java/technologies/java-se-glance.html)  
[AWS S3](https://aws.amazon.com/pm/serv-s3/?trk=fecf68c9-3874-4ae2-a7ed-72b6d19c8034&sc_channel=ps&sc_campaign=acquisition&sc_medium=ACQ-P|PS-GO|Brand|Desktop|SU|Storage|S3|US|EN|Text&s_kwcid=AL!4422!3!488982706719!e!!g!!aws%20s3&ef_id=CjwKCAjwtcCVBhA0EiwAT1fY7zCCyUnNgm3T3fN9dZcJXLDoLPHRMy2lB0uXK5b9r_orr8LdMNdw7RoCgqsQAvD_BwE:G:s&s_kwcid=AL!4422!3!488982706719!e!!g!!aws%20s3)


## How to use
You'll need these pieces of data [see how to get AWS credentials](#how-to-get-your-own-s3-awsaccesskeyid-and-awssecretkey)
- AWS access key
- AWS secret key
- bucket name
- your S3's region 

1. run with program arguments:  
`--s3.AWSAccessKeyId=AKICRCCIVC4CC6AOOOJZ --s3.AWSSecretKey=BsC7HrsC0ILC8f7x13fh9gCqFCqYBx3iJtWtx60o`  
If you're in intelij, you can add this line by going to Run/Edit configuration/CLI arguments  
These are made up secrets.  Replace with your own.  
2. Bucket name 
Edit bucket/BucketName/PROFILE_IMAGE with your bucket name
3. region
Edit config/AmazonConfig/.withRegion with your region

run Main  
then run the frontend at src/img-app with `npm start`  
[backend](http://localhost:8080/api/v1/user-profile)  
[frontend](http://localhost:3000/)  
Drag and drop images to see them appear.  

### How to get your own S3, AWSAccessKeyId, and AWSSecretKey
1. [make an aws account and log into the console](https://aws.amazon.com/)
2. Get your security credentials under "my account"
3. Click `Create access key` AWS will send you two keys, **access and secret**
4. Now go to Services and find S3
5. click `+ Create bucket` and set the **name and region**
6. the region may say, US East (N. Virginia) us-east-1, but your region is the last part, 'us-east-1' for example.

### Testing
*lol*












