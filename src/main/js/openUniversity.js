// ==UserScript==
// @name        国开大学 旅游学院
// @namespace   
// @version      v1
// @description  hello world!
// @author       liulingfeng
// @match        http://*/*
// @icon        http://www.ouchn.cn/
// @grant        none
// ==/UserScript==

(function() {
    'use strict';
    var i;
    var href = location.href;
    var isxyzt=true;
    if(href.indexOf("sectionid=")!=-1){
        var nextsection=function(){ //跳转下一专题
            var zt = document.getElementById("list").getElementsByTagName("span")[0].innerText.trim().replace(/ /g,""); //当前专题
            var ssectionid = "sectionid=";//开始区域
            var esectionid = "&";//结束区域
            var sectionid = href.substring(href.indexOf(ssectionid) + 10); //从开始代码向后取
            if(sectionid.indexOf(esectionid)==-1){
                esectionid = "\"";
            }
            sectionid ="sectionid="+sectionid.substring(0, sectionid.indexOf(esectionid));//从结束代码向前取
            var eqid=0;
            var ztlj=document.getElementsByClassName("listinfo")[0];
            for(i = 0; i < ztlj.getElementsByTagName("a").length; i++){
                if(ztlj.getElementsByTagName("a")[i].href.trim().indexOf(sectionid)!=-1){
                    eqid=i+1;
                }
            }
            if(eqid==0){ //sectionid未匹配时进行文本匹配
                for(i = 0; i < ztlj.getElementsByTagName("a").length; i++){
                    if(ztlj.getElementsByTagName("a")[i].innerText.trim().replace(/ /g,"").indexOf(zt)!=-1){
                        ztlj.getElementsByTagName("a")[i+1].click();
                        break;
                    }
                }
            }else{
                ztlj.getElementsByTagName("a")[eqid].click();
            }
        };
        var video = document.getElementsByTagName("video");
        if(video.length>0){ //是视频的话就播放
            isxyzt=false;
            video[0].src="https://vod.300hu.com/4c1f7a6atransbjngwcloud1oss/5d82e52a382171174942019585/v.f30.mp4"; //替换成15秒视频
            video[0].click();
            setInterval(function(){
                if(video[0].ended){
                    for(var i = 0; i < document.getElementsByTagName("li").length; i++){
                        if(document.getElementsByTagName("li")[i].className == "act"){
                            if(i == document.getElementsByTagName("li").length-1){
                                nextsection();
                            }else{
                                document.getElementsByTagName("li")[i+1].click();
                                break;
                            }
                        }
                    }
                }else{
                    if(video[0].paused){
                        console.log("暂停点击播放");
                        video[0].click();
                    }
                }
            },3500);
        }else{ //文本直接翻页
            if(document.getElementsByTagName("li").length<7){
                setInterval(function(){
                    location.reload();
                },3500);
            }
            for(i = 0; i < document.getElementsByTagName("li").length-1; i++){
                if(document.getElementsByTagName("li")[i].className == "act"){
                    isxyzt=false;
                    setInterval(function(){
                        document.getElementsByTagName("li")[i+1].click();
                    },3500);
                    break;
                }
            }
        }
        if(isxyzt){
            nextsection();
        }
    }
})();