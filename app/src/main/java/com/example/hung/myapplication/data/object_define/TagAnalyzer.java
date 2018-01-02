package com.example.hung.myapplication.data.object_define;

/**
 * Created by Hung on 11/16/2017.
 */
public class TagAnalyzer {
    private String TagString;
    private String[] tags;
    //Tag Ranking: L-Location, O-Other, U-organization unit, T-Time,etc

    public TagAnalyzer(String tagString) {
        TagString = tagString;
    }

    public boolean IsvalidTag(){
        boolean result=true;
        if (TagString.contains(" "))                            //no space
            return false;
        String ErrorChar= "`~!#$%^&*()@_+=><.?/|}]{[";          //no weird characters
        for (int i=0;i<ErrorChar.length();i++){
            if (TagString.indexOf(ErrorChar.charAt(i))>=0)
                return false;
        }
        seperate_comma();
        for(int i=0;i<tags.length;i++) {
            if (tags[i].length()<3)                               //if 2 character, false . ex o-
                return false;
            else {
                char first = tags[i].charAt(0);
                char second = tags[i].charAt(1);
                if (first == 'l' && second == '-')              //start with l-, o-, t-, ou-, true
                    result = true;
                else if (first == 'o' && second == '-')
                    result = true;
                else if (first == 't' && second == '-')
                    result = true;
                else if (first == 'u' && second == '-')
                    result = true;
                else result = false;
            }
            String[] seperated=seperate_Hyphen(tags[i]);
            if(seperated.length>2)                                  //case contain 2-
                return false;
        }
        return result;
    }

    public String[] seperate_comma(){
        tags = TagString.split(",");
        return tags;
    }

    public String[] seperate_Hyphen(String string){
        return string.split("-");
    }
}
