/**
 * 软件著作权：学科网
 * 系统名称：xy360
 * 创建日期： 2015-01-09
 */
package com.example.utils;

/**
 * @version 1.0
 * @author LiaoGang
 */

public class StringUtil {
	
	/**
	 * 获取当前系统的换行符
	 * @return
	 */
	public static String getLineSeparator(){
		return System.getProperty("line.separator");
	}
	
	/** 
     * Remove occurences of html, defined as any text 
     * between the characters "<" and ">". Optionally  
     * replace HTML tags with a space. 
     * @version 1.0
     * @author
     * @param str 
     * @param addSpace 
     */  
    public static String removeHTML(String str, boolean addSpace) {  
          
        if(str == null) return "";  
        StringBuffer ret = new StringBuffer(str.length());  
        int start = 0;  
        int beginTag = str.indexOf("<");  
        int endTag = 0;  
        if(beginTag == -1) return str;  
          
        while(beginTag >= start) {  
            if(beginTag > 0) {  
                ret.append(str.substring(start, beginTag));  
                  
                // replace each tag with a space (looks better)  
                if(addSpace) ret.append(" ");  
            }  
            endTag = str.indexOf(">", beginTag);  
              
            // if endTag found move "cursor" forward  
            if(endTag > -1) {  
                start = endTag + 1;  
                beginTag = str.indexOf("<", start);  
            }  
              
            // if no endTag found, get rest of str and break  
            else {  
                ret.append(str.substring(beginTag));  
                break;  
            }  
        }  
        // append everything after the last endTag  
        if(endTag >-1 && endTag + 1 < str.length()) {  
            ret.append(str.substring(endTag + 1));  
        }  
        
        //System.out.println(ret.toString());  
          
        return removeBlank(ret.toString().trim());
    }  

    
    /** 
     * Remove occurences of html, defined as any text 
     * between the characters "<" and ">". 
     * Replace any HTML tags with a space.  
     * @param str 
     * @return 
     */  
    public static String removeHTML(String str) {  
        return removeHTML(str, true);  
    }  
    
    
    /**
     * 将字符串首字母大写
     * @param content
     * @return
     */
    public static String upperFirstLetter(String content) {
    	if(content!=null&&content.length()>0){
    		String firstLetter = content.substring(0, 1).toUpperCase();
    		String secondLetter = content.substring(1);
    		content = firstLetter + secondLetter;
    	}
    	return content;
    }
    
    
    /**
     * 去除空格
     * @param str
     * @return
     */
    public static String removeBlank(String str) {
    	StringBuffer sb = new StringBuffer("");
    	for(char ch : str.toCharArray()) {
    		if(ch!=' ' && ch!='\n' && ch!='\r') {
    			sb.append(ch);
    		}
    	}
    	return sb.toString().replace("&nbsp;", " ").replace("<br>", " ").replace("<br/>", " ");
    }
    
}  