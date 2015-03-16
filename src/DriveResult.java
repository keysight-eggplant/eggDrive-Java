import java.awt.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class DriveResult {
    private String output;
    private Double duration;
    private Object returnValue;
    private Object result;

    private boolean successful;

    private Integer faultCode;
    private String faultString;

    DriveResult(String output, Double duration, Object returnValue, Object result) {
        successful = true;
        this.output = output;
        if ( this.output == null ) this.output = "";
        this.duration = duration;
        this.returnValue = returnValue;
        this.result = result;
    }

    DriveResult(Integer faultCode, String faultString) {
        successful = false;
        this.faultCode = faultCode;
        this.faultString = faultString;
    }

    public void printIfError(){
        printIfError(System.err);
    }

    public void printIfError(PrintStream printStream){
        if (!wasSuccessful()) {
            printStream.println(String.format("%d: %s", getFaultCode(), getFaultString()));
        }
    }

    public boolean wasSuccessful() {
        return successful;
    }

    public String getOutput() {
        return output;
    }

    public Double getDuration() {
        return duration;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public Object getResult() {
        return result;
    }

    public Integer getFaultCode() {
        return faultCode;
    }

    public String getFaultString() {
        return faultString;
    }

    public Point getReturnValueAsPoint() {
        if ( returnValue instanceof Object[] && ((Object[])returnValue)[0] instanceof Double ) {
            return new Point(((Double) ((Object[]) returnValue)[0]).intValue(), ((Double) ((Object[]) returnValue)[1]).intValue());
        }
        return null;
    }

    public List<Point> getReturnValueAsPoints() {
        List<Point> ret = new ArrayList<Point>();

        if ( returnValue instanceof Object[] && ( ((Object[])returnValue).length ==0 || ((Object[])returnValue)[0] instanceof Object[] )) {
            for (Object doubles : (Object[]) returnValue) {
                ret.add(new Point(((Double) ((Object[]) doubles)[0]).intValue(), ((Double) ((Object[]) doubles)[1]).intValue()));
            }
        } else if ( returnValue instanceof Object[] && ((Object[])returnValue)[0] instanceof Double ) {
            ret.add(getReturnValueAsPoint());
        }
        return ret;
    }

    public Dimension getReturnValueAsDimension() {
        if ( returnValue instanceof Object[] && ((Object[])returnValue)[0] instanceof Double ) {
            return new Dimension(((Double) ((Object[]) returnValue)[0]).intValue(), ((Double) ((Object[]) returnValue)[1]).intValue());
        }
        return null;
    }

    public Map<String, Object> getReturnValueAsPList() {
        return (Map<String,Object>) returnValue;
    }

    public List<Map<String, Object>> getReturnValueAsPLists() {
        List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();

        if ( returnValue instanceof Object[]) {
            for (Object map : (Object[]) returnValue) {
                ret.add((Map<String, Object>) map);
            }
        }else{
            ret.add(getReturnValueAsPList());
        }

        return ret;
    }

}

