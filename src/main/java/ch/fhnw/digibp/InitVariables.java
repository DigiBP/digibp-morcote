package ch.fhnw.digibp;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.*;


public class InitVariables implements JavaDelegate {


    private DelegateExecution execution;



    public void execute(DelegateExecution execution) throws Exception {

        this.execution = execution;

        Object val1= execution.getVariable("Product of Stock");
        val1.toString();
    }


}

