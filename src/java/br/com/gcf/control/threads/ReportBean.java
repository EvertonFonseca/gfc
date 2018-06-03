/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.control.threads;

import java.util.concurrent.ThreadFactory;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

/**
 *
 * @author Windows
 */
public class ReportBean {

    private static ThreadFactory threadFactory = new CustomizableThreadFactory();

    public static void runReports(Runnable runnable) {

        try {

            if (!(runnable instanceof ReportTask) && !(runnable instanceof ReportTask2)) {
                throw new Exception("Runnable must be ReportTask!");
            }

            Thread thread = threadFactory.newThread(runnable);
            thread.setPriority(Thread.MAX_PRIORITY);
            thread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
