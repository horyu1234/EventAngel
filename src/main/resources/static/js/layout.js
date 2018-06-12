"use strict";

moment.locale('ko');
Sugar.extend();

window.dataLayer = window.dataLayer || [];

function gtag() {
    dataLayer.push(arguments);
}

gtag('js', new Date());
gtag('config', 'UA-75680482-7');