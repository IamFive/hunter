$(function(){
        //新加入会员轮转
    var Interval;
    Interval = setInterval('action()', 2000);
    $("#ajaxcontent").hover(
    function(){clearInterval(Interval);},
    function(){Interval = setInterval('action()',2000);});
});
            
var _recommendCount = parseInt("17");
function action()
{
    var strhtml = $('#ajaxcontent .indexrow').eq(_recommendCount-1).html();
    if(strhtml == null){
        return false;
    }
    $('#ajaxcontent .indexrow').eq(_recommendCount-1).remove();
    $('#ajaxcontent').prepend('<li class="indexrow" style="display:none;" >'+strhtml+'</li>');
    $('#ajaxcontent .indexrow').eq(0).slideDown(500);
}