package com.netvariant.customer;


import com.netvariant.shared.util.ContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.moqui.context.ExecutionContext;
import org.moqui.context.LoggerFacade;
import org.moqui.context.MessageFacade;
import org.moqui.service.ServiceFacade;
import org.moqui.util.ContextStack;

import java.util.HashMap;
import java.util.Map;


public class CustomerServices {

    /**
     * Creates a new enumeration.
     *
     * @param ec Execution context
     * @return Output parameter map
     */
    public Map<String , Object> createCustomer(ExecutionContext ec) {


        // shortcuts for convenience
        MessageFacade mf = ec.getMessage();
        ContextStack cs = ec.getContext();
        LoggerFacade lf = ec.getLogger();
        ServiceFacade sf = ec.getService();



        // get parameters
        String customerNumber = (String) cs.getOrDefault("customerId", null);
        String customerFname = (String) cs.getOrDefault("customerFname", null);
        String customerLname = (String) cs.getOrDefault("customerLname", null);
        String carId = (String) cs.getOrDefault("carId" , null);

        // log parameters
        long startTimeMillis = System.currentTimeMillis();
        String logId = ContextUtil.getLogId(ec);
        lf.debug(String.format("[%s] Creating customer ...", logId));
        lf.debug(String.format("[%s] Param customerNumber=%s", logId, customerNumber));
        lf.debug(String.format("[%s] Param customerFname=%s", logId, customerFname));
        lf.debug(String.format("[%s] Param customerLname=%s", logId, customerLname));
        lf.debug(String.format("[%s] Param carId=%s", logId, carId));

        // validate input
        if (StringUtils.isBlank(customerFname)) {
            lf.error(String.format("[%s] First name is required", logId));
            mf.addError("Number is required.");
            return new HashMap<>();
        }

        // create enumeration
        Map<String, Object> cust = sf.sync().name("create#moqui.rent.Customer")
                .parameter("customerNumber", customerNumber)
                .parameter("customerFname", customerFname)
                .parameter("customerLname",customerLname)
                .parameter("carId", carId)
                .call();
        String custnb = (String) cust.get("custnb");

        // log processing time
        lf.debug(String.format("[%s] Customer with number %s created successfully in %d milliseconds", logId, custnb, System.currentTimeMillis() - startTimeMillis));
        mf.addMessage("Created successfully");

        // return output parameters
        HashMap<String, Object> outParams = new HashMap<>();
        outParams.put("custnb", custnb);
        return outParams;
    }
/*
    public Map<String, Object> updateCustomer (ExecutionContext ec) {

        // shortcuts for convenience
        ContextStack cs = ec.getContext();
        MessageFacade mf = ec.getMessage();
        ServiceFacade sf = ec.getService();
        LoggerFacade lf = ec.getLogger();

        // get parameters
        String customerId = (String) cs.getOrDefault("customerId", null);
        String customerName = (String) cs.getOrDefault("customerName", null);
        String carId = (String) cs.getOrDefault("carId", null);


        // log parameters
        long startTimeMillis = System.currentTimeMillis();
        String logId = ContextUtil.getLogId(ec);
        lf.debug(String.format("[%s] Updating customer ...", logId));
        lf.debug(String.format("[%s] Param customerId=%s", logId, customerId));
        lf.debug(String.format("[%s] Param customerName=%s", logId, customerName));
        lf.debug(String.format("[%s] Param carId=%s", logId, carId));

        // validate input
        if (StringUtils.isBlank(customerId)) {
            lf.error(String.format("[%s] customerId is required", logId));
            mf.addError("ID is required.");
            return new HashMap<>();
        }

        // update enumeration
        sf.sync().name("update#moqui.rent.Customer")
                .parameter("customerId", customerId)
                .parameter("customerName", customerName)
                .parameter("carId", carId)
                .call();

        // log processing time
        lf.debug(String.format("[%s] Customer with ID %s updated successfully in %d milliseconds", logId, customerId, System.currentTimeMillis() - startTimeMillis));
        mf.addMessage("Updated successfully");

        // return output parameters
        HashMap<String, Object> outParams = new HashMap<>();
        outParams.put("customerId", customerId);
        return outParams;
    }*/
}


