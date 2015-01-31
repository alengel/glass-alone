# glass-alone

This repository is a standalone Google Glass application. It is written in Java using the Android GDK. 

It allows the user of the application to take a picture of a product, get it analysed using the Camfind image recognition API. The returned string is then used to retrieve product data from the Brandwatch and Semantics3 API. The data is used to construct 5 HTML Glass cards that is then displayed to the user. 

<b>How to use this repository:</b>
<br/>
Get the repo and install it on Glass, it should work just out of the box. It is missing the Brandwatch & Semantics3 credentials, which you will have to replace. Contact me if you have any questions. 
