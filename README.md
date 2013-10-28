paddy
===============

A RESTful service which proxies HTTP requests that typically return json and pads them into jsonp. 


#### Requirements

All dependencies are managed via Maven.

#### Execution

http://localhost:8080/paddy/padify

##### Required Query Paramaters #####
+ url=\<url you would like to pad\>

##### Optional Query Paramaters #####
+ callback=\<customed callback name\>, default is *callback*

If you are unfortunate enough to be behind an HTTP proxy, I got your back.

+ lame_proxy=\<proxy_host\>:\<proxy_port\>

*Example:* http://localhost:8080/paddy/padify?url=http://sweetapi.bro.com/gimmie-json&callback=holla

*Example:* http://localhost:8080/paddy/padify?url=http://sweetapi.bro.com/gimmie-json&callback=holla&lame_proxy=facist.proxy.local:8000

##### Return: #####

*Before:*
{
    "awesome": "totally",
    "stay": "based"
}

*After:*
callback({
    "awesome": "totally",
    "stay": "based"
})
