package com.sidert.sidertmovil.v2.repositories;

import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.v2.utils.ExecutorUtil;

public class BaseRepository {

    protected final ExecutorUtil executorUtil;
    protected final SessionManager sessionManager;

    public BaseRepository(ExecutorUtil executorUtil, SessionManager sessionManager) {
        this.executorUtil = executorUtil;
        this.sessionManager = sessionManager;
    }
}
