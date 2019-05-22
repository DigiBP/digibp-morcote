

import java.util.Calendar;
import java.text.SimpleDateFormat;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


public class InitVariables implements JavaDelegate {


    private DelegateExecution execution;




    private void setIfNull(String varName, String value) {
        Object var = execution.getVariable(varName);
        if (var == null) {
            execution.setVariable(varName, value);
            LOGGER.info("set to '" + varName + "' to '" + value + "'");
        }
    }



    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    public void execute(DelegateExecution execution) throws Exception {

        this.execution = execution;

        exec

    }

}