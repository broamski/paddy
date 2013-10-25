paddy
===============

A RESTful service which proxies HTTP requests that typically return json and pads them into jsonp. 


#### Requirements

All other dependencies are managed via Maven.

#### Execution

http://localhost:8080/paddy/padify

##### Required Query Paramaters ####
+ url=\<url you would like to pad\>
+ callback=<customed callback name>, default is *callback*

##### Optional Query Paramaters ####
If you are unfortunate enough to be behind an HTTP proxy, I got your back.

+ lame_proxy=\<proxy_host\>:\<proxy_port\>