package com.sidert.sidertmovil.v2.bussinesmodel;

import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.v2.SidertMovilApplication;
import com.sidert.sidertmovil.v2.utils.ExecutorUtil;

public class BaseBussinesModel {

    protected final SidertMovilApplication sidertMovilApplication;
    protected final ExecutorUtil executorUtil;
    protected final SessionManager sessionManager;

    public BaseBussinesModel(SidertMovilApplication sidertMovilApplication, ExecutorUtil executorUtil, SessionManager sessionManager) {
        this.sidertMovilApplication = sidertMovilApplication;
        this.executorUtil = executorUtil;
        this.sessionManager = sessionManager;
    }

}
