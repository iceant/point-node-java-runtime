;(function(window, document, $){
    function toggleStylesheet( href, onoff ){
        var existingNode=0 //get existing stylesheet node if it already exists:
        for(var i = 0; i < document.styleSheets.length; i++){
            if( document.styleSheets[i].href && document.styleSheets[i].href.indexOf(href)>-1 ) existingNode = document.styleSheets[i].ownerNode
        }
        if(onoff == undefined) onoff = !existingNode //toggle on or off if undefined
        if(onoff){ //TURN ON:
            if(existingNode) return onoff //already exists so cancel now
            var link  = document.createElement('link');
            link.rel  = 'stylesheet';
            link.type = 'text/css';
            link.href = href;
            document.getElementsByTagName('head')[0].appendChild(link);
        }else{ //TURN OFF:
            if(existingNode) existingNode.parentNode.removeChild(existingNode)
        }
        return onoff
    }

    var addCSSRule = function addCSSRule(sheet, selector, rules){
        //Backward searching of the selector matching cssRules
        var index=sheet.cssRules.length-1;
        for(var i=index; i>0; i--){
            var current_style = sheet.cssRules[i];
            if(current_style.selectorText === selector){
                //Append the new rules to the current content of the cssRule;
                rules=current_style.style.cssText + rules;
                if("deleteRule" in sheet) { sheet.deleteRule(i); }
                else if("removeRule" in sheet) { sheet.removeRule(i); }
                index=i;
            }
        }
        if(sheet.insertRule){
            sheet.insertRule(selector + "{" + rules + "}", index);
        }
        else{
            sheet.addRule(selector, rules, index);
        }
        return sheet.cssRules[index].cssText;
    }

    function appendSheet(id, rules){
        var sheet = document.createElement('style')
        sheet.setAttribute("id", id);
        sheet.innerHTML = rules;
        document.body.appendChild(sheet);
    }

    function removeSheet(id){
        var sheetToBeRemoved = document.getElementById(id);
        if(sheetToBeRemoved) {
            var sheetParent = sheetToBeRemoved.parentNode;
            sheetParent.removeChild(sheetToBeRemoved);
        }
    }

    return {
        toggleStylesheet:toggleStylesheet,
        addCSSRule:addCSSRule
    };
}(window, document, jQuery));