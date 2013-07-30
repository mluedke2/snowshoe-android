Android wrapper for SnowShoe SDK v2
===============================

This repo contains an example project using the [SnowShoe Stamp](http://www.snowshoestamp.com). Download the example project (built for Android Studio) to see how easy it is to drop in [SnowShoe](http://www.snowshoestamp.com) functionality. OAuth 1.0a and all that is built-in.

1. Import The Project into Android Studio
-----------------------------------------

* In general it's recommended to use Android Studio. For tips on how to import projects, there are many resources floating around [including this one from Google](http://developer.android.com/sdk/installing/migrate.html).

* Make sure to insert your `appKey` and `appSecret` that you get from the [SnowShoe site](http://www.snowshoestamp.com) when you register a new application (which is totally free). In the SnowShoeActivity.java file, look for the spots labelled: `YOUR_APP_ID` and `YOUR_APP_SECRET`.

* *If you don't put in a valid key/secret pair, the SnowShoe servers will disapprove of you. Stay on their good side and feed them a key and secret!*

* Run your project on a touchscreen device ([make sure it can accept 5 touches!](https://play.google.com/store/apps/details?id=com.batterypoweredgames.mtvistest&hl=en)), and [set a logcat filter](https://developer.android.com/tools/help/logcat.html) for messages of level Info with tag "result".

* Stamp your phone with your developer's stamp ([need one? they're totally free](https://beta.snowshoestamp.com/get_started/)) and you can hopefully see a result like this pop into logcat:

>{"stamp": {"serial": "DEV-STAMP"}, "receipt": "EdKr/rBblHx8ce+9QPZXlyVYvl4=",
> "secure": false, "created": "2013-06-19 01:08:38.366249"}

* The operative part is that "serial" value. Just write a simple JSON parser to scoop that out and you can be on your way.

* If you use a production (aka not the free developer) stamp that hasn't been tied to your `appKey`, or press your fingers against the phone, or whatever, you'll get a message like the following:

>{"receipt": "iTpXGev3ya2k4UMgO7bc+9o/+mU=", "created": "2013-06-19 01:12:23.481493",
> "secure": false, "error": {"message": "Stamp not found", "code": 32}}

* Feel free to either take out the code from these classes, or simply reference this project as a library project and extend "SnowShoeActivity" in your own app!

Questions?
==========

For hardware or API questions/feedback, address those to [SnowShoe](http://www.snowshoestamp.com) directly. For questions/feedback on this library, email me at mluedke2@gmail.com, visit [my blog](http://www.mattluedke.com), or [follow me on Twitter](https://twitter.com/matt_luedke)
