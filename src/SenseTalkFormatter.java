import java.awt.*;
import java.util.*;
import java.util.List;


class SenseTalkFormatter {

    Map<String,Object> argMap = new HashMap<String, Object>();
    List<Object> argList = new LinkedList<Object>();

    SenseTalkFormatter reset(){
        argList.clear();
        argMap.clear();
        return this;
    }

    String asCommand(String commandName){
        if ( argMap.size() > 0 && argList.size() > 0)
            return String.format("%s (%s, %s)", commandName, formatNoParens(argList), formatNoParens(argMap) );
        if ( argMap.size() > 0 )
            return String.format("%s (%s)", commandName, formatNoParens(argMap) );
        if ( argList.size() > 0 )
            return String.format("%s %s", commandName, formatNoParens(argList) );
        return commandName;
    }

    String asFunction(String functionName){
        if ( argMap.size() > 0 && argList.size() > 0)
            return String.format("return %s (%s, %s)", functionName, formatNoParens(argList), formatNoParens(argMap) );
        if ( argMap.size() > 0 )
            return String.format("return %s (%s)", functionName, formatNoParens(argMap) );
        if ( argList.size() > 0 )
            return String.format("return %s (%s)", functionName, formatNoParens(argList) );
        return String.format("return %s()", functionName);
    }

    SenseTalkFormatter addParameter(Object param){
        if(param != null) argList.add(param);
        return this;
    }

    SenseTalkFormatter addQuotedParameter(Object param){
        if(param != null) argList.add(quoted(formatObject(param)));
        return this;
    }

    SenseTalkFormatter addPListParameter(String key, Object param){
        if(key != null && param != null) argMap.put(key, param);
        return this;
    }

    SenseTalkFormatter addPListParameters(Map<String, Object> otherPlist){
        if(otherPlist != null) argMap.putAll(otherPlist);
        return this;
    }

    SenseTalkFormatter addQuotedPListParameter(String key, Object param){
        if(key != null && param != null) argMap.put(key, quoted(formatObject(param)));
        return this;
    }


    static String quoted(String inner) {
        if ( inner == null) return null;
        return String.format("\"%s\"", inner.replaceAll("\"", "\" & quote & \""));
    }

    static String format(List<Object> list) {
        return "("+formatNoParens(list)+")";
    }
    static String formatNoParens(List<Object> list) {
        String stList = "";
        for (Object entry : list) {
            stList += formatObject(entry) + ", ";
        }
        stList = stList.substring(0, stList.length()-2); // remove trailing comma and space
        return stList;
    }

    static String format(Map<String,Object> plist) {
        return "("+formatNoParens(plist)+")";
    }
    static String formatNoParens(Map<String,Object> plist) {
        String stPlist = "";
        for (Map.Entry<String, Object> entry : plist.entrySet()) {
            stPlist += entry.getKey() + ":" + formatObject(entry.getValue()) + ", ";
        }
        stPlist = stPlist.substring(0, stPlist.length()-2); // remove trailing comma and space
        return stPlist;
    }

    static String formatObject(Object object) {
        if (object instanceof String) {
            return ((String)object);
        } else if (object instanceof Boolean) {
            return ((Boolean)object) ? "true" : "false";
        } else if (object instanceof Point) {
            Point p = (Point)object;
            return String.format("(%d, %d)",p.x,p.y);
        } else if (object instanceof Rectangle) {
            Rectangle r = (Rectangle)object;
            return String.format("((%d, %d), (%d, %d))", r.x, r.y, r.x+r.width, r.y+r.height);
        } else if (object instanceof Dimension) {
            Dimension d = (Dimension)object;
            return String.format("((%d, %d))", d.width, d.height);
        } else if (object instanceof Object[]) {
            List<Object> list = Arrays.asList((Object[])object);
            return format(list);
        } else if (object instanceof Map) {
            return format((Map<String,Object>)object);
        }
        else if (object != null) {
            return object.toString();
        } else {
            return "";
        }
    }

}
