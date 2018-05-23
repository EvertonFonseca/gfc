/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.control.threads;

import eu.webtoolkit.jwt.WApplication;

/**
 *
 * @author Windows
 */
public abstract class ReportTask<T> implements Runnable {

    public WApplication app;
    public T signal;
    public WApplication.UpdateLock lock;

    public ReportTask(WApplication app, T signal) {

        this.app = app;
        this.signal = signal;
    }

    public void beginLock() {

        this.app.attachThread(true);
        this.lock = app.getUpdateLock();

    }

    public void endLock() {

        try {

            this.app.triggerUpdate();

        } finally {

            this.lock.release();
            this.app.attachThread(false);
        }

    }

}
