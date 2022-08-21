# Code generator
/usr/local/opt/swagger-codegen@2/bin/swagger-codegen generate -i https://developers.strava.com/swagger/swagger.json -l java -o generated/java

## Request scope
https://www.strava.com/oauth/authorize?client_id=55311&response_type=code&redirect_uri=http://developers.strava.com&approval_prompt=force&scope=read_all,profile:read_all,activity:read_all